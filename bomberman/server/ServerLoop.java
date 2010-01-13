/*
 *  KC Bomberman
 *  Copyright (C) 2008,2009 Christian Lins <cli@openoffice.org>
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

package bomberman.server;

import bomberman.server.api.Session;
import bomberman.net.Event;
import java.util.Map.Entry;

/**
 * The Server loop.
 * @author Christian Lins
 * @author Kai Ritterbusch
 */
public class ServerLoop extends Thread
{
  private Server server;

  public ServerLoop(Server server)
  {
    this.server = server;
  }

  /**
   * This method runs in a loop while the associated @see{Server} is running.
   */
  @Override
  public void run()
  {
    for (;;)
    {
      try
      {
        for (Entry<String, Game> entry : this.server.getGames().entrySet())
        {
          Game game = entry.getValue();

          // Check if there are enough real players or any
          // spectators left for gaming
          if (game.isRunning() &&
                  (game.getPlayerCount() == 1 ||
                  (game.getPlayerSessions().size() == 0 && game.getSpectatorSessions().size() == 0)))
          {
            game.setRunning(false);

            // Send won game message to the remaining user
            // (if not AIPlayer)
            if (game.getPlayerSessions().size() > 0)
            {
              this.server.getClients().get(game.getPlayerSessions().get(0))
                      .gameStopped(new Event(new Object[]{2}));
              
              // We have to store the game result in the Highscore list
              this.server.getHighscore().hasWonGame(game.getPlayers().get(0).getNickname());
            }
            
            // Send gameStopped message to spectators if existing
            if(game.getSpectatorSessions().size() > 0)
            {
              for(Session sess : game.getSpectatorSessions())
              {
                this.server.getClients().get(sess)
                        .gameStopped(new Event(new Object[]{0}));
              }
            }            
            System.out.println("remove Game ----------");
            
            for(Session sess : game.getPlayerSessions())
              this.server.getPlayerToGame().remove(sess);
            
            this.server.getGames().remove(game.toString());
            this.server.refresh();
            break; // Stop the for-loop
          }

          // Check if it is necessary to send playground update
          // messages to the Clients
          if (game.isPlaygroundUpdateRequired())
          {
            // Updates Playground when moved
            for (Session sess : game.getPlayerSessions())
            {
              this.server.getClients().get(sess).playgroundUpdate(
                      new Event(new Object[]{game.getPlayground()}));
            }
            // Updates Playground for Spectator when moved
            for (Session sess : game.getSpectatorSessions())
            {
              this.server.getClients().get(sess).playgroundUpdate(
                      new Event(new Object[]{game.getPlayground()}));
            }
          }
        }

        Thread.sleep(100); // TODO: Little hacky, no real producer/consumer
      }
      catch (Exception ex)
      {
        ex.printStackTrace();
      }
    }
  }
}
