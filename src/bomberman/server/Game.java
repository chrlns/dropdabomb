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

import bomberman.server.api.Session;
import bomberman.server.api.Element;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a game that can be created and started by the users.
 * @author Kai Ritterbusch
 * @author Christian Lins
 */
public class Game implements Serializable
{

  private Session          creator        = null;
  private String           gameName       = null;
  private List<Session>    playerSessions = new ArrayList<Session>();
  private List<Player>     players        = new ArrayList<Player>();
  private List<Session>    spectSessions  = new ArrayList<Session>();
  private Playground       playground;
  private volatile boolean playgroundUpdateRequired = false;
  private boolean          running        = false;
  
  public Game(String name, Session creator)
  {
    this.gameName = name;
    this.creator  = creator;
    this.playground = new Playground(Playground.DEFAULT_WIDTH, Playground.DEFAULT_HEIGHT);
  }
  
  /**
   * Fills up the game with AI-controlled players.
   */
  public void addAI()
  {
    while(players.size() < 4)
      addPlayer(new AIPlayer(this, playground));
  }
  
  /**
   * Adds a spectator to the playground
   * @param session
   */
  public void addSpectator(Session session)
  {
    spectSessions.add(session);
  }
  
  /**
   * Adds a player to the playground
   * @param player
   * @return
   */
  public boolean addPlayer(Player player)
  {
    player.setID(players.size() + 1);
    
    // Adds player to playground view, set starting position
    int x = 0;
    int y = 0;
    if(player.getID() == 1)
    {
      x = 1;
      y = 1;
    }
    else if(player.getID() == 2)
    {
      x = playground.getWidth() - 2;
      y = playground.getHeight() - 2;
    }
    else if(player.getID() == 3)
    {
      x = 1;
      y = playground.getHeight() - 2;      
    }
    else if(player.getID() == 4)
    {
      x = playground.getWidth() - 2;
      y = 1;
    }
    else
      return false; // Game is full
    
    this.playground.setElement(x, y, player.getID(), player);
    player.setPosition(x, y);
    
    players.add(player);
    player.setGame(this);
    System.out.println("Player"+ player.getID() +"added to Playground ("+player.getNickname() +")");
    return true;
  }
  
  /**
   * Returns session of the creator of the Game
   * @return Creator
   */
  public Session getCreator()
  {
    return this.creator;
  }
  
  /**
   * Returns the list of sessions of all players in the game
   * @return player sessions
   */
  public List<Session> getPlayerSessions()
  {
    return this.playerSessions;
  }
  
  /**
   * Returns a list of the sessions of all spectators
   * @return list of all spectator sessions
   */
  public List<Session> getSpectatorSessions()
  {
    return this.spectSessions;
  }
  
  /**
   * Returns number of players in this game (incl. AIPlayers).
   * @return int
   */
  public int getPlayerCount()
  {
    return this.players.size();
  }
  
  /**
   * Forces the server to update the Playground, e.g. when an AI player has moved.
   */
  public void forcePlaygroundUpdate()
  {
    this.playgroundUpdateRequired = true;
  }
  
  /**
   * Return the playground
   * @return playground
   */
  public Playground getPlayground()
  {
    return this.playground;
  }
  
  /**
   * Returns list of all players
   * @return list of players
   */
  public List<Player> getPlayers()
  {
    return this.players;
  }
  
  /**
   * Check if Update of the Playground is required
   * @return true if update is required
   */
  public boolean isPlaygroundUpdateRequired()
  {
    boolean update = this.playgroundUpdateRequired;
    this.playgroundUpdateRequired = false;
    
    return update;
  }
  
  /**
   * Removes player from the playground
   * @param x
   * @param y
   * @param player
   */ 
  public void removePlayer(int x, int y, Player player)
  {  
    if(this.playground.getElement(x, y).equals(player))   // Removes only the selected player 
      this.playground.setElement(x, y, player.getID(), null);
  }
  
  /**
   * Removes player from playerSession list
   * @param session
   */
  public void removePlayer(Session session)
  {
    this.playerSessions.remove(session);
  }
  
  /**
   * Removes player from playerlist
   * @param player
   */
  public void removePlayer(Player player)
  {
    this.players.remove(player);
  }
  
  /**
   * Moves a player in the game's playground if possible.
   * @param player
   * @param dx
   * @param dy
   * @return
   */
  public boolean movePlayer(Player player, int dx, int dy)
  {
    if(dx == 0 && dy == 0)
      return true;
    
    this.playgroundUpdateRequired = true;
    
    // Check if we can move in that direction
    int nx = player.getX() + dx;
    int ny = player.getY() + dy;
    
    if(nx < 0 || ny < 0 
            || this.playground.getWidth() <= nx 
            || this.playground.getHeight() <= ny)
      return false;
    
    Element el = this.playground.getElement(nx, ny)[0];
    if(el == null) // oder Extra
    {  
      // Set old position in Playground to null...
      this.playground.setElement(player.getX(), player.getY(), player.getID(), null);
      // ...and set new position
      player.move(dx, dy);
      this.playground.setElement(player.getX(), player.getY(), player.getID(), player);
      
      return true;
    }
    else if(el instanceof Extra)
    {
      if(el instanceof ExtraBomb)
        player.raiseBombCount();
      else
        player.raiseBombDistance();
      this.playground.setElement(el.getX(), el.getY(), 0, null);
      // Set old position in Playground to null...
      this.playground.setElement(player.getX(), player.getY(), player.getID(), null);
      player.setPosition(nx, ny);
      this.playground.setElement(player.getX(), player.getY(), player.getID(), player);
      
      return true;
    }
    else
      return false;
  }
  
  /**
   * String with gamename
   * @return String with gamename
   */
  @Override
  public String toString()
  {
    return this.gameName;
  }

  /**
   * Set the Playground
   * @param playground
   */
  public void setPlayground(Playground playground) 
  {
    this.playground = playground;
  }

  /**
   * Checks if game is Running
   * @return true if is running
   */
  public boolean isRunning() 
  {
    return running;
  }
  /**
   * Sets game status
   * @param running
   */
  public void setRunning(boolean running) 
  {
    this.running = running;
  }
}
