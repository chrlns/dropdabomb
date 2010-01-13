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

import java.util.Timer;
import java.util.TimerTask;

/**
 * Timer controlling exploding bombs.
 * @author Christian Lins (kai.ritterbusch@fh-osnabrueck.de)
 * @author Christian Lins (christian.lins@web.de)
 */
class BombTimer extends TimerTask
{
  public static final int BOMB_TIME = 4000;
  
  private Timer timer = new Timer();
  private Bomb bomb;
    
  public BombTimer(Bomb bomb)
  {
    this.bomb = bomb;
    timer.schedule(this, BOMB_TIME / 6, BOMB_TIME / 6);
  }
    
  /**
   * Is called when bomb has reached last Image status
   */   
  @Override  
  public void run()
  {
    if(bomb.tick() >= 6)
    {
      bomb.explode();
      timer.cancel();
    }
  }  
}
