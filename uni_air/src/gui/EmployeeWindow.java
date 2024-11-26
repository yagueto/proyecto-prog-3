package gui;

import domain.ModeloVuelo;
import domain.ModeloVuelo.TipoVentana;
import domain.Vuelo;

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

        ModeloVuelo vuelos = new ModeloVuelo(Vuelo.getVuelos(), TipoVentana.EMPLOYEE);
        JTable tabla = new JTable(vuelos);

        TableColumn tableColumn = tabla.getColumnModel().getColumn(6);

        tableColumn.setCellEditor(new CellButtonRendererEditor((int clickedRow) -> {
            // Vuelo.getVuelos().get(clickedRow).setPasajeros(Vuelo.getVuelos().get(clickedRow).getPasajeros() - 1);
            SwingUtilities.invokeLater(() -> new CheckInWindow(this, Vuelo.getVuelos().get(clickedRow)));
            setVisible(false);
        }));
        // tableColumn.setCellRenderer(new CellButtonRendererEditor());

        JScrollPane scrollPane = new JScrollPane(tabla);
        this.add(scrollPane);


        setVisible(true);
    }


}
