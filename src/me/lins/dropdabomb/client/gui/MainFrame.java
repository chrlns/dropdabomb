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
package me.lins.dropdabomb.client.gui;

import java.awt.Container;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.URL;

import javax.swing.JFrame;

import me.lins.dropdabomb.client.AudioThread;
import me.lins.dropdabomb.client.ClientThread;
import me.lins.dropdabomb.client.io.Resource;
import me.lins.dropdabomb.net.Event;

/**
 * The main application window.
 * 
 * @author Kai Ritterbusch
 * @author Christian Lins
 */
@SuppressWarnings("serial")
public class MainFrame extends JFrame {
    private static MainFrame instance = null;

    public static MainFrame getInstance() {
        return instance;
    }

    protected LobbyPanel lobbyPanel = new LobbyPanel();
    protected StartPanel startPanel = new StartPanel();

    public MainFrame() {
        instance = this;
        setTitle("DropDaBomb - A Free Java B*mberm*n Clone");
        resetSize();
        showStartPanel();
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        WindowListener listener = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent w) {
                try {
                    if (ClientThread.getInstance().Session != null) {
                        System.out.println("Send logout message to server...");
                        ClientThread.getInstance().Server
                                .logout(new Event(new Object[] { ClientThread
                                        .getInstance().Session }));
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };
        this.addWindowListener(listener);

        URL sound = Resource.getAsURL("res/sfx/battle.wav");
        if (sound != null) {
            AudioThread.playSound(sound);
        }
    }

    public void resetSize() {
        setSize(640, 500);
    }

    /**
     * Sets the ContentPane of the mainframe
     * 
     * @param cnt
     */
    @Override
    public void setContentPane(Container cnt) {
        super.setContentPane(cnt);
        repaint();
    }

    /**
     * One Instance of the LobbyPanel for the whole game.
     * 
     * @return instance of LobbyPanel
     */
    public LobbyPanel getLobbyPanel() {
        return lobbyPanel;
    }

    public void showStartPanel() {
        setContentPane(this.startPanel);
    }

    @Override
    public void setVisible(boolean state) {
        if (!state) {
            instance = null;
        }
        super.setVisible(state);
    }

}
