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
package me.lins.dropdabomb.client;

import java.io.OutputStream;

import me.lins.dropdabomb.net.Event;
import me.lins.dropdabomb.net.EventDispatcherBase;
import me.lins.dropdabomb.server.api.ServerInterface;

/**
 * This class is the glue between Server and Client. It takes calls from the
 * Client and makes Events from it. These Events are serialized and sent to the
 * Server.
 * 
 * @author Christian Lins
 */
class ClientOutput extends EventDispatcherBase implements ServerInterface {

    public ClientOutput(OutputStream out) {
        super(out);
    }

    @Override
    public void createGame(Event event) {
        event.setMethodName("createGame");
        dispatchEvent(event);
    }

    @Override
    public void joinGame(Event event) {
        event.setMethodName("joinGame");
        dispatchEvent(event);
    }

    @Override
    public void joinViewGame(Event event) {
        event.setMethodName("joinViewGame");
        dispatchEvent(event);
    }

    @Override
    public void leaveGame(Event event) {
        event.setMethodName("leaveGame");
        dispatchEvent(event);
    }

    @Override
    public void login1(Event event) {
        event.setMethodName("login1");
        dispatchEvent(event);
    }

    @Override
    public void login2(Event event) {
        event.setMethodName("login2");
        dispatchEvent(event);
    }

    @Override
    public void logout(Event event) {
        event.setMethodName("logout");
        dispatchEvent(event);
    }

    @Override
    public void logoutAll(Event event) {
        event.setMethodName("logoutAll");
        dispatchEvent(event);
    }

    @Override
    public void move(Event event) {
        event.setMethodName("move");
        dispatchEvent(event);
    }

    @Override
    public void placeBomb(Event event) {
        event.setMethodName("placeBomb");
        dispatchEvent(event);
    }

    @Override
    public void sendChatMessage(Event event) {
        event.setMethodName("sendChatMessage");
        dispatchEvent(event);
    }

    @Override
    public void startGame(Event event) {
        event.setMethodName("startGame");
        dispatchEvent(event);
    }

}
