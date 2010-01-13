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

package bomberman.net;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XppDriver;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;

/**
 * Receives XML serialized Events from an InputStream.
 * @author Christian Lins
 */
public abstract class EventReceiverBase extends Thread
{

  protected InputStream in;
  
  public EventReceiverBase(InputStream in)
  {
    if(!(in instanceof BufferedInputStream))
      in = new BufferedInputStream(in);
    this.in = in;
  }
  
  protected void processEvent(Event event)
  { 
    try
    {
      String methodName = event.getMethodName();
      Class  clazz      = getClass();
      Method method     = clazz.getMethod(methodName, Event.class);
      method.setAccessible(true);
      method.invoke(this, event);
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
    }
  }
  
  /**
   * Waits for incoming messages from the connected Client.
   */
  @Override
  public void run()
  {
    try
    {
      BufferedReader inreader = new BufferedReader(new InputStreamReader(in));
      XStream        xstream  = new XStream(new XppDriver());
      StringBuffer   buffer   = new StringBuffer();

      String line = inreader.readLine();
      while(line != null)
      {
        if(line.equals("END"))
        {
          Event event = (Event)xstream.fromXML(buffer.toString());
          System.out.println(this + " Event received: " + event.getMethodName());
          buffer = new StringBuffer();
          processEvent(event);
        }
        else
        {
          buffer.append(line);
        }
        line = inreader.readLine();
      }
    }
    catch(IOException ex)
    {
      ex.printStackTrace();
    }
  }

}
