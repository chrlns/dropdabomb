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
package me.lins.dropdabomb.server;

import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;

import me.lins.dropdabomb.server.api.ServerInterface;

/**
 * This thread opens a ServerSocket and waits for incoming connections.
 * 
 * @author Christian Lins
 */
public class ServerThread extends Thread {

    public ServerThread(boolean daemon) {
        super("ServerThread");
        setDaemon(daemon);
    }

    /**
     * Run method of this thread. Creates a RMI registry and a new server and
     * waits for the server to exit.
     */
    @Override
    public void run() {
        try {
            // Create Server instance
            Server.getInstance();

            try {
                // Set a shutdown hook that stores the changed highscore and
                // database
                Runtime.getRuntime().addShutdownHook(
                        new ShutdownThread(Server.getInstance().getDatabase(),
                                Server.getInstance().getHighscore()));
            } catch (Exception ex) {
                System.err
                        .println("Could not register shutdown hook due to exception:"
                                + ex.getLocalizedMessage());
                System.err
                        .println("As a result no persistent database/highscore available!");
            }

            // Create server socket
            ServerSocket ssocket = new ServerSocket(
                    ServerInterface.DEFAULT_PORT);

            System.out.println("Bomberman Server running ...");

            // Wait for incoming connections
            for (;;) {
                Socket socket = ssocket.accept();
                ServerOutput output = new ServerOutput(socket.getOutputStream());
                ServerInput input = new ServerInput(socket.getInputStream(),
                        output);
                input.start();
            }
        } catch (Exception ex) {
            if (ex.getCause() instanceof BindException) {
                System.out.println(ex.getLocalizedMessage());
                System.out.println("Server/Registry already running?");

                if (isDaemon()) {
                    JOptionPane.showMessageDialog(null,
                            "Port ist belegt. Server schon am Laufen?",
                            "KCBomberman", JOptionPane.WARNING_MESSAGE);
                }
            } else
                ex.printStackTrace();

            System.exit(1);
        }
    }

    /**
     * Stops and exits this thread. Hopefully, this method is threaf-safe
     * otherwise we could have used the Thread.stop() method which is deprecated
     * for this reason.
     */
    public synchronized void stopThread() {
        try {
            notify();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
