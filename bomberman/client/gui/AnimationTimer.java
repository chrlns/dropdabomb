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

package bomberman.client.gui;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Main-Class of the Timer-Classes.
 * Implements a timerTask for animations.
 * @author Kai Ritterbusch
 */
abstract class AnimationTimer extends TimerTask
{
  protected ElementPainter painter;
  protected Timer          timer    = new Timer();
  
  /**
   * Initialize the Timer
   * @param painter
   * @param delay
   * @param period
   */
  public AnimationTimer(ElementPainter painter, int delay, int period)
  {
    this.painter = painter;    
    this.timer.schedule(this, delay, period);
  }

}
