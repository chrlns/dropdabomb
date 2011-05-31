/*
 *  KC Bomberman
 *  Copyright 2008 Christian Lins <christian.lins@web.de>
 *  Copyright 2008 Kai Ritterbusch <kai.ritterbusch@googlemail.com>
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

/**
 * Thread controlling one AIPlayer.
 * @author Christian Lins (christian.lins@web.de)
 */
class AIPlayerThread extends Thread
{
  public static final int TICK_TIME = 300;
  
  private Game     game;
  private AIPlayer player;
  
  public AIPlayerThread(AIPlayer player, Game game)
  {
    super("AIPlayerThread for player " + player.hashCode());
    
    this.game   = game;
    this.player = player;
    
    setPriority(Thread.MIN_PRIORITY);
  }
  
  @Override
  public void run()
  {
    try
    {
      // Wait for the game to start
      while (!game.isRunning())
      {
        Thread.sleep(100);
      }

      // Run until game stopps
      while (game.isRunning() && !player.isDead())
      {
        player.tick();
        Thread.sleep(TICK_TIME);
      }
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
  }
}
