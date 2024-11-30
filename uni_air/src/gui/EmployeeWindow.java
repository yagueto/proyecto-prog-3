package gui;

import domain.Flight;
import domain.FlightModel;
import domain.FlightModel.TipoVentana;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.io.Serial;

public class EmployeeWindow extends AbstractWindow {
    @Serial
    private static final long serialVersionUID = 4922652717777952972L;


    public EmployeeWindow() {
        super();
        setTitle("Empleado");

        JPanel panel = new JPanel();
        panel.setBorder(createBorder("Empleado"));
        this.add(panel, BorderLayout.NORTH);

        FlightModel vuelos = new FlightModel(Flight.getVuelos(), TipoVentana.EMPLOYEE);
        JTable tabla = new JTable(vuelos);

        TableColumn tableColumn = tabla.getColumnModel().getColumn(6);

        tableColumn.setCellEditor(new CellButtonRendererEditor((int clickedRow) -> {
            // Vuelo.getFlights().get(clickedRow).setPasajeros(Vuelo.getFlights().get(clickedRow).getPasajeros() - 1);
            SwingUtilities.invokeLater(() -> new CheckInWindow(this, Flight.getVuelos().get(clickedRow)));
            setVisible(false);
        }));
        // tableColumn.setCellRenderer(new CellButtonRendererEditor());

        JScrollPane scrollPane = new JScrollPane(tabla);
        this.add(scrollPane);


        setVisible(true);
    }


}
