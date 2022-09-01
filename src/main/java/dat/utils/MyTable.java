package dat.utils;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class MyTable extends JTable {
    public MyTable(DefaultTableModel model) {
        super(model);
        autoWidthColumns();
        setPreferredScrollableViewportSize(getPreferredSize());
        changeSelection(0, 0, false, false);
        setAutoCreateRowSorter(true);
        JTableHeader header = getTableHeader();
        ((DefaultTableCellRenderer) header.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
        setRowHeight(30);
        setDefaultEditor(Object.class, null);
        setOpaque(true);
    }

    private void autoWidthColumns() {
        try {
            final TableColumnModel columnModel = getColumnModel();
            for (int column = 0; column < getColumnCount(); column++) {
                int width = 15; // Min width
                for (int row = 0; row < getRowCount(); row++) {
                    TableCellRenderer renderer = getCellRenderer(row, column);
                    Component comp = prepareRenderer(renderer, row, column);
                    width = Math.max(comp.getPreferredSize().width + 1, width);
                }
                if (width > 300)
                    width = 300;
                columnModel.getColumn(column).setPreferredWidth(width);
            }
        } catch (Exception ignored) {
        }
    }

    @Override
    public void setModel(TableModel dataModel) {
        super.setModel(dataModel);
        autoWidthColumns();
    }

    public void hiddenColumn(int columnIndex) {
        getColumnModel().getColumn(columnIndex).setPreferredWidth(0);
        getColumnModel().getColumn(columnIndex).setMinWidth(0);
        getColumnModel().getColumn(columnIndex).setMaxWidth(0);
    }

    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
        Component c = super.prepareRenderer(renderer, row, column);
        if (!isRowSelected(row)) {
            c.setBackground(row % 2 == 0 ? getBackground() : MyColor.light_gray);
        }
        return c;
    }
}
