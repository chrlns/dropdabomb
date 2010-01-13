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

package bomberman.server;

import bomberman.client.api.ServerListenerInterface;
import bomberman.net.Event;
import bomberman.net.EventDispatcherBase;
import java.io.OutputStream;

/**
 * Sends Events to a connected client.
 * @author Christian Lins
 */
class ServerOutput extends EventDispatcherBase implements ServerListenerInterface
{

  public ServerOutput(OutputStream out)
  {
    super(out);
  }
  
  public void continueLogin(Event event)
  {
    System.out.println("ServerOutput.continueLogin()");
    event.setMethodName("continueLogin");
    dispatchEvent(event);
  }
  
  public void explosion(Event event) 
  {
    event.setMethodName("explosion");
    dispatchEvent(event);
  }

  public void gameJoined(Event event) 
  {
    event.setMethodName("gameJoined");
    dispatchEvent(event);
  }

  public void gameListUpdate(Event event) 
  {
    event.setMethodName("gameListUpdate");
    dispatchEvent(event);
  }

  public void gameStarted(Event event) 
  {
    event.setMethodName("gameStarted");
    dispatchEvent(event);
  }

  public void gameStopped(Event event) 
  {
    event.setMethodName("gameStopped");
    dispatchEvent(event);
  }

  public void loggedIn(Event event) 
  {
    event.setMethodName("loggedIn");
    dispatchEvent(event);
  }

  public void loggedOut(Event event) 
  {
    event.setMethodName("loggedOut");
    dispatchEvent(event);
  }

  public void playerDied(Event event) 
  {
    event.setMethodName("playerDied");
    dispatchEvent(event);
  }

  public void playerLeftGame(Event event) 
  {
    event.setMethodName("playerLeftGame");
    dispatchEvent(event);
  }

  public void playgroundUpdate(Event event) 
  {
    event.setMethodName("playgroundUpdate");
    dispatchEvent(event);
  }

  public void receiveChatMessage(Event event) 
  {
    event.setMethodName("receiveChatMessage");
    dispatchEvent(event);
  }

  public void userListUpdate(Event event) 
  {
    event.setMethodName("userListUpdate");
    dispatchEvent(event);
  }

  public void youDied(Event event) 
  {
    event.setMethodName("youDied");
    dispatchEvent(event);
  }

}
