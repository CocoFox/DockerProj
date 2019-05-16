package app;

import javax.swing.table.AbstractTableModel;

/**
 * TableauChaussures
 */
public class TableauChaussures extends AbstractTableModel {

    private static final long serialVersionUID = 1L;

    String[] nomColonnes;
    Object[][] data;

    public TableauChaussures(Object[][] data) {
        this.data = data;
        nomColonnes = new String[] { "ID", "Nom", "Couleur", "Marque", "Style", "Quantité", "Prix" };
    }

    @Override
    public int getColumnCount() {
        return nomColonnes.length;
    }

    @Override
    public String getColumnName(int column) {
        return nomColonnes[column];
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        data[rowIndex][columnIndex] = aValue;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    @Override
    public int getRowCount() {
        return data.length;
    }

    @Override
    public Object getValueAt(int lig, int col) {
        return data[lig][col];
    }
}