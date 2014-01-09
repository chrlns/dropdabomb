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
package me.lins.dropdabomb;

import me.lins.dropdabomb.client.ClientThread;
import me.lins.dropdabomb.server.ServerThread;
import me.lins.dropdabomb.server.gui.ServerControlPanel;
import me.lins.dropdabomb.server.gui.ServerFrame;

/**
 * Main entry point of both Server and Client.
 * 
 * @author Christian Lins
 */
public class Main {
    public static final String VERSION   = "2.0.0";

    public static boolean      Debugging = false;

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            printArguments();
            return;
        }

        // Should we show the Server GUI?
        boolean headlessServer = false;

        // Should we start a Server?
        boolean startServer = false;

        // Should we start a Client?
        boolean startClient = false;

        for (int n = 0; n < args.length; n++) {
            if (args[n].equals("--client")) {
                startClient = true;
            } else if (args[n].equals("-d") || args[n].equals("--debug")) {
                Debugging = true;
            } else if (args[n].equals("--headless")) {
                headlessServer = true;
            } else if (args[n].equals("--server")) {
                startServer = true;
            }
        }

        if (startClient) {
            ClientThread.getInstance().start();
        }

        if (startServer) {
            ServerThread serverThread = new ServerThread(!headlessServer);
            serverThread.start();

            if (!headlessServer) {
                new ServerFrame(serverThread).setVisible(true);
                ServerControlPanel.getInstance().addLogMessages(
                        "Server ready...");
            }

            synchronized (serverThread) {
                serverThread.wait();
            }
        }
    }

    private static void printArguments() {
        System.out.println("Options:");
        System.out.println(" --client\tStart the Client GUI");
        System.out
                .println(" --server\tStart the Game server (including its GUI)");
        System.out.println(" --headless\tSuppress the Server GUI");
        System.out.println("All options can be used in conjunction, e.g.");
        System.out.println("\tdropdabomb --client --server --headless");
        System.out.println("starts the game in single player mode.");
    }
}
