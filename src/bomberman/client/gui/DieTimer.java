/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bomberman.client.gui;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Subclass from AnimationTimer
 * Implements the die Animation
 * @author Kai Ritterbusch (kai.ritterbusch@fh-osnabrueck.de)
 */
public class DieTimer extends AnimationTimer
{
  private int            calls = 0;
  
  public DieTimer(ElementPainter painter, int delay, int period)
  {
    super(painter, delay, period);
  }
  
  /**
   * Cancel Timer
   * @return
   */
  @Override
  public boolean cancel()
  {
    this.timer.cancel();
    return super.cancel();
  }


  /**
   * Run the Animation
   */
  @Override
  public void run()
  {
    if(calls++ > 5)
      cancel();
    
    painter.nextDieImage();
    painter.repaint();
  }
}
