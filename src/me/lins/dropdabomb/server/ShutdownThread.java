/*
 *  KC Bomberman
 *  Copyright (C) 2008 Christian Lins <christian@lins.me>
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

/**
 * Stores the persistent data of the Server. This thread is executed when the VM
 * stops. Due to security restrictions this is not done on the Java WebStart
 * edition of KC Bomberman.
 * 
 * @author Christian Lins
 */
class ShutdownThread extends Thread {

    public static final String DATABASE_FILE  = "database.po";
    public static final String HIGHSCORE_FILE = "highscore.po";
    private Database           database       = null;
    private Highscore          highscore      = null;

    public ShutdownThread(Database database, Highscore highscore) {
        this.database = database;
        this.highscore = highscore;
    }

    /**
     * Serializes the Database and the Highscore.
     */
    @Override
    public void run() {

    }
}
