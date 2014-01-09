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
package me.lins.dropdabomb.client;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Thread playing a AudioClip.
 * 
 * @author Christian Lins
 */
public class AudioThread extends Thread {

    private static volatile AudioThread instance = new AudioThread();

    public static void playSound(URL url) {
        if (url == null) {
            return;
        }

        try {
            AudioThread.instance.queue.put(url);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    /*
     * The queue has a maximum capacity of Integer.MAX_VALUE, so it will
     * probably never block at put operation, but block on take if empty.
     */
    private final BlockingQueue<URL> queue = new LinkedBlockingQueue<URL>();

    private AudioThread() {
        setPriority(MAX_PRIORITY);
        start();
    }

    /**
     * Plays the sound
     */
    @Override
    public void run() {
        for (;;) {
            try {
                URL url = this.queue.take();
                AudioClip clip = Applet.newAudioClip(url);
                clip.play();

                /*
                 * This is a workaround to prevent multiple clips from starting
                 * at exactly the same time, which causes garbage on some audio
                 * systems. Increase the sleep time if necessary.
                 */
                Thread.sleep(100);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

}
