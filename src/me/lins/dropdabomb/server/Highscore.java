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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.lins.dropdabomb.util.Pair;

/**
 * Stores the persistent Highscore data.
 * 
 * @author Kai Ritterbusch
 * @author Christian Lins
 */
public class Highscore implements Serializable {

    private static final long                         serialVersionUID = -4160498922750320232L;

    /** Stores won and lost games per username */
    private final Map<String, Pair<Integer, Integer>> data             = new HashMap<String, Pair<Integer, Integer>>();

    /**
     * Get number of lost games.
     * 
     * @param username
     * @return number of Lost Games
     */
    public int getLostGames(String username) {
        Pair<Integer, Integer> p = data.get(username);
        if (p != null) {
            return p.getB();
        } else {
            return 0;
        }
    }

    /**
     * Get number of won games.
     * 
     * @param username
     * @return number of won Games
     */
    public int getWonGames(String username) {
        Pair<Integer, Integer> p = data.get(username);
        if (p != null) {
            return p.getA();
        } else
            return 0;
    }

    /**
     * @return A list of names this highscore knows.
     */
    public List<String> getNames() {
        return new ArrayList<String>(data.keySet());
    }

    /**
     * Increases the number of lost games for this username.
     * 
     * @param username
     * @return
     */
    public int hasLostGame(String username) {
        int lostGames = 0;
        if (this.data.containsKey(username))
            lostGames = this.data.get(username).getB();
        else
            this.data.put(username, new Pair<Integer, Integer>(0, 0));

        lostGames++;
        this.data.get(username).setB(lostGames);
        return lostGames;
    }

    /**
     * Increases the number of won games for this username.
     * 
     * @param username
     * @return
     */
    public int hasWonGame(String username) {
        int wonGames = 0;
        if (this.data.containsKey(username))
            wonGames = this.data.get(username).getA();
        else
            this.data.put(username, new Pair<Integer, Integer>(0, 0));

        wonGames++;
        this.data.get(username).setA(wonGames);
        return wonGames;
    }
}
