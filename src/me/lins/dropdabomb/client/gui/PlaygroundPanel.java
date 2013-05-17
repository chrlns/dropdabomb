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

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.ImageCapabilities;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.VolatileImage;

import javax.swing.JPanel;

import me.lins.dropdabomb.client.ClientThread;
import me.lins.dropdabomb.net.Event;
import me.lins.dropdabomb.server.Playground;
import me.lins.dropdabomb.server.api.Element;


/**
 * Panel that displays a game's playground. The client receives changes from the
 * server and displays this changes on a PlaygroundPanel.
 * 
 * @author Kai Ritterbusch
 * @author Christian Lins
 */
@SuppressWarnings("serial")
public class PlaygroundPanel extends JPanel implements KeyListener {

    private final ElementPainter[][] elementPainter;
    private boolean                  spectatorStatus = false;
    private VolatileImage            buffer;
    private Graphics                 bufferGraphics;

    public PlaygroundPanel(int cols, int rows, boolean spectatorStatus) {
        setBackground(Color.BLACK);
        this.spectatorStatus = spectatorStatus;
        GridBagConstraints gbc = new GridBagConstraints();
        GridBagLayout gbl = new GridBagLayout();
        setLayout(gbl);
        gbc.fill = GridBagConstraints.BOTH;

        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(1, 1, 1, 1);

        this.elementPainter = new ElementPainter[cols][rows];
        gbl.setConstraints(this, gbc);

        for (int y = 0; y < rows; y++) {
            gbc.gridy = y;
            for (int x = 0; x < cols; x++) {
                gbc.gridx = x;
                this.elementPainter[x][y] = new ElementPainter();
                add(this.elementPainter[x][y], gbc);
            }
        }

        setDoubleBuffered(false);
        MainFrame.getInstance().setSize(
                (cols + 2) * ElementPainter.DEFAULT_SIZE,
                (rows + 2) * ElementPainter.DEFAULT_SIZE);

        MainFrame.getInstance().repaint();
    }

    /**
     * Draws the die animation
     * 
     * @param x
     * @param y
     * @param playerNumber
     */
    public void drawDieAnimation(int x, int y, int playerNumber) {
        int explPeriod = 150;
        this.elementPainter[x][y].newDieAnimation(0, explPeriod, playerNumber);
    }

    /**
     * Draws explosion animation
     * 
     * @param x
     * @param y
     * @param distance
     */
    public void drawExplosion(int x, int y, int distance) {
        int explPeriod = 150;
        this.elementPainter[x][y].newExplosion(0, explPeriod);

        boolean skipxp = false;
        boolean skipxm = false;
        boolean skipyp = false;
        boolean skipym = false;

        for (int i = 1; i <= distance; i++) {
            if (x + i < this.elementPainter.length && !skipxp) {
                this.elementPainter[x + i][y].newExplosion(i * explPeriod,
                        explPeriod);
                if (this.elementPainter[x + i][y].getElement() != null)
                    skipxp = true;
            }
            if (x - i >= 0 && !skipxm) {
                this.elementPainter[x - i][y].newExplosion(i * explPeriod,
                        explPeriod);
                if (this.elementPainter[x - i][y].getElement() != null)
                    skipxm = true;
            }
            if (y + i < this.elementPainter[0].length && !skipyp) {
                this.elementPainter[x][y + i].newExplosion(i * explPeriod,
                        explPeriod);
                if (this.elementPainter[x][y + i].getElement() != null)
                    skipyp = true;
            }
            if (y - i >= 0 && !skipym) {
                this.elementPainter[x][y - i].newExplosion(i * explPeriod,
                        explPeriod);
                if (this.elementPainter[x][y - i].getElement() != null)
                    skipym = true;
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent event) {
    }

    @Override
    public void keyReleased(KeyEvent event) {
    }

    /**
     * Reacts on player actions.
     * 
     * @param event
     */
    @Override
    public void keyPressed(KeyEvent event) {
        try {
            // do nothing if the calling client is a Spectator
            if (spectatorStatus == true) {
                if (event.getKeyCode() == KeyEvent.VK_ESCAPE)
                    ClientThread.getInstance().ServerListener
                            .playerLeftGame(new Event(new Object[] {}));
                return;
            }
            switch (event.getKeyCode()) {
            case KeyEvent.VK_UP: {
                ClientThread.getInstance().Server.move(new Event(new Object[] {
                        ClientThread.getInstance().Session, 0, -1 }));
                break;
            }
            case KeyEvent.VK_DOWN: {
                ClientThread.getInstance().Server.move(new Event(new Object[] {
                        ClientThread.getInstance().Session, 0, +1 }));
                break;
            }
            case KeyEvent.VK_LEFT: {
                ClientThread.getInstance().Server.move(new Event(new Object[] {
                        ClientThread.getInstance().Session, -1, 0 }));
                break;
            }
            case KeyEvent.VK_RIGHT: {
                ClientThread.getInstance().Server.move(new Event(new Object[] {
                        ClientThread.getInstance().Session, +1, 0 }));
                break;
            }
            case KeyEvent.VK_SPACE: {
                ClientThread.getInstance().Server.placeBomb(new Event(
                        new Object[] { ClientThread.getInstance().Session }));
                break;
            }
            case KeyEvent.VK_ESCAPE: {
                ClientThread.getInstance().Server.leaveGame(new Event(
                        new Object[] { ClientThread.getInstance().Session }));
                ClientThread.getInstance().ServerListener
                        .playerLeftGame(new Event(new Object[] {}));
                break;
            }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (bufferGraphics == null) {
            if (buffer == null) {
                try {
                    buffer = createVolatileImage(getWidth(), getHeight(),
                            new ImageCapabilities(true));
                } catch (AWTException ex) {
                    ex.printStackTrace();
                }
            }
            bufferGraphics = buffer.createGraphics();
        }

        super.paintComponent(bufferGraphics);
        g.drawImage(buffer, 0, 0, null);
    }

    public void updatePlaygroundView(Playground playground) {
        int cols = playground.getColumns();
        int rows = playground.getRows();

        for (int x = 0; x < cols; x++) {
            for (int y = 0; y < rows; y++) {
                Element[] newElements = playground.getElement(x, y);
                elementPainter[x][y].setElement(newElements);
            }
        }

        repaint();
    }

}
