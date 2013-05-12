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
package bomberman.server.api;

import java.io.Serializable;

/**
 * One tile of the Playground that can be painted through the ElementPainter
 * class on client side. There are various derived classes for the different
 * tile types: @see{Bomb}, @see{Wall}, @see{Player}. This class could be seen as
 * model component on a MVC-architecture.
 * 
 * @author Christian Lins
 */
@SuppressWarnings("serial")
public abstract class Element implements Serializable {
    protected transient int gridX;
    protected transient int gridY;

    /**
     * No-arg constructor required for serialization.
     */
    protected Element() {

    }

    public Element(int x, int y) {
        this.gridX = x;
        this.gridY = y;
    }

    public abstract String getImageFilename();

    public int getX() {
        return this.gridX;
    }

    public int getY() {
        return this.gridY;
    }

    public void setPosition(int x, int y) {
        this.gridX = x;
        this.gridY = y;
    }
}
