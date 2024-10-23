package gui;

import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

public class CellButtonRendererEditor extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2031353771384496925L;
	private JButton b;
	private int rowSelected;

	public CellButtonRendererEditor() {
		
		b = new JButton();
		b.setText("→");
		b.addActionListener(e -> {
			System.out.println(rowSelected);
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
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		
		return b;
	}

}
