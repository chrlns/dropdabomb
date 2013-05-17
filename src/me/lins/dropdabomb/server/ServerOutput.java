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

import java.io.OutputStream;

import me.lins.dropdabomb.client.api.ServerListenerInterface;
import me.lins.dropdabomb.net.Event;
import me.lins.dropdabomb.net.EventDispatcherBase;

/**
 * Sends Events to a connected client.
 * 
 * @author Christian Lins
 */
class ServerOutput extends EventDispatcherBase implements
        ServerListenerInterface {

    public ServerOutput(OutputStream out) {
        super(out);
    }

    @Override
    public void continueLogin(Event event) {
        System.out.println("ServerOutput.continueLogin()");
        event.setMethodName("continueLogin");
        dispatchEvent(event);
    }

    @Override
    public void explosion(Event event) {
        event.setMethodName("explosion");
        dispatchEvent(event);
    }

    @Override
    public void gameJoined(Event event) {
        event.setMethodName("gameJoined");
        dispatchEvent(event);
    }

    @Override
    public void gameListUpdate(Event event) {
        event.setMethodName("gameListUpdate");
        dispatchEvent(event);
    }

    @Override
    public void gameStarted(Event event) {
        event.setMethodName("gameStarted");
        dispatchEvent(event);
    }

    @Override
    public void gameStopped(Event event) {
        event.setMethodName("gameStopped");
        dispatchEvent(event);
    }

    @Override
    public void loggedIn(Event event) {
        event.setMethodName("loggedIn");
        dispatchEvent(event);
    }

    @Override
    public void loggedOut(Event event) {
        event.setMethodName("loggedOut");
        dispatchEvent(event);
    }

    @Override
    public void playerDied(Event event) {
        event.setMethodName("playerDied");
        dispatchEvent(event);
    }

    @Override
    public void playerLeftGame(Event event) {
        event.setMethodName("playerLeftGame");
        dispatchEvent(event);
    }

    @Override
    public void playgroundUpdate(Event event) {
        event.setMethodName("playgroundUpdate");
        dispatchEvent(event);
    }

    @Override
    public void receiveChatMessage(Event event) {
        event.setMethodName("receiveChatMessage");
        dispatchEvent(event);
    }

    @Override
    public void userListUpdate(Event event) {
        event.setMethodName("userListUpdate");
        dispatchEvent(event);
    }

    @Override
    public void youDied(Event event) {
        event.setMethodName("youDied");
        dispatchEvent(event);
    }

}
