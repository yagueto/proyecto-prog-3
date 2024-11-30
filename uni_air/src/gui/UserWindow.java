package gui;

import db.FlightDAO;
import domain.ModeloVuelo;
import domain.ModeloVuelo.TipoVentana;
import domain.Vuelo;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.List;

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

    private static JProgressBar progressBar;
    private static JButton searchButton;

    private static ModeloVuelo modeloVuelo;

    public FlightSearchPanel() {
        this.setLayout(new BorderLayout());

        // Panel para campos de filtrado de resultados
        JPanel filtersPanel = createSearchPanel();
        this.add(filtersPanel, BorderLayout.NORTH);

        // JTable para mostrar resultados
        modeloVuelo = new ModeloVuelo(List.of(), TipoVentana.USER);
        JTable tabla = new JTable(modeloVuelo);

        TableColumn c = tabla.getColumnModel().getColumn(5);
        CellButtonRendererEditor cellButtonRendererEditor = new CellButtonRendererEditor((int row) -> new BuyWindow(modeloVuelo.getVuelos().get(row)));
        c.setCellEditor(cellButtonRendererEditor);
//		c.setCellRenderer(cellButtonRendererEditor);

        JScrollPane scrollPane = new JScrollPane(tabla);

        // TEST: para actualizar la tabla al hacer la búsqueda
//		b.addActionListener((e) -> {
//			modeloVuelo.fireTableDataChanged();
//		});

        this.add(scrollPane);
    }

    private static JPanel createSearchPanel() {
        JPanel filtersPanel = new JPanel();
        JPanel buttonsPanel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(filtersPanel, BoxLayout.Y_AXIS);
        filtersPanel.setLayout(boxLayout);

        buttonsPanel.setBackground(UIManager.getColor("ProgressBar.foreground"));

        JTextField origenField = new JTextField(10); // TODO: se podría añadir un placeholder?
        JTextField destField = new JTextField(10);
        JTextField datePicker = new JTextField(8); // TODO: se podría añadir un datePicker? añadir al menos lógica de

        searchButton = new JButton("Buscar");

        progressBar = new JProgressBar();
        progressBar.setIndeterminate(false);
        progressBar.setBorderPainted(false);

        buttonsPanel.add(origenField);
        buttonsPanel.add(destField);
        buttonsPanel.add(datePicker);
        buttonsPanel.add(searchButton);
        filtersPanel.add(buttonsPanel, BorderLayout.NORTH);
        filtersPanel.add(progressBar, BorderLayout.SOUTH);

        searchButton.addActionListener(e -> {
            Thread t = new Thread(() -> {
                updateTableData();
            });
            t.start();

        });

        return filtersPanel;
    }


    /**
     * Carga datos de la búsqueda de vuelos en la tabla
     */
    private static void updateTableData() {
        SwingUtilities.invokeLater(() -> {
            progressBar.setIndeterminate(true);
            searchButton.setEnabled(false);
        });

        // TODO: DEBUG: Temporal, mientras no existe aún la búsqueda de vuelos
        //Airport origen = AirportDAO.getAirportDAO().get("LCG");
        //Airport destino = AirportDAO.getAirportDAO().get("BIO");
        //Airline airline = AirlineDAO.getAirlineDAO().get("VLG");
        modeloVuelo.getVuelos().addAll(FlightDAO.getFlightDAO().getAll());
        SwingUtilities.invokeLater(() -> {
            modeloVuelo.fireTableDataChanged();
            progressBar.setIndeterminate(false);
            searchButton.setEnabled(true);

        });
    }
}

/**
 * Panel para mostrar los vuelos comprados por el usuario
 */
class FlightHistoryPanel extends JSplitPane {

    private static final long serialVersionUID = 560276176324109821L;
    /**
     *
     */
    JList<Vuelo> lista_vuelos = new JList<>();
    // En vez de un Label lo cambiaré para incluir mejor toda la información del
    // vuelo
    JLabel label = new JLabel();
    // JSplitPane panelDividido = new JSplitPane();
    JPanel panel = new JPanel();

    public FlightHistoryPanel() {
        DefaultListModel<Vuelo> modelo = new DefaultListModel<>();

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
            List<Vuelo> vuelos = Vuelo.getVuelos();
            SwingUtilities.invokeLater(() -> {
                modelo.addAll(vuelos);
                lista_vuelos.setModel(modelo);
                lista_vuelos.ensureIndexIsVisible(modelo.getSize());
            });
        });
        t.start();

    }

    static class VueloListRenderer extends DefaultListCellRenderer {

        private static final long serialVersionUID = 1L;

        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            VueloListRenderer c = (VueloListRenderer) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            Vuelo v = (Vuelo) value;
            c.setText(v.getCodigo());
            return c;
        }


    }

}