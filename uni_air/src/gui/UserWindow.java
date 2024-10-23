package gui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableColumn;

import main.Main;
import main.ModeloVuelo;

public class UserWindow extends AbstractWindow {
	private static final long serialVersionUID = 7345092960587394070L;

	/*
	 * Ventana para el cliente, incluye lista de vuelos y búsqueda
	 */
	public UserWindow() {
		super();
		JTabbedPane tabPane = new JTabbedPane();

		JPanel flightHistoryPanel = new FlightHistoryPanel();
		JPanel flightSearchPanel = new FlightSearchPanel();

		tabPane.addTab("Mis Vuelos", flightHistoryPanel);
		tabPane.addTab("Buscar vuelo", flightSearchPanel);

		JTable table = new JTable();

		flightHistoryPanel.add(table);

		tabPane.setBorder(createBorder("Cliente"));
		add(tabPane);

		this.setVisible(true);
	}

}

/**
 * Panel de búsqueda de vuelos
 */
class FlightSearchPanel extends JPanel {

	/**
	 * Random generated UID
	 */
	private static final long serialVersionUID = 4735413587006131088L;

	public FlightSearchPanel() {
		this.setLayout(new BorderLayout());

		// Panel para campos de filtrado de resultados
		JPanel filtersPanel = new JPanel();
		filtersPanel.setBackground(new Color(255, 0, 0)); // DEBUG: añadido color para visibilidad

		JTextField origenField = new JTextField(10); // TODO: se podría añadir un placeholder?
		JTextField destField = new JTextField(10);
		JTextField datePicker = new JTextField(8); 	// TODO: se podría añadir un datePicker? añadir al menos lógica de
													// comprobación

		JButton b = new JButton("Buscar"); // TODO: implementar

		filtersPanel.add(origenField);
		filtersPanel.add(destField);
		filtersPanel.add(datePicker);
		filtersPanel.add(b);
		this.add(filtersPanel, BorderLayout.NORTH);

		// JTable para mostrar resultados
		ModeloVuelo modeloVuelo = new ModeloVuelo(Main.vuelos);
		JTable tabla = new JTable(modeloVuelo);
		
		TableColumn c = tabla.getColumnModel().getColumn(5);
		c.setCellEditor(new CellButtonRendererEditor());
		c.setCellRenderer(new CellButtonRendererEditor());
		
		JScrollPane scrollPane = new JScrollPane(tabla);
		this.add(scrollPane);
	}
}

/**
 * Panel para mostrar los vuelos comprados por el usuario
 */
class FlightHistoryPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 560276176324109821L;

	public void FlightSearchPanel() {
		// TODO
	}
}