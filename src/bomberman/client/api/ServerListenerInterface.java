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

package bomberman.client.api;

import bomberman.net.Event;

/**
 * Callback interface for the clients.
 * @author Kai Ritterbusch
 * @author Christian Lins
 */
public interface ServerListenerInterface
{

  /**
   * On the playground the Client is currently playing,
   * an explosion has occurred.
   * This methods requests the Client gui to display this explosion.
   * The Event parameter contains three arguments (int x, int y, int distance)
   */
   void explosion(Event event);
  
  /**
   * The server notifies the client player that a player has died.
   * The Event parameter contains three arguments (int x, int y, int playerNumber)
   */
  void playerDied(Event event);
  
  /**
   * The Client receives a chat message.
   * Event parameter contains one argument (String message)
   */
  void receiveChatMessage(Event event);
  
  /**
   * Server acknowledges part one of the CHAP method and requests the
   * client to continue with part 2.
   * @param event
   */
  void continueLogin(Event event);
  
  /**
   * The Client was successfully logged in.
   * Event parameter contains one argument (Session session)
   */
  void loggedIn(Event event);
  
  /**
   * The Client was logged out.
   * Event parameter contains no argument.
   */
  void loggedOut(Event event);
  
  /**
   * Transmittes an updated playground to the client.
   * The Playground is complete, so that no inconsistencies can occur.
   * Event parameter contains one argument (Playground playground).
   */
  void playgroundUpdate(Event event);
  
  /**
   * The server sends an update of the game list.
   * Event parameter contains one argument (List<GameInfo> gameList)
   */
  void gameListUpdate(Event event);
  
  /**
   * Notifies the client that is has joined the game.
   * Event parameter contains one argument (String gameName).
   */
  void gameJoined(Event event);
  
  /**
   * Notifies the Client that the game it was playing in was
   * stopped by the Server due to the given condition.
   * The condition can be one of the following values:
   * <ul>
   *  <li>0: Unknown</li>
   *  <li>1: Game was stopped by admin</li>
   *  <li>2: You won the game</li>
   * </ul>
   * Event parameter contains one argument (int condition).
   */
  void gameStopped(Event event);
  
  /**
   * Is called if the server starts the game this client has joined.
   * Event parameter contains one argument (boolean specStatus).
   */
  void gameStarted(Event event);
 
  /**
   * Updates the user list in the LobbyPanel.
   * Event parameter contains one argument (List<String> users).
   */
  void userListUpdate(Event event);
  
  /**
   * This method is called when players leaves game.
   * Event parameter contains one argument (int playerNumber).
   * TODO: Currently not supported!
   */   
  void playerLeftGame(Event event);
  
  /**
   * This Method is called when Player died and therefore lost the game.
   * Event parameter contains no arguments (void).
   */
  void youDied(Event event);

}
