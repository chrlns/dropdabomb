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

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import jaxser.Jaxser;

/**
 * Receives XML serialized Events from an InputStream.
 * @author Christian Lins
 */
public abstract class EventReceiverBase extends Thread {

	protected InputStream in;

	public EventReceiverBase(InputStream in) {
		if (!(in instanceof BufferedInputStream)) {
			in = new BufferedInputStream(in);
		}
		this.in = in;
	}

	protected void processEvent(Event event) {
		try {
			String methodName = event.getMethodName();
			Class clazz = getClass();
			Method method = clazz.getMethod(methodName, Event.class);
			method.setAccessible(true);
			method.invoke(this, event);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Waits for incoming messages from the connected Client.
	 */
	@Override
	public void run() {
		try {
			BufferedReader inreader = new BufferedReader(new InputStreamReader(in));
			Jaxser jaxser = new Jaxser();
			StringBuffer buffer = new StringBuffer();

			String line = inreader.readLine();
			while (line != null) {
				if (line.equals("END")) {
					Event event = (Event)jaxser.fromXML(buffer.toString());
					System.out.println(this + " Event received: " + event.getMethodName());
					buffer = new StringBuffer();
					processEvent(event);
				} else {
					buffer.append(line);
				}
				line = inreader.readLine();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
