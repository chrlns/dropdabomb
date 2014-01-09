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

import java.io.Serializable;

import me.lins.dropdabomb.server.api.Element;
import me.lins.dropdabomb.server.api.Explodable;

/**
 * The BOMB!
 * 
 * @author Kai Ritterbusch
 * @author Christian Lins
 */
class Bomb extends Element implements Explodable, Serializable {

    private static final long   serialVersionUID = -5117720119134439933L;

    // Transient fields; so they are not serialized
    private transient Player    player;
    private transient BombTimer timer;

    // Serializable fields
    private int                 stage            = 1;

    /**
     * No-arg constructor required for serialization.
     */
    protected Bomb() {

    }

    public Bomb(int x, int y, Player player) {
        super(x, y);
        this.player = player;

        timer = new BombTimer(this);
    }

    /**
     * Bomb explodes.
     */
    void explode() {
        try {
            System.out.println(this + " explodes!");
            player.bombs.remove(this);
            player.game.getPlayground().setElement(gridX, gridY, 0, null);
            timer.cancel();
            Server.getInstance().notifyExplosion(player.game, gridX, gridY,
                    player.bombDistance);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Gets filename of the Image
     * 
     * @return
     */
    @Override
    public String getImageFilename() {
        return "res/gfx/bomb/bomb" + stage + ".png";
    }

    /**
     * Updates Playground
     * 
     * @return
     */
    int tick() {
        player.game.forcePlaygroundUpdate();
        return ++stage;
    }
}
