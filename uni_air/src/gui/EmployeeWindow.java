package gui;

import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableModel;

import main.Vuelo;

public class EmployeeWindow extends AbstractWindow {
	static final long serialVersionUID = 4922652717777952972L;
	
	 
	public EmployeeWindow() {
		super();
		setTitle("Empleado");
		
		JPanel panel = new JPanel();
		panel.setBorder(createBorder("Empleado"));
		this.add(panel);
		
//		List<Vuelo> vuelos = List.of(
//				new Vuelo("A123", 2.0, 45),
//				new Vuelo("B234", 5.0, 150),
//				new Vuelo("C345", 6.0, 89)
//
//		);
		
		JTable tabla = new JTable();
		panel.add(tabla);
		
		setVisible(true);
		}

	
	
}
