package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.time.LocalDateTime;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.TableColumn;

import main.Main;
import main.ModeloVuelo;
import main.Vuelo;
import main.ModeloVuelo.TipoVentana;
import main.Vuelo;

public class UserWindow extends AbstractWindow {
	private static final long serialVersionUID = 7345092960587394070L;

	/*
	 * Ventana para el cliente, incluye lista de vuelos y búsqueda
	 */
	public UserWindow() {
		super();
		JTabbedPane tabPane = new JTabbedPane();

		JSplitPane flightHistoryPanel = new FlightHistoryPanel();
		JPanel flightSearchPanel = new FlightSearchPanel();

		tabPane.addTab("Mis Vuelos", flightHistoryPanel);
		tabPane.addTab("Buscar vuelo", flightSearchPanel);

		//JTable table = new JTable();
		//flightHistoryPanel.add(table);

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
		JTextField datePicker = new JTextField(8); // TODO: se podría añadir un datePicker? añadir al menos lógica de
													// comprobación

		JButton b = new JButton("Buscar"); // TODO: implementar

		filtersPanel.add(origenField);
		filtersPanel.add(destField);
		filtersPanel.add(datePicker);
		filtersPanel.add(b);
		this.add(filtersPanel, BorderLayout.NORTH);

		// JTable para mostrar resultados
		ModeloVuelo modeloVuelo = new ModeloVuelo(Vuelo.getVuelos(), TipoVentana.USER);
		JTable tabla = new JTable(modeloVuelo);

		TableColumn c = tabla.getColumnModel().getColumn(5);
		CellButtonRendererEditor cellButtonRendererEditor = new CellButtonRendererEditor((int row) -> {
			new BuyWindow(Vuelo.getVuelos().get(row));
		});
		c.setCellEditor(cellButtonRendererEditor);
//		c.setCellRenderer(cellButtonRendererEditor);

		JScrollPane scrollPane = new JScrollPane(tabla);

		// TEST: para actualizar la tabla al hacer la búsqueda
//		b.addActionListener((e) -> {
//			Main.vuelos.add(new Vuelo("a", "aa", "AAA", LocalDateTime.now(), LocalDateTime.now(), 1, 1L));
//			modeloVuelo.fireTableDataChanged();
//		});

		this.add(scrollPane);
	}
}

/**
 * Panel para mostrar los vuelos comprados por el usuario
 */
class FlightHistoryPanel extends JSplitPane {

	/**
	 * 
	 */
	JList<Vuelo> lista_vuelos = new JList<>();

	// En vez de un Label lo cambiaré para incluir mejor toda la información del
	// vuelo
	JLabel label = new JLabel();

	JPanel panel = new JPanel();
	// JSplitPane panelDividido = new JSplitPane();

	private static final long serialVersionUID = 560276176324109821L;

	public FlightHistoryPanel() {
		DefaultListModel<Vuelo> modelo = new DefaultListModel<Vuelo>();

		lista_vuelos.setCellRenderer(new VueloListRenderer());
		// Ponemos el modelo para la lista de vuelos
		lista_vuelos.setModel(modelo);

		// Creamos 10 vuelos añadidos al modelo
		// Para Obtener/Seleccionar un vuelo de la lista de vuelos
		lista_vuelos.getSelectionModel().addListSelectionListener(e -> {
			// Cogemos un vuelo
			Vuelo v = lista_vuelos.getSelectedValue();
			/*
			 * Ponemos los datos del Vuelo que queremos que aparezcan a la derecha de la
			 * pantalla al seleccionar el vuelo que queremos ver
			 */
			label.setText("Origen: " + v.getOrigen() + " // Destino: " + v.getDestino());
		});

		// Añadimos a la izquierda del panel dividido un panel de scroll con la lista
		this.setLeftComponent(new JScrollPane(lista_vuelos));

		// Añadimos el label que contiene la info de origen y destino del vuelo al panel
		// y
		// el mismo luego al panel dividido de la derecha
		panel.add(label);
		this.setRightComponent(panel);
		this.setBackground(Color.BLACK);
		//add(panelDividido);
		
		Thread t = new Thread(() -> {
			SwingUtilities.invokeLater(() -> {
				modelo.addAll(Vuelo.getVuelos());
				lista_vuelos.ensureIndexIsVisible(modelo.getSize());
			});
			
		});
		t.start();

	}
	class VueloListRenderer extends DefaultListCellRenderer{

		private static final long serialVersionUID = 1L;

		@Override
		public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
				boolean cellHasFocus) {
			VueloListRenderer c = (VueloListRenderer) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			Vuelo v = (Vuelo) value;
			c.setText(Integer.toString(v.getCodigo()));
			return c;
		}
		
		
		
		
		
	}
	
}