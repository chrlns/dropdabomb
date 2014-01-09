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
package me.lins.dropdabomb.client.gui;


/**
 * Subclass from AnimationTimer Implements the die Animation
 * 
 * @author Kai Ritterbusch
 */
public class DieTimer extends AnimationTimer {
    private int calls = 0;

    public DieTimer(ElementPainter painter, int delay, int period) {
        super(painter, delay, period);
    }

    /**
     * Cancel Timer
     * 
     * @return
     */
    @Override
    public boolean cancel() {
        this.timer.cancel();
        return super.cancel();
    }

    /**
     * Run the Animation
     */
    @Override
    public void run() {
        if (calls++ > 5)
            cancel();

        painter.nextDieImage();
        painter.repaint();
    }
}
