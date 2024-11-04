package gui;

import main.ModeloVuelo;
import main.ModeloVuelo.TipoVentana;
import main.Vuelo;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;

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
            Vuelo.getVuelos().get(clickedRow).setPasajeros(Vuelo.getVuelos().get(clickedRow).getPasajeros() - 1);
            vuelos.fireTableDataChanged();
            // TODO mejorar el check-in
        }));
        // tableColumn.setCellRenderer(new CellButtonRendererEditor());

        JScrollPane scrollPane = new JScrollPane(tabla);
        this.add(scrollPane);


        setVisible(true);
    }


}
