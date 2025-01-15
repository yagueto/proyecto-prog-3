package gui;

import db.FlightDAO;
import domain.models.FlightModel;
import domain.models.FlightModel.TipoVentana;
import gui.misc.CellButtonRendererEditor;

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

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(createBorder("Empleado"));
        this.add(panel);

        FlightModel vuelos = new FlightModel(FlightDAO.getFlightDAO().getAll(), TipoVentana.EMPLOYEE);
        JTable tabla = new JTable(vuelos);
        resizeColumnWidth(tabla);

        TableColumn tableColumn = tabla.getColumnModel().getColumn(6);

        tableColumn.setCellEditor(new CellButtonRendererEditor("COMPRAR →", (int clickedRow) -> {
            SwingUtilities.invokeLater(() -> new CheckInWindow(this, vuelos.getFlights().get(clickedRow)));
            setVisible(false);
        }));
        // tableColumn.setCellRenderer(new CellButtonRendererEditor());

        JScrollPane scrollPane = new JScrollPane(tabla);
        panel.add(scrollPane);


        setVisible(true);
    }

}
