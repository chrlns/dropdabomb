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
package me.lins.dropdabomb.server.gui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.table.AbstractTableModel;

/**
 * A custom table model for the user list.
 * 
 * @author <a href="kai.ritterbusch@googlemail.com">Kai Ritterbusch</a>
 */
public class UserListTableModel extends AbstractTableModel {

    private static final long            serialVersionUID = 469656393754490310L;

    private final String[]               columnNames      = { "Username",
            "Status"                                     };
    private ArrayList<ArrayList<Object>> data             = new ArrayList<ArrayList<Object>>();
    private final boolean[]              sortColumnDesc;

    public UserListTableModel() {
        this.sortColumnDesc = new boolean[columnNames.length];
    }

    /**
     * Adds a row to this table model.
     * 
     * @param row
     */
    public void addRow(ArrayList<Object> row) {
        data.add(row);
        this.fireTableRowsInserted(data.size() - 1, data.size() - 1);
    }

    /**
     * Deletes a row from this table model. The row is specified through the
     * given index.
     * 
     * @param idx
     */
    public void deleteRow(int idx) {
        data.remove(idx);
        this.fireTableRowsDeleted(idx, idx);
    }

    /**
     * @return Number of rows.
     */
    @Override
    public int getRowCount() {
        return data.size();
    }

    /**
     * @return Object at the given row and col.
     */
    @Override
    public Object getValueAt(int row, int col) {
        return data.get(row).get(col);
    }

    /**
     * @param row
     * @return Row at specified row index.
     */
    public Object getRow(int row) {
        return data.get(row);
    }

    /**
     * @return Name of the column specified by the given column index.
     */
    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    /**
     * Set row data for username.
     */
    public void setDataForUsername(String username, String strData) {
        for (ArrayList<Object> a : data)
            if (a.get(0).equals(username)) {
                a.set(1, strData);
                int y = data.indexOf(a);
                fireTableCellUpdated(y, 1);
            }
    }

    /**
     * @return Class type of the specified column c.
     */
    @Override
    public Class<?> getColumnClass(int c) {
        if (data.size() == 0)
            return Object.class;
        return getValueAt(0, c).getClass();
    }

    /**
     * @return Number of columns.
     */
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    /**
     * Overwrites this model data with the given data object.
     * 
     * @param data
     */
    public void setData(ArrayList<ArrayList<Object>> data) {
        this.data = data;
    }

    /**
     * Sets a value at the specified index positions.
     */
    @Override
    public void setValueAt(Object value, int row, int col) {
        data.get(row).set(col, value);
        fireTableCellUpdated(row, col);
    }

    /**
     * Always returns false.
     * 
     * @param row
     * @param col
     * @return false
     */
    @Override
    public boolean isCellEditable(int row, int col) {
        return false;
    }

    /**
     * @return All rows of this table model.
     */
    public ArrayList<ArrayList<Object>> getRows() {
        return data;
    }

    /**
     * Returns the sort state of the specified column.
     * 
     * @param col
     * @return
     */
    public boolean getSortState(int col) {
        return sortColumnDesc[col];
    }

    /**
     * Sort this model by the specified column.
     * 
     * @param col
     */
    public void sortByColumn(final int col) {
        if (data.size() == 0)
            return;

        Collections.sort(data, new Comparator<ArrayList<Object>>() {
            @SuppressWarnings("unchecked")
            @Override
            public int compare(ArrayList<Object> v1, ArrayList<Object> v2) {
                int size1 = v1.size();
                if (col >= size1)
                    throw new IllegalArgumentException("Out of Bounds");

                Comparable<Object> s1 = (Comparable<Object>) v1.get(col);
                Comparable<Object> s2 = (Comparable<Object>) v2.get(col);

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
