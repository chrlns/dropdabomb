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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.table.AbstractTableModel;

/**
 * Custom TableModel for LobbyPanel, Gamelist
 * 
 * @author Kai Ritterbusch
 */
@SuppressWarnings("serial")
public class GameListTableModel extends AbstractTableModel {

    private final String[]     columnNames = { "Spielname", "Ersteller",
            "Spielerzahl", "Status"       };                              // Spaltennamen
    private List<List<Object>> data        = new ArrayList<List<Object>>(); // Daten
    private final boolean[]    sortColumnDesc;                             // Sortierungsstatus

    public GameListTableModel() {
        this.sortColumnDesc = new boolean[columnNames.length];
    }

    /**
     * Inser new Row into Table
     * 
     * @param row
     */
    public void addRow(List<Object> row) {
        data.add(row);
        this.fireTableRowsInserted(data.size() - 1, data.size() - 1);
    }

    /**
     * Delete specific (y) row
     * 
     * @param y
     */
    public void deleteRow(int y) {
        data.remove(y);
        this.fireTableRowsDeleted(y, y);
    }

    /**
     * Returns size of data
     * 
     * @return int
     */
    @Override
    public int getRowCount() {
        return data.size();
    }

    /**
     * Returns specific Value for a Column at row,colw
     * 
     * @param row
     * @param col
     * @return Object
     */
    @Override
    public Object getValueAt(int row, int col) {
        return data.get(row).get(col);
    }

    /**
     * Returns row (row)
     * 
     * @param row
     * @return Object
     */
    public Object getRow(int row) {
        return data.get(row);
    }

    /**
     * Get Name of Column (TableHeader)
     * 
     * @param col
     * @return
     */
    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    /**
     * Returns Datatype for specific Column
     * 
     * @param c
     * @return
     */
    @Override
    public Class<?> getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    /**
     * Returns size of Columns
     * 
     * @return
     */
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    /**
     * Initialize data for the table
     * 
     * @param data
     */
    public void setData(List<List<Object>> data) {
        this.data = data;
        fireTableDataChanged();
    }

    /**
     * Sets data of Column at (row, col)
     * 
     * @param value
     * @param row
     * @param col
     */
    @Override
    public void setValueAt(Object value, int row, int col) {
        data.get(row).set(col, value);
        fireTableCellUpdated(row, col);
    }

    /**
     * Returns true if Cell is editable
     * 
     * @param row
     * @param col
     * @return
     */
    @Override
    public boolean isCellEditable(int row, int col) {
        return false;
    }

    /**
     * Returns size of Rows
     * 
     * @return
     */
    public List<?> getRows() {
        return data;
    }

    /**
     * Returns sortstate
     * 
     * @param col
     * @return
     */
    public boolean getSortState(int col) {
        return sortColumnDesc[col];
    }

    /**
     * Sort Table column
     * 
     * @param col
     */
    public void sortByColumn(final int col) {
        if (data.size() == 0)
            return;
        Collections.sort(data, new Comparator<List<?>>() {
            @SuppressWarnings({ "rawtypes", "unchecked" })
            @Override
            public int compare(List<?> v1, List<?> v2) {
                int size1 = v1.size();
                if (col >= size1)
                    throw new IllegalArgumentException("Out of Bounds");

                Comparable s1 = (Comparable<?>) v1.get(col);
                Comparable s2 = (Comparable<?>) v2.get(col);

                int cmp = s1.compareTo(s2);
                if (sortColumnDesc[col]) {

                    cmp *= -1;
                }
                return cmp;
            }
        });
        sortColumnDesc[col] ^= true;
    }
}
