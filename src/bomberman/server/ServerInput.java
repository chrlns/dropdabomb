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
package bomberman.server;

import java.io.InputStream;

import bomberman.net.Event;
import bomberman.net.EventReceiverBase;
import bomberman.server.api.ServerInterface;
import bomberman.server.api.Session;

/**
 * Handles the InputStream of a Client's socket. In most aspects this class is
 * an adapter to the bloat Server class.
 * 
 * @author Christian Lins
 */
class ServerInput extends EventReceiverBase implements ServerInterface {

    private ServerOutput out = null;

    public ServerInput(InputStream in, ServerOutput out) {
        super(in);
        this.out = out;
    }

    @Override
    public void createGame(Event event) {
        Session session = (Session) event.getArguments()[0];
        String gameName = (String) event.getArguments()[1];
        Server.getInstance().createGame(session, gameName);
    }

    @Override
    public void joinGame(Event event) {
        Session session = (Session) event.getArguments()[0];
        String gameName = (String) event.getArguments()[1];
        Server.getInstance().joinGame(session, gameName);
    }

    @Override
    public void joinViewGame(Event event) {
        Session session = (Session) event.getArguments()[0];
        String gameName = (String) event.getArguments()[1];
        Server.getInstance().joinViewGame(session, gameName);
    }

    @Override
    public void leaveGame(Event event) {
        Session session = (Session) event.getArguments()[0];
        Server.getInstance().leaveGame(session);
    }

    @Override
    public void login1(Event event) {
        System.out.println("ServerInput.login1(" + event.getArguments()[0]
                + ")");
        long challenge = Server.getInstance().login1(
                (String) event.getArguments()[0]);
        this.out.continueLogin(new Event(new Object[] { challenge }));
    }

    @Override
    public void login2(Event event) {
        System.out.println("ServerInput.login2()");
        Server.getInstance().login2((String) event.getArguments()[0],
                (Long) event.getArguments()[1], this.out);
    }

    @Override
    public void logout(Event event) {
        Session session = (Session) event.getArguments()[0];
        Server.getInstance().logout(session);
    }

    @Override
    public void logoutAll(Event event) {
        Server.getInstance().logoutAll();
    }

    @Override
    public void move(Event event) {
        Session session = (Session) event.getArguments()[0];
        int x = (Integer) event.getArguments()[1];
        int y = (Integer) event.getArguments()[2];
        Server.getInstance().move(session, x, y);
    }

    @Override
    public void placeBomb(Event event) {
        Session session = (Session) event.getArguments()[0];
        Server.getInstance().placeBomb(session);
    }

    @Override
    public void sendChatMessage(Event event) {
        Session session = (Session) event.getArguments()[0];
        String message = (String) event.getArguments()[1];
        Server.getInstance().sendChatMessage(session, message);
    }

    @Override
    public void startGame(Event event) {
        Session session = (Session) event.getArguments()[0];
        String gameName = (String) event.getArguments()[1];
        Server.getInstance().startGame(session, gameName);
    }

}
