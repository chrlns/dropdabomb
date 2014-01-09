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
package me.lins.dropdabomb.server.api;

import java.io.Serializable;
import java.util.Random;

/**
 * Creates randomized Session.
 * 
 * @author Kai Ritterbusch
 * @author Christian Lins
 */
public class Session implements Serializable {

    private static final long serialVersionUID = -1207981957932084719L;

    private final int         sessionID;
    private final int         hashCode;

    public Session() {
        Random rn = new Random();
        this.sessionID = rn.nextInt();

        this.hashCode = rn.nextInt();
    }

    /**
     * It is necessary to override this method that instance recognition works
     * over VM borders.
     */
    @Override
    public boolean equals(Object obj) {
        return hashCode() == obj.hashCode();
    }

    /**
     * @return The Session ID.
     */
    public int getID() {
        return this.sessionID;
    }

    /**
     * It is necessary to override this method that instance recognition works
     * over VM borders.
     * 
     * @return
     */
    @Override
    public int hashCode() {
        return this.hashCode;
    }
}
