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
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Writes Events to an output stream.
 * @author Christian Lins
 */
public class EventDispatcherBase 
{

  public static final String XML_END = "\nEND\n";
  
  private OutputStream  out     = null;
  private XStream       xstream = new XStream(new DomDriver());
  
  public EventDispatcherBase(OutputStream out)
  {
    if(!(out instanceof BufferedOutputStream))
      out = new BufferedOutputStream(out);
    this.out = out;
  }
  
  /**
   * This method MUST be synchronized due to heavily asynchronous calls.
   * Otherwise the XML serialized data could be scattered on client side.
   * @param event
   */
  protected synchronized void dispatchEvent(Event event)
  {
    try
    {
      this.xstream.toXML(event, this.out);
      this.out.write(XML_END.getBytes());
      this.out.flush();
      System.out.println(this + " Event dispatched: " + event.getMethodName());
    }
    catch(IOException ex)
    {
      ex.printStackTrace();
    }
  }

}
