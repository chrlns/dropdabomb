/*
 *  KC Bomberman
 *  Copyright (C) 2008-2011 Christian Lins <christian@lins.me>
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

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import jaxser.Jaxser;

/**
 * Writes Events to an output stream.
 * @author Christian Lins
 */
public class EventDispatcherBase {

	public static final byte[] XML_END = "\nEND\n".getBytes();
	private OutputStream out = null;
	private Jaxser jaxser = new Jaxser();

	public EventDispatcherBase(OutputStream out) {
		if (!(out instanceof BufferedOutputStream)) {
			out = new BufferedOutputStream(out);
		}
		this.out = out;
	}

	/**
	 * This method MUST be synchronized due to heavily asynchronous calls.
	 * Otherwise the XML serialized data could be scattered on client side.
	 * @param event
	 */
	protected synchronized void dispatchEvent(Event event) {
		try {
			this.jaxser.toXML(event, this.out);
			this.out.write(XML_END);
			this.out.flush();
			System.out.println(this + " Event dispatched: " + event.getMethodName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
