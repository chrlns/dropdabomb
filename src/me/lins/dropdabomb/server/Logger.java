/*
 *  KC Bomberman
 *  Copyright 2008 Christian Lins <christian.lins@web.de>
 *  Copyright 2008 Kai Ritterbusch <kai.ritterbusch@googlemail.com>
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

package me.lins.dropdabomb.server;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Provides logging methods.
 * @author Kai Ritterbusch (kai.ritterbusch@fh-osnabrueck.de)
 */
public class Logger 
{
  public static final String FILENAME = "server.log";
  
  private PrintStream out = System.out;
    
  public Logger()
  {
    try
    {
      out = new PrintStream(FILENAME);  
    }
    catch(Exception ex)
    {
      System.err.println("Logging disabled due to exception: " + ex.getLocalizedMessage());
    }
  }
  
  /**
   * Adds log message to server.log
   * @param action
   * @param ip
   */
  public void log(String action, String ip)
  {
    // Creates actual Date
    Calendar cal = new GregorianCalendar( TimeZone.getTimeZone("ECT") );
    SimpleDateFormat formater = new SimpleDateFormat();
    String date = formater.format(cal.getTime());
    
    out.println(date + ": " + ip + "->" + action);
    out.flush();
  }
}
