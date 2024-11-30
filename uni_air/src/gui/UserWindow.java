package gui;

import db.AirportDAO;
import db.FlightDAO;
import domain.Airport;
import domain.Flight;
import domain.FlightModel;
import domain.FlightModel.TipoVentana;
import gui.misc.HintTextField;
import org.jdatepicker.JDatePicker;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.GregorianCalendar;
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

    private static FlightModel flightModel;

    public FlightSearchPanel() {
        this.setLayout(new BorderLayout());

        // Panel para campos de filtrado de resultados
        JPanel filtersPanel = createSearchPanel();
        this.add(filtersPanel, BorderLayout.NORTH);

        // JTable para mostrar resultados
        flightModel = new FlightModel(List.of(), TipoVentana.USER);
        JTable tabla = new JTable(flightModel);

        TableColumn c = tabla.getColumnModel().getColumn(5);
        CellButtonRendererEditor cellButtonRendererEditor = new CellButtonRendererEditor((int row) -> new BuyWindow(flightModel.getFlights().get(row)));
        c.setCellEditor(cellButtonRendererEditor);
//		c.setCellRenderer(cellButtonRendererEditor);

        JScrollPane scrollPane = new JScrollPane(tabla);

        this.add(scrollPane);
    }

    private static JPanel createSearchPanel() {
        JPanel filtersPanel = new JPanel();
        JPanel buttonsPanel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(filtersPanel, BoxLayout.Y_AXIS);
        filtersPanel.setLayout(boxLayout);

        buttonsPanel.setBackground(UIManager.getColor("ProgressBar.foreground"));

        JTextField origenField = new HintTextField(11, "Aeropuerto de origen");
        JTextField destField = new HintTextField(12, "Aeropuerto de destino");
        JDatePicker datePicker = new JDatePicker();

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
            GregorianCalendar calendar = (GregorianCalendar) datePicker.getModel().getValue();
            if (calendar == null) {
                JOptionPane.showMessageDialog(null, "¡Fecha de salida vacía!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            ZonedDateTime zonedDateTime = calendar.toZonedDateTime();

            Thread t = new Thread(() -> updateTableData(origenField.getText(), destField.getText(), zonedDateTime.toLocalDate()));
            t.start();

        });

        return filtersPanel;
    }


    /**
     * Carga datos de la búsqueda de vuelos en la tabla
     */
    private static void updateTableData(String origen, String destino, LocalDate fecha_salida) {
        SwingUtilities.invokeLater(() -> {
            progressBar.setIndeterminate(true);
            searchButton.setEnabled(false);
        });
        Airport airport_origen = AirportDAO.getAirportDAO().get(origen);
        Airport airport_destino = AirportDAO.getAirportDAO().get(destino);
        if (airport_origen == null) {
            JOptionPane.showMessageDialog(null, "¡Aeropuerto de origen inválido!", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (airport_destino == null) {
            JOptionPane.showMessageDialog(null, "¡Aeropuerto de destino inválido!", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            flightModel.getFlights().clear();
            flightModel.getFlights().addAll(FlightDAO.getFlightDAO().search(airport_origen, airport_destino, fecha_salida));
        }
        SwingUtilities.invokeLater(() -> {
            flightModel.fireTableDataChanged();
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
    JList<Flight> lista_vuelos = new JList<>();
    // En vez de un Label lo cambiaré para incluir mejor toda la información del
    // vuelo
    JLabel label = new JLabel();
    // JSplitPane panelDividido = new JSplitPane();
    JPanel panel = new JPanel();

    public FlightHistoryPanel() {
        DefaultListModel<Flight> modelo = new DefaultListModel<>();

        lista_vuelos.setCellRenderer(new VueloListRenderer());
        // Ponemos el modelo para la lista de vuelos
        lista_vuelos.setModel(modelo);

        // Creamos 10 vuelos añadidos al modelo
        // Para Obtener/Seleccionar un vuelo de la lista de vuelos
        lista_vuelos.getSelectionModel().addListSelectionListener(e -> {
            // Cogemos un vuelo
            Flight v = lista_vuelos.getSelectedValue();
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
            List<Flight> flights = Flight.getVuelos();
            SwingUtilities.invokeLater(() -> {
                modelo.addAll(flights);
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
            Flight v = (Flight) value;
            c.setText(v.getCodigo());
            return c;
        }


    }

}