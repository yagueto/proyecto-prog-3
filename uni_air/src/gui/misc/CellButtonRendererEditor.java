package gui.misc;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class CellButtonRendererEditor extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {

    /**
     *
     */
    private static final long serialVersionUID = -2031353771384496925L;
    private final JButton b;
    private int rowSelected;

    public CellButtonRendererEditor(String text, Clickable c) {

        b = new JButton();
        b.setText(text);
        b.addActionListener(e -> {
            c.onClick(rowSelected);
            fireEditingStopped();
        });
    }

    @Override
    public Object getCellEditorValue() {
        return null;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        rowSelected = row;
        return b;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        return b;
    }

    public interface Clickable {
        void onClick(int row);
    }

}
