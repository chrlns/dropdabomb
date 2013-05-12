/*
 *  DropDaBomb
 *  Copyright (C) 2008-2013 Christian Lins <christian@lins.me>
 *  Copyright (C) 2008 Kai Ritterbusch <kai.ritterbusch@googlemail.com>
 * 
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package me.lins.dropdabomb.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import me.lins.dropdabomb.net.Event;
import me.lins.dropdabomb.server.api.Element;
import me.lins.dropdabomb.server.api.Session;

/**
 * 
 * @author Christian Lins
 */
class ExplosionConsumer extends Thread {

    private Server server = null;

    public ExplosionConsumer(Server server) {
        this.server = server;
    }

    @Override
    public void run() {
        for (;;) {
            try {
                // Calling take() blocks if the queue is empty
                List<Object> explData = this.server.getExplosions().take();
                if (explData == null) // Queue is empty
                {
                    break;
                }

                Game game = (Game) explData.get(0);
                if (!game.isRunning()) // Game could be stopped until now
                {
                    continue;
                }

                int x = (Integer) explData.get(1);
                int y = (Integer) explData.get(2);
                int dist = (Integer) explData.get(3);
                for (Session sess : game.getPlayerSessions()) {
                    this.server.getClients().get(sess)
                            .explosion(new Event(new Object[] { x, y, dist }));
                }
                for (Session sess : game.getSpectatorSessions()) {
                    this.server.getClients().get(sess)
                            .explosion(new Event(new Object[] { x, y, dist }));
                }

                boolean top = false;
                boolean bottom = false;
                boolean left = false;
                boolean right = false;

                // Delete exploded elements
                for (int i = 1; i <= dist; i++) {
                    for (int k = 0; k < 5; k++) {
                        // on the bomb
                        Element el = game.getPlayground().getElement(x, y)[k];
                        processExplElement(game, el, x, y, k);

                        if (!right) {
                            Element e = game.getPlayground().getElement(x + i,
                                    y)[k];
                            right = processExplElement(game, e, x + i, y, k);
                        }
                        if (!left) {
                            Element e = game.getPlayground().getElement(x - i,
                                    y)[k];
                            left = processExplElement(game, e, x - i, y, k);
                        }
                        if (!top) {
                            Element e = game.getPlayground().getElement(x,
                                    y - i)[k];
                            top = processExplElement(game, e, x, y - i, k);
                        }
                        if (!bottom) {
                            Element e = game.getPlayground().getElement(x,
                                    y + i)[k];
                            bottom = processExplElement(game, e, x, y + i, k);
                        }
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /*
     * Get randomized extras or null after explosion
     * 
     * @return Element or null
     */
    private Element createRandomizedExtra(int x, int y) {
        Random rn = new Random();
        float i = rn.nextFloat();
        if (i < 0.15)
            return new ExtraDistance(x, y);
        else if (i < 0.25)
            return new ExtraBomb(x, y);
        else
            return null;
    }

    /**
     * @return false if the bombing blast must continue in this direction.
     */
    private boolean processExplElement(Game game, Element e, int x, int y, int k) {
        if (e != null) {
            if (e instanceof ExplodableWall) {
                game.getPlayground().setElement(x, y, 0,
                        createRandomizedExtra(x, y));
                return true;
            } else if (e instanceof Player) {
                System.out.println(e + " died!");

                // Remove player from game (happens for both AI and Non-AI
                // players)
                game.removePlayer((Player) e);

                // If e is an AIPlayer then it must die.
                // A Non-AI Player will trigger the if-clause in the following
                // for-loop.
                if (e instanceof AIPlayer) {
                    ((AIPlayer) e).die();
                }

                // Loop through all Non-AI players
                List<Session> sessions = new ArrayList<Session>(
                        game.getPlayerSessions());
                for (Session sess : sessions) {
                    try {
                        this.server
                                .getClients()
                                .get(sess)
                                .playerDied(
                                        new Event(new Object[] { x, y,
                                                ((Player) e).getID() }));

                        // Save this death to highscore list.
                        // Note: !(e instanceof AIPlayer) is obsolet at this
                        // point
                        if (e.equals(this.server.getPlayers().get(sess))) {
                            // Send youDied() message to client
                            this.server.getClients().get(sess)
                                    .youDied(new Event(new Object[0]));

                            // Remove session from game; this is important,
                            // otherwise
                            // the game would not stop
                            game.removePlayer(sess);
                            this.server.getPlayerToGame().remove(sess);
                        }
                    } catch (Exception re) {
                        re.printStackTrace();
                    }
                }

                try {
                    // Store the game result in the highscore
                    this.server.getHighscore().hasLostGame(
                            ((Player) e).getNickname());
                    // And update the game and user list on client side
                    this.server.refresh();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                // Remove player from game
                game.removePlayer(x, y, (Player) e);
                game.getPlayground().setElement(x, y, ((Player) e).getID(),
                        null);

                return false;
            } else if (e instanceof Extra) {
                // Delete extra
                game.getPlayground().setElement(x, y, 0, null);
                return true;
            } else if (e instanceof Bomb) {
                ((Bomb) e).explode();
                return false;
            } else if (e instanceof SolidWall) {
                // Stop bombing blast
                return true;
            }
        }
        // Default: continue with bombing blast.
        return false;
    }

}
