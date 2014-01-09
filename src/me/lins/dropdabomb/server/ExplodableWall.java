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

import me.lins.dropdabomb.server.api.Explodable;

/**
 * An exploadable wall.
 * 
 * @author Christian Lins
 */
class ExplodableWall extends Wall implements Explodable {

    private static final long  serialVersionUID = -1095368066934855550L;
    public static final String IMAGE_FILENAME   = "res/gfx/explodable_wall.png";

    protected ExplodableWall() {

    }

    public ExplodableWall(int x, int y) {
        super(x, y);
    }

    /**
     * Gets the filename of an Image
     * 
     * @return Filename
     */
    @Override
    public String getImageFilename() {
        return IMAGE_FILENAME;
    }
}
