/*
 *  DropDaBomb
 *  Copyright (C) 2008-2013 Christian Lins <christian@lins.me>
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
package me.lins.dropdabomb.client.io;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.swing.ImageIcon;

/**
 * Static methods for loading resources.
 * 
 * @author Kai Ritterbusch
 * @author Christian Lins
 */
public class Resource {

    public static byte[] getBytes(File file) {
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            byte[] buffer = new byte[(int) file.length()];

            in.read(buffer);

            return buffer;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Loads Image
     * 
     * @param name
     * @return null if loading Image fails
     */
    public static ImageIcon getImage(String name) {
        URL url = Thread.currentThread().getContextClassLoader()
                .getResource(name);

        if (url == null) {
            Image img = Toolkit.getDefaultToolkit().createImage(name);
            return new ImageIcon(img);
        }

        return new ImageIcon(url);
    }

    /**
     * Loads resource an creates reference
     * 
     * @return
     */
    public static URL getAsURL(String name) {
        return Thread.currentThread().getContextClassLoader().getResource(name);
    }

    /**
     * Loads resource and creates Inputstream
     * 
     * @param name
     * @return
     */
    public static InputStream getAsStream(String name) {
        try {
            URL url = getAsURL(name);
            return url.openStream();
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
