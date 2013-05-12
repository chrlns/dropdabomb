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

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import me.lins.dropdabomb.Main;
import me.lins.dropdabomb.server.api.Element;
import me.lins.dropdabomb.server.api.Explodable;


/**
 * An AI-controlled player. The AI uses a modified A* algorithm for path
 * finding.
 * 
 * @author Kai Ritterbusch
 * @author Christian Lins
 */
class AIPlayer extends Player {

    private static final long     serialVersionUID = -5235279588090879189L;

    private transient List<int[]> currentPath      = new ArrayList<int[]>();
    private transient boolean     isDead           = false;
    private transient Playground  playground       = null;

    /**
     * No-arg constructor required for serialization.
     */
    protected AIPlayer() {

    }

    public AIPlayer(Game g, Playground playground) {
        super(g, "KI-Knecht");

        this.nickname += hashCode();

        if (g == null || playground == null)
            throw new IllegalArgumentException();

        this.game = g;
        this.playground = playground;

        Thread thread = new AIPlayerThread(this, g);
        thread.start();
    }

    /**
     * Search for explodable Elements in Element[]
     * 
     * @param elements
     * @return
     */
    private int containsExplodable(Element[] elements) {
        int explodables = 0;

        for (Element e : elements) {
            if (e instanceof Explodable && !(e instanceof Extra))
                explodables++;
        }

        return explodables;
    }

    /**
     * Check if Element[] contains another element
     * 
     * @param elements
     * @param c
     * @return true if Element[] contains c
     */
    private boolean contains(Element[] elements, Element c) {
        for (Element e : elements)
            if (c.equals(e))
                return true;

        return false;
    }

    /**
     * Method is called when AIPlayer dies. Sets this.isDead to true and removes
     * player from gamelist
     */
    public void die() {
        this.isDead = true;

        // Remove player from game
        this.game.removePlayer(this);
    }

    /**
     * Returns living status of the Player
     * 
     * @return
     */
    public boolean isDead() {
        return this.isDead;
    }

    /**
     * Determines if the given point is a possible target zone, that means has
     * Explodable neighbours.
     * 
     * @param pnt
     * @return
     */
    private boolean isTargetZone(Point pnt) {
        // Determine all possible neighbours...
        Element[] n1 = this.playground.getElement(pnt.x + 1, pnt.y);
        Element[] n2 = this.playground.getElement(pnt.x - 1, pnt.y);
        Element[] n3 = this.playground.getElement(pnt.x, pnt.y + 1);
        Element[] n4 = this.playground.getElement(pnt.x, pnt.y - 1);

        int numExpln1 = containsExplodable(n1);
        int numExpln2 = containsExplodable(n2);
        int numExpln3 = containsExplodable(n3);
        int numExpln4 = containsExplodable(n4);
        int sumExpl = numExpln1 + numExpln2 + numExpln3 + numExpln4;

        if (sumExpl == 1) {
            if (contains(n1, this) || contains(n2, this) || contains(n3, this)
                    || contains(n4, this))
                return false;
            else
                return true;
        } else if (sumExpl > 1)
            return true;
        else
            return false;
    }

    private boolean isExtra(Point pnt) {
        Element[] el = this.playground.getElement(pnt.x, pnt.y);
        if (el == null)
            return false;
        return el[0] instanceof Extra;
    }

    /**
     * Calculates a path to a possible bombing spot
     */
    private List<int[]> calculateTargetPath() {
        // The A* Algorithm
        List<int[]> openNodes = new ArrayList<int[]>(); // Not yet visited nodes
        List<int[]> closedNodes = new ArrayList<int[]>(); // Already visited
                                                          // nodes

        // Initialize with starting point (add = push = at end of list)
        openNodes.add(new int[] { gridX, gridY, 0, 0 }); // Starting point
                                                         // (gridX, gridY) is
                                                         // the current player
                                                         // position
        while (openNodes.size() > 0) {
            int[] node = openNodes.remove(0); // pop()
            Point pnt = new Point(node[0], node[1]);

            if (checkForBomb(pnt) != null) {
                // A bomb in the path is a really bad idea or we must know
                // when this bomb explodes...
                // And placing a bomb next to a ticking other is a even worse
                // idea...
                continue;
            } else if (isTargetZone(pnt) || // Are the neighbours of point
                                            // explodable?
                    isExtra(pnt) || // Or is it an extra we can collect?
                    closedNodes.size() > 15) {
                // Backtrace the path
                List<int[]> path = new ArrayList<int[]>();
                path.add(0, node);
                while (closedNodes.size() > 0)
                    path.add(0, closedNodes.remove(0));

                return path;
            } else {
                int r1 = Math.random() > 0.5 ? 1 : -1;
                int r2 = r1 == 1 ? -1 : 1;

                // Find all possible neighbours of node
                Element[] n1a = this.playground.getElement(node[0] + r1,
                        node[1]);
                Element n1 = n1a == null ? null : n1a[0];
                Element[] n2a = this.playground.getElement(node[0] + r2,
                        node[1]);
                Element n2 = n2a == null ? null : n2a[0];
                Element[] n3a = this.playground.getElement(node[0], node[1]
                        + r1);
                Element n3 = n3a == null ? null : n3a[0];
                Element[] n4a = this.playground.getElement(node[0], node[1]
                        + r2);
                Element n4 = n4a == null ? null : n4a[0];

                boolean saveNode = false;

                if ((n1 == null || n1 instanceof Extra)
                        && node[2] != node[0] + r1) {
                    openNodes.add(0, new int[] { node[0] + r1, node[1],
                            node[0], node[1] });
                    saveNode = true;
                }
                if ((n2 == null || n2 instanceof Extra)
                        && node[2] != node[0] + r2) {
                    openNodes.add(0, new int[] { node[0] + r2, node[1],
                            node[0], node[1] });
                    saveNode = true;
                }
                if ((n3 == null || n3 instanceof Extra)
                        && node[3] != node[1] + r1) {
                    openNodes.add(0, new int[] { node[0], node[1] + r1,
                            node[0], node[1] });
                    saveNode = true;
                }
                if ((n4 == null || n4 instanceof Extra)
                        && node[3] != node[1] + r2) {
                    openNodes.add(0, new int[] { node[0], node[1] + r2,
                            node[0], node[1] });
                    saveNode = true;
                }

                if (saveNode)
                    closedNodes.add(0, node);
            }
        }
        return null;
    }

    /**
     * Calculate escaperoute
     * 
     * @param bomb
     * @return
     */
    private List<int[]> calculateHidePath(Element bomb) {
        if (bomb == null)
            return null;

        int x = bomb.getX();
        int y = bomb.getY();

        // the A* Algorithmus
        List<int[]> openNodes = new ArrayList<int[]>(); // Not yet travelled
                                                        // nodes
        List<int[]> closedNodes = new ArrayList<int[]>(); // Travelled nodes

        // Initialize with starting point
        openNodes.add(0, new int[] { x, y, 0, 0 }); // Starting point (x, y) is
                                                    // the bomb
        while (openNodes.size() > 0) {
            int[] node = openNodes.remove(0);
            if (node[0] != x && node[1] != y || closedNodes.size() > 15) // Is
                                                                         // this
                                                                         // point
                                                                         // save?
            {
                // check path
                List<int[]> path = new ArrayList<int[]>();
                path.add(0, node);
                while (closedNodes.size() > 0)
                    path.add(0, closedNodes.remove(0));

                return path;
            } else {
                // Get all neighbours from node
                Element[] n1a = this.playground
                        .getElement(node[0] + 1, node[1]);
                Element n1 = n1a == null ? null : n1a[0];
                Element[] n2a = this.playground
                        .getElement(node[0] - 1, node[1]);
                Element n2 = n2a == null ? null : n2a[0];
                Element[] n3a = this.playground
                        .getElement(node[0], node[1] + 1);
                Element n3 = n3a == null ? null : n3a[0];
                Element[] n4a = this.playground
                        .getElement(node[0], node[1] - 1);
                Element n4 = n4a == null ? null : n4a[0];

                boolean saveNode = false;

                if ((n1 == null || n1 instanceof Extra)
                        && node[2] != node[0] + 1) {
                    openNodes.add(0, new int[] { node[0] + 1, node[1], node[0],
                            node[1] });
                    saveNode = true;
                }
                if ((n2 == null || n2 instanceof Extra)
                        && node[2] != node[0] - 1) {
                    openNodes.add(0, new int[] { node[0] - 1, node[1], node[0],
                            node[1] });
                    saveNode = true;
                }
                if ((n3 == null || n3 instanceof Extra)
                        && node[3] != node[1] + 1) {
                    openNodes.add(0, new int[] { node[0], node[1] + 1, node[0],
                            node[1] });
                    saveNode = true;
                }
                if ((n4 == null || n4 instanceof Extra)
                        && node[3] != node[1] - 1) {
                    openNodes.add(0, new int[] { node[0], node[1] - 1, node[0],
                            node[1] });
                    saveNode = true;
                }

                if (saveNode)
                    closedNodes.add(0, node);
            }
        }

        return null;
    }

    /**
     * Checks if bomb is near the player.
     * 
     * @param bomb
     * @return null if no Bomb is found
     */
    private Element checkForBomb(Point bomb) {
        int matrixX = this.gridX;
        int matrixY = this.gridY;

        if (bomb != null) {
            matrixX = bomb.x;
            matrixY = bomb.y;
        }

        // Player is on the bomb
        if (this.playground.getElement(matrixX, matrixY)[0] instanceof Bomb)
            return this.playground.getElement(matrixX, matrixY)[0];

        // Player is near the bomb
        for (int i = 0; i < 4; i++) {
            if (this.playground.getElement(matrixX + i, matrixY) != null
                    && this.playground.getElement(matrixX + i, matrixY)[0] instanceof Bomb) {
                return this.playground.getElement(matrixX + i, matrixY)[0];
            } else if (this.playground.getElement(matrixX - i, matrixY) != null
                    && this.playground.getElement(matrixX - i, matrixY)[0] instanceof Bomb) {
                return this.playground.getElement(matrixX - i, matrixY)[0];
            } else if (this.playground.getElement(matrixX, matrixY + i) != null
                    && this.playground.getElement(matrixX, matrixY + i)[0] instanceof Bomb) {
                return this.playground.getElement(matrixX, matrixY + i)[0];
            } else if (this.playground.getElement(matrixX, matrixY - i) != null
                    && this.playground.getElement(matrixX, matrixY - i)[0] instanceof Bomb) {
                return this.playground.getElement(matrixX, matrixY - i)[0];
            }
        }
        return null;
    }

    /**
     * Moves Player
     * 
     * @param dx
     * @param dy
     * @return true if playermoved
     */
    private boolean wannaMove(int dx, int dy) {
        if (Main.Debugging)
            System.out.println(this.nickname + " laeuft in Richtung " + dx
                    + "/" + dy);

        boolean moved = this.game.movePlayer(this, dx, dy);

        if (moved) {
            this.game.forcePlaygroundUpdate();
        }

        return moved;
    }

    /**
     * A small step for AI... Moving of the Player
     */
    public void tick() {
        if (!this.game.isRunning())
            return;

        if (currentPath.size() > 0) // walk if path exists
        {
            int[] node = currentPath.remove(0);
            if (!wannaMove(node[0] - gridX, node[1] - gridY)) // Move expects
                                                              // relative
                                                              // direction
                currentPath = new ArrayList<int[]>(); // Delete path because it
                                                      // must be invalid
        } else if (bombs.size() < super.bombCount) // You can put a bomb
        {
            if (isTargetZone(new Point(gridX, gridY))) {
                placeBomb();
                currentPath = calculateHidePath(checkForBomb(new Point(gridX,
                        gridY)));
                if (currentPath == null) {
                    currentPath = new ArrayList<int[]>();
                    placeBomb(); // Suicide
                }
            } else {
                currentPath = calculateTargetPath();
                if (currentPath == null)
                    currentPath = new ArrayList<int[]>();
            }
        } else {
            Element bomb = checkForBomb(null);
            if (bomb == null)
                return;
            currentPath = calculateHidePath(bomb);
            if (currentPath == null) {
                currentPath = new ArrayList<int[]>();
            }
        }
    }

}
