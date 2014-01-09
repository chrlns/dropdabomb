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
package me.lins.dropdabomb.util;

/**
 * A generic pair (template) of two objects.
 * 
 * @author Christian Lins
 */
public class Pair<T1, T2> {

    protected T1 t1;
    protected T2 t2;

    /**
     * No-arg constructor required for serialization.
     */
    protected Pair() {
    }

    public Pair(T1 a, T2 b) {
        this.t1 = a;
        this.t2 = b;
    }

    /**
     * @return First object instance of this pair.
     */
    public T1 getA() {
        return this.t1;
    }

    /**
     * @return Second object instance of this pair.
     */
    public T2 getB() {
        return this.t2;
    }

    /**
     * Sets the first object of this pair.
     * 
     * @param a
     */
    public void setA(T1 a) {
        this.t1 = a;
    }

    /**
     * Sets the second object of this pair.
     * 
     * @param b
     */
    public void setB(T2 b) {
        this.t2 = b;
    }
}
