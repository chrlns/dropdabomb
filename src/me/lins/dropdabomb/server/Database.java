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
package me.lins.dropdabomb.server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Holds all persistant user data (except @see{Highscore}).
 * 
 * @author Christian Lins
 */
public class Database implements Serializable {

    private static final long         serialVersionUID = -2683692874539653550L;

    private final Map<String, String> data             = new HashMap<String, String>();

    /**
     * in order to start a WebStart game a default user is important
     */
    public Database() {
        addUser("gast", "");
    }

    /**
     * Adds a new user to the Database. If the username already exists, it will
     * be overwritten with the new values.
     * 
     * @param username
     * @param password
     */
    public void addUser(String username, String password) {
        this.data.put(username, password);
    }

    /**
     * @return A List with all user names that are known by this Database.
     */
    public List<String> getUsers() {
        return new ArrayList<String>(data.keySet());
    }

    /**
     * Returns the password for the given username or null if the username is
     * not known.
     * 
     * @param username
     * @return
     */
    public String getPassword(String username) {
        return this.data.get(username);
    }

    /**
     * Deletes the given username from this Database.
     * 
     * @param username
     */
    public void removeUser(String username) {
        this.data.remove(username);
    }
}
