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

/**
 * Logical Playground where the Server manages a game. This class is
 * serializable and takes most of the network bandwidth.
 * 
 * @author Kai Ritterbusch
 * @author Christian Lins
 */
public class Playground implements Serializable {
    private static final long serialVersionUID = 8490800837792946540L;
    public static final int   DEFAULT_WIDTH    = 17;
    public static final int   DEFAULT_HEIGHT   = 15;

    /**
     * 3D-matrix. The third level has size 5. [][][0] is for Bombs and Extras
     * [][][1-4] is for Players.
     */
    private Element[][][]     matrix           = null;
    private final int         cols, rows;

    /**
     * No-arg constructor required for serialization.
     */
    protected Playground() {
        this.rows = DEFAULT_WIDTH;
        this.cols = DEFAULT_HEIGHT;
    }

    public Playground(int cols, int rows) {
        this.cols = cols;
        this.rows = rows;
        this.matrix = new Element[cols][rows][5];

        // Initialize the playground
        for (int x = 0; x < cols; x++) {
            for (int y = 0; y < rows; y++) {
                // Solid borders
                if ((x == 0) || (x == cols - 1) || (y == 0) || (y == rows - 1)) {
                    this.matrix[x][y][0] = new SolidWall(x, y); // A solid wall
                }
                // Solid walls within
                else if ((y % 2 == 0) && (x % 2 == 0)) {
                    this.matrix[x][y][0] = new SolidWall(x, y); // Solid wall
                }
                // Player starting points
                else if ((x == 1 && (y == 1 || y == 2))
                        || (x == 2 && y == 1)
                        || // top left
                        (x == cols - 2 && (y == 1 || y == 2))
                        || (x == cols - 3 && y == 1)
                        || // top right
                        (x == 1 && (y == rows - 2 || y == rows - 3))
                        || (x == 2 && y == rows - 2)
                        || // lower left
                        (x == cols - 2 && (y == rows - 2 || y == rows - 3))
                        || (x == cols - 3 && y == rows - 2) // lower right
                ) {
                    // Make no walls
                    continue;
                } else if (Math.random() >= 0.2) // 20% of the area should be
                                                 // empty
                {
                    matrix[x][y][0] = new ExplodableWall(x, y); // Exploadable
                                                                // wall

                    // Extras are placed later when a Wall explodes.
                    // Currently done in ExplosionConsumer
                }
            }
        }
    }

    /**
     * @return Number of columns.
     */
    public int getColumns() {
        return cols;
    }

    /**
     * @return Number of rows.
     */
    public int getRows() {
        return rows;
    }

    /**
     * Get Element at x,y
     * 
     * @param x
     * @param y
     * @return Element[] at the specific position
     */
    public Element[] getElement(int x, int y) {
        try {
            return this.matrix[x][y];
        } catch (ArrayIndexOutOfBoundsException ex) {
            return null;
        }
    }

    /**
     * Sets Element e at x,y on layer layer
     * 
     * @param x
     * @param y
     * @param layer
     * @param e
     */
    public void setElement(int x, int y, int layer, Element e) {
        this.matrix[x][y][layer] = e;
    }
}
