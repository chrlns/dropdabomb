/*
 *  KC Bomberman
 *  Copyright (C) 2008,2009 Christian Lins <cli@openoffice.org>
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
 * Client and makes Events from it. These Events are sent to the Server.
 * @author Christian Lins
 */
class ClientOutput extends EventDispatcherBase implements ServerInterface
{

  public ClientOutput(OutputStream out)
  {
    super(out);
  }
  
  public void createGame(Event event)
  {
    event.setMethodName("createGame");
    dispatchEvent(event);
  }

  public void joinGame(Event event)
  {
    event.setMethodName("joinGame");
    dispatchEvent(event);
  }

  public void joinViewGame(Event event)
  {
    event.setMethodName("joinViewGame");
    dispatchEvent(event);
  }

  public void leaveGame(Event event)
  {
    event.setMethodName("leaveGame");
    dispatchEvent(event);
  }

  public void login1(Event event) 
  {
    event.setMethodName("login1");
    dispatchEvent(event);
  }

  public void login2(Event event)
  {
    event.setMethodName("login2");
    dispatchEvent(event);
  }

  public void logout(Event event)
  {
    event.setMethodName("logout");
    dispatchEvent(event);
  }

  public void logoutAll(Event event)
  {
    event.setMethodName("logoutAll");
    dispatchEvent(event);
  }

  public void move(Event event)
  {
    event.setMethodName("move");
    dispatchEvent(event);
  }

  public void placeBomb(Event event)
  {
    event.setMethodName("placeBomb");
    dispatchEvent(event);
  }

  public void sendChatMessage(Event event)
  {
    event.setMethodName("sendChatMessage");
    dispatchEvent(event);
  }

  public void startGame(Event event)
  {
    event.setMethodName("startGame");
    dispatchEvent(event);
  }

}
