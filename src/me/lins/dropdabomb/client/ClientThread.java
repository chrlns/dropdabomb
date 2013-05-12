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

import java.awt.AWTEvent;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.ConnectException;

import me.lins.dropdabomb.client.gui.MainFrame;
import me.lins.dropdabomb.server.api.ServerInterface;
import me.lins.dropdabomb.server.api.Session;

/**
 * ClientThread starts a Thread for each Client
 * @author Kai Ritterbusch
 * @author Christian Lins
 */
public class ClientThread extends Thread
{
  
  private static ClientThread instance = new ClientThread();
  
  /**
   * This method is not synchronized as the instance is created at class
   * initialization.
   * @return
   */
  public static ClientThread getInstance()
  {
    return instance;
  }
  
  public ServerInterface Server;
  public ClientInput     ServerListener;
  public Session         Session;
  
  private String hostname = "localhost";
  private int    port     = ServerInterface.DEFAULT_PORT;
  
  private ClientThread()
  {
  }
  
  public void connect(String hostname)
    throws ConnectException, IOException, UnknownHostException
  {
    String[] hp = hostname.split(":");
    if(hp[0].length() > 0)
      this.hostname = hp[0];
    if(hp.length > 1)
      this.port = Integer.parseInt(hp[1]);
    
    // Connect to server
    Socket socket  = new Socket(this.hostname, this.port);
    Server         = new ClientOutput(socket.getOutputStream());
    ServerListener = new ClientInput(socket.getInputStream());
    ServerListener.start();
  }
  
  @Override
  public void run()
  {
    try
    {
      Toolkit.getDefaultToolkit().getSystemEventQueue().push(
        new EventQueue()
        {
          @Override
          protected void dispatchEvent(AWTEvent event) 
          {
            if (event instanceof KeyEvent) 
            {
              keyEvent((KeyEvent)event);     
            }
            super.dispatchEvent(event); 
          }
        });
      
      // Create main frame
      new MainFrame().setVisible(true);
    }
    catch (Exception ex) 
    {
      ex.printStackTrace();
    }
  }
  
  /**
   * Processes ALL KeyEvents the MainFrame receives.
   * @param event
   */
  private void keyEvent(KeyEvent event)
  {
    if(event.getID() == KeyEvent.KEY_PRESSED)
    {
      Container cnt = MainFrame.getInstance().getContentPane();
      if(cnt instanceof KeyListener)
      {
        ((KeyListener)cnt).keyPressed(event);
      }
    }
  }

}
