package gui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

import main.Main;
import main.ModeloVuelo;
import main.ModeloVuelo.TipoVentana;

public class AdminWindow extends AbstractWindow {
	private static final long serialVersionUID = 8885137220929508082L;

	public AdminWindow() {
		super();
		setTitle("Admin");
		
		JPanel panel= new JPanel();
		panel.setBorder(createBorder("Admin"));
		JButton btnAnadirVuelo= new JButton("Añadir vuelo");
		ModeloVuelo vuelos= new ModeloVuelo(Main.vuelos, TipoVentana.ADMIN);
		
		JTable tabla = new JTable(vuelos);
		JScrollPane scrollPane = new JScrollPane(tabla);
		
		
		this.add(panel,BorderLayout.NORTH);
		this.add(btnAnadirVuelo,BorderLayout.SOUTH);
		
		this.add(scrollPane);
		
		btnAnadirVuelo.addActionListener((e)->{
			String titulo=JOptionPane.showInputDialog("Introduce el codigo del nuevo vuelo");
			String origen= JOptionPane.showInputDialog("Introduce el aeropuerto origen");
			String destino= JOptionPane.showInputDialog("Introduce el aeropuerto destino");
			
		});
		
		
		
		
		
		setVisible(true);
		
	}

public static void main(String[] args) {
	

	new AdminWindow();
	
	}
	

}
