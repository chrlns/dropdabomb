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

import java.util.ArrayList;
import java.util.List;

import me.lins.dropdabomb.server.api.Element;
import me.lins.dropdabomb.server.api.Explodable;

/**
 * Player.
 * 
 * @author Kai Ritterbusch
 * @author Christian Lins
 */
public class Player extends Element implements Explodable {

    private static final long serialVersionUID = -7477360646062364093L;

    static enum MoveDirection {
        UP, DOWN, LEFT, RIGHT, NONE, EXPLODING
    }

    protected List<Bomb>  bombs             = new ArrayList<Bomb>();
    protected Game        game;
    protected String      nickname;
    protected int         id;

    // For extras
    protected int         bombDistance      = 1;
    protected int         bombCount         = 1;

    private MoveDirection lastMoveDirection = MoveDirection.DOWN;

    /**
     * No-arg constructor required for serialization.
     */
    protected Player() {

    }

    public Player(Game game, String nickname) {
        super(0, 0);

        this.game = game;
        this.nickname = nickname;
    }

    /**
     * rais if collect BombDistance-Extra
     */
    public void raiseBombDistance() {
        this.bombDistance++;
    }

    /**
     * raise Bombcount
     */
    public void raiseBombCount() {
        this.bombCount++;
    }

    /**
     * Returns Image filename for player
     * 
     * @return filename
     */
    @Override
    public String getImageFilename() {
        String imgPath = "res/gfx/player" + getID() + "/";
        String addition = "";

        switch (lastMoveDirection) {
            case UP: {
                addition = "1";
                break;
            }
            case DOWN: {
                addition = "6";
                break;
            }
            case LEFT: {
                addition = "11";
                break;
            }
            case RIGHT: {
                addition = "16";
                break;
            }
            case EXPLODING:
                break;
            case NONE:
                break;
            default:
                break;
        }

        imgPath = imgPath + addition + ".png";

        return imgPath;
    }

    /**
     * Nickname of the player
     * 
     * @return
     */
    public String getNickname() {
        return this.nickname;
    }

    /**
     * Nickname of the player while using toString()
     * 
     * @return
     */
    @Override
    public String toString() {
        return this.nickname;
    }

    /**
     * Returns ID of the player
     * 
     * @return
     */
    public int getID() {
        return id;
    }

    /**
     * Moves player
     * 
     * @param dx
     * @param dy
     */
    void move(int dx, int dy) {
        this.gridX += dx;
        this.gridY += dy;

        if (dx < 0)
            lastMoveDirection = MoveDirection.LEFT;
        else if (dx > 0)
            lastMoveDirection = MoveDirection.RIGHT;
        else if (dy < 0)
            lastMoveDirection = MoveDirection.UP;
        else if (dy > 0)
            lastMoveDirection = MoveDirection.DOWN;
    }

    /**
     * place a Bomb
     */
    void placeBomb() {
        if (bombs.size() >= this.bombCount)
            return;
        System.out.println("Spieler " + nickname + " legt Bombe bei " + gridX
                + "/" + gridY);

        Bomb bomb = new Bomb(gridX, gridY, this);
        this.bombs.add(bomb);

        this.game.getPlayground().setElement(gridX, gridY, 0, bomb);
    }

    /**
     * Set active game of the player
     * 
     * @param game
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * Set Id of the player
     * 
     * @param id
     */
    public void setID(int id) {
        this.id = id;
    }
}
