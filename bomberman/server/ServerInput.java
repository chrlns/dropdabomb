/*
 *  KC Bomberman
 *  Copyright (C) 2008-2009 Christian Lins <cli@openoffice.org>
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

import bomberman.server.api.Session;
import bomberman.net.Event;
import bomberman.server.api.ServerInterface;
import bomberman.net.EventReceiverBase;
import java.io.InputStream;

/**
 * Handles the InputStream of a Client's socket. In most aspects this class
 * is an adapter to the bloat Server class.
 * @author Christian Lins
 */
class ServerInput extends EventReceiverBase implements ServerInterface 
{

  private ServerOutput out = null;
  
  public ServerInput(InputStream in, ServerOutput out)
  {
    super(in);
    this.out = out;
  }
  
  public void createGame(Event event) 
  {
    Session session  = (Session)event.getArguments()[0];
    String  gameName = (String)event.getArguments()[1];
    Server.getInstance().createGame(session, gameName);
  }

  public void joinGame(Event event) 
  {
    Session session  = (Session)event.getArguments()[0];
    String  gameName = (String)event.getArguments()[1];
    Server.getInstance().joinGame(session, gameName);
  }

  public void joinViewGame(Event event) 
  {
    Session session  = (Session)event.getArguments()[0];
    String  gameName = (String)event.getArguments()[1];
    Server.getInstance().joinViewGame(session, gameName);
  }

  public void leaveGame(Event event) 
  {
    Session session  = (Session)event.getArguments()[0];
    Server.getInstance().leaveGame(session);
  }

  public void login1(Event event)
  {
    System.out.println("ServerInput.login1(" + event.getArguments()[0] + ")");
    long challenge = Server.getInstance().login1((String)event.getArguments()[0]);
    this.out.continueLogin(new Event(new Object[]{challenge}));
  }

  public void login2(Event event) 
  {
    System.out.println("ServerInput.login2()");
    Server.getInstance().login2(
            (String)event.getArguments()[0], (Long)event.getArguments()[1], this.out);
  }

  public void logout(Event event) 
  {
    Session session = (Session)event.getArguments()[0];
    Server.getInstance().logout(session);
  }

  public void logoutAll(Event event) 
  {
    Server.getInstance().logoutAll();
  }

  public void move(Event event) 
  {
    Session session = (Session)event.getArguments()[0];
    int     x       = (Integer)event.getArguments()[1];
    int     y       = (Integer)event.getArguments()[2];
    Server.getInstance().move(session, x, y);
  }

  public void placeBomb(Event event) 
  {
    Session session = (Session)event.getArguments()[0];
    Server.getInstance().placeBomb(session);
  }

  public void sendChatMessage(Event event) 
  {
    Session session = (Session)event.getArguments()[0];
    String  message = (String)event.getArguments()[1];
    Server.getInstance().sendChatMessage(session, message);
  }

  public void startGame(Event event) 
  {
    Session session  = (Session)event.getArguments()[0];
    String  gameName = (String)event.getArguments()[1];
    Server.getInstance().startGame(session, gameName);
  }

}
