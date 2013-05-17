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

/**
 * Contains the information for one Game. This class is used to update clients
 * with new game information. This class capsulates all necessary information
 * for the client about a @see{Game}. To transmit a full Game class instance
 * would be a lot of overhead and a potential security leak.
 * 
 * @author Christian Lins
 */
public class GameInfo implements Serializable {

    private static final long serialVersionUID = -5760989736155351368L;

    private String            gameName         = null;
    private String            creator          = null;
    private String            status           = null;
    private String            playerCount      = null;

    /**
     * No-arg constructor required for serialization.
     */
    protected GameInfo() {

    }

    /**
     * Constructs a new GameInfo using the given parameters.
     * 
     * @param gameName
     * @param creator
     * @param isRunning
     * @param playerCount
     */
    public GameInfo(String gameName, String creator, boolean isRunning,
            int playerCount) {
        this.gameName = gameName;
        this.creator = creator;

        if (isRunning) {
            this.status = "Started";
        } else {
            this.status = "Waiting...";
        }

        this.playerCount = playerCount + "/4";
    }

    /**
     * @return Name of the game.
     */
    public String getName() {
        return this.gameName;
    }

    /**
     * @return Creator of the game.
     */
    public String getCreator() {
        return this.creator;
    }

    /**
     * @return Status of the game.
     */
    public String getStatus() {
        return this.status;
    }

    /**
     * @return Number of players that have joined the game.
     */
    public String getPlayerCount() {
        return this.playerCount;
    }
}
