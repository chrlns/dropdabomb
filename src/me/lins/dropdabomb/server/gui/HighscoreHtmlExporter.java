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

package me.lins.dropdabomb.server.gui;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

import me.lins.dropdabomb.server.Highscore;

/**
 * This class exports a Highscore to a HTML formatted file.
 * @author Christian Lins (christian.lins@web.de)
 */
class HighscoreHtmlExporter 
{
  /**
   * Writes a HTML head.
   * @param out
   */
  static void writeHtmlHead(PrintWriter out)
  {
    out.println("<html>");
    out.println("<head><title>KC Bomberman Highscore Results</title></head>");
    out.println("<body>");
    out.println("<h1>KC Bomberman Highscore</h1>");
  }
  
  /**
   * Writes a HTML tail to the given writer.
   * @param out
   */
  static void writeHtmlTail(PrintWriter out)
  {
    out.println("</body></html>");
  }
  
  /**
   * Exports the given @see{Highscore} to the given OutputStream.
   * The output is formatted in HTML.
   * @param highscore
   * @param out
   * @throws java.io.IOException
   */
  static void export(Highscore highscore, OutputStream out)
    throws IOException
  {
    PrintWriter writer = new PrintWriter(out);
    writeHtmlHead(writer);
    
    // Write table header
    writer.println("<table border=1>");
    writer.println("<tr>");
    writer.print("<td><strong>Name</strong></td>");
    writer.print("<td><strong>Gewonnen</strong></td>");
    writer.print("<td><strong>Verloren</strong></td>");
    writer.println("</tr>");
    
    List<String> names = highscore.getNames();
    for(String name : names)
    {
      writer.println("<tr>");
      // Output name
      writer.print("<td>");
      writer.print(name);
      writer.print("</td>");
      // Output won games
      writer.print("<td>");
      writer.print(highscore.getWonGames(name));
      writer.print("</td>");
      // Output lost games
      writer.print("<td>");
      writer.print(highscore.getLostGames(name));
      writer.print("</td>");
      writer.println("</tr>");
    }
    
    writer.println("</table>");
    writeHtmlTail(writer);
    writer.flush();
    writer.close();
  }
}
