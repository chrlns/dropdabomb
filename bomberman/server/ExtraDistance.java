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

package bomberman.server;

/**
 * Extra: raise Bombdistance
 * @author Kai Ritterbusch (kai.ritterbusch@fh-osnabrueck.de)
 */
class ExtraDistance extends Extra
{
  public static final String IMAGE_FILENAME = "resource/gfx/extras/bombDistance.png";
  
  public ExtraDistance(int x, int y)
  {
    super(x,y);
  }
  
   /**
   * Gets the filename of an Image
   * @return Filename
   */
  public String getImageFilename()
  {
    return IMAGE_FILENAME;
  }
}
