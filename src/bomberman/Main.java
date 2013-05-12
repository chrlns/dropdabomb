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

package bomberman;

import bomberman.client.ClientThread;
import bomberman.server.ServerThread;
import bomberman.server.gui.ServerControlPanel;
import bomberman.server.gui.ServerFrame;

/**
 * Main entry point of both Server and Client.
 * 
 * @author Christian Lins
 */
public class Main {
    public static boolean Debugging = false;

    public static void main(String[] args) throws Exception {
        /** Should we show the Server GUI? */
        boolean headlessServer = false;

        /** Should we start a Server? */
        boolean startServer = false;

        /** Should we start a Client? */
        boolean startClient = false;

        String hostname = null;

        for (int n = 0; n < args.length; n++) {
            if (args[n].equals("--client")) {
                startClient = true;
            } else if (args[n].equals("-d") || args[n].equals("--debug")) {
                Debugging = true;
            } else if (args[n].equals("--headless")) {
                headlessServer = true;
            } else if (args[n].equals("--server")) {
                startServer = true;
            } else if (args[n].equals("-h") || args[n].equals("--hostname")) {
                hostname = args[n + 1];
            }
        }

        if (startClient) {
            ClientThread.getInstance().start();
        }

        if (startServer) {
            ServerThread serverThread = new ServerThread(!headlessServer);
            serverThread.start();

            synchronized (serverThread) {
                serverThread.wait();
            }

            if (!headlessServer) {
                new ServerFrame(serverThread).setVisible(true);
                ServerControlPanel.getInstance().addLogMessages(
                        "Bombermanserver bereit ...");
            }
        }

    }
}
