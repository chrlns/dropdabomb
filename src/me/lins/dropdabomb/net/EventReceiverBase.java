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

import java.io.InputStream;
import java.lang.reflect.Method;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;

/**
 * Receives serialized Events from an InputStream.
 * 
 * @author Christian Lins
 */
public abstract class EventReceiverBase extends Thread {

    protected InputStream in;
    protected Input       input;
    protected Kryo        kryo = new Kryo();

    public EventReceiverBase(InputStream in) {
        this.input = new Input(in);
    }

    protected void processEvent(Event event) {
        try {
            String methodName = event.getMethodName();
            Class<? extends EventReceiverBase> clazz = getClass();
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
            while (!input.eof()) {
                Event event = this.kryo.readObject(input, Event.class);
                processEvent(event);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
