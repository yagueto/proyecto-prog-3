package gui;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

import main.ModeloVuelo;
import main.ModeloVuelo.TipoVentana;
import main.Vuelo;

public class EmployeeWindow extends AbstractWindow {
	private static final long serialVersionUID = 4922652717777952972L;
	
	 
	public EmployeeWindow() {
		super();
		setTitle("Empleado");
		
		JPanel panel = new JPanel();
		panel.setBorder(createBorder("Empleado"));
		this.add(panel, BorderLayout.NORTH);
		
		ModeloVuelo vuelos = new ModeloVuelo(Vuelo.getVuelos(), TipoVentana.EMPLOYEE);
		JTable tabla = new JTable(vuelos);
		
		TableColumn tableColumn = tabla.getColumnModel().getColumn(6);
		
		tableColumn.setCellEditor(new CellButtonRendererEditor((int clickedRow) -> {
			// TODO: implementar onClick
		}));
		// tableColumn.setCellRenderer(new CellButtonRendererEditor());
		
		JScrollPane scrollPane = new JScrollPane(tabla);
		this.add(scrollPane);
		

		setVisible(true);
		}
	
	
	

	
	
}
