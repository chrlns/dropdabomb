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
package me.lins.dropdabomb.net;

import java.io.OutputStream;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;

/**
 * Writes Events to an output stream.
 * 
 * @author Christian Lins
 */
public class EventDispatcherBase {

    private final Output output;
    private final Kryo   kryo = new Kryo();

    public EventDispatcherBase(OutputStream out) {
        this.output = new Output(out);
    }

    /**
     * This method MUST be synchronized due to heavily asynchronous calls.
     * Otherwise the serialized data could be scattered on client side.
     * 
     * @param event
     */
    protected synchronized void dispatchEvent(Event event) {
        try {
            this.kryo.writeObject(output, event);
            this.output.flush();
            System.out.println(this + " Event dispatched: "
                    + event.getMethodName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
