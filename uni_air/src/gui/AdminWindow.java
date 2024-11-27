package gui;

import domain.ModeloVuelo;
import domain.ModeloVuelo.TipoVentana;
import domain.Vuelo;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

public class AdminWindow extends AbstractWindow {
    private static final long serialVersionUID = 8885137220929508082L;

    public AdminWindow() {
        super();
        setTitle("Admin");


        JPanel panel = new JPanel();
        panel.setBorder(createBorder("Admin"));
        JButton btnAnadirVuelo = new JButton("Añadir vuelo");
        ModeloVuelo vuelos = new ModeloVuelo(Vuelo.getVuelos(), TipoVentana.ADMIN);

        JTable tabla = new JTable(vuelos);
        JScrollPane scrollPane = new JScrollPane(tabla);


        this.add(panel, BorderLayout.NORTH);
        this.add(btnAnadirVuelo, BorderLayout.SOUTH);

        this.add(scrollPane);

        btnAnadirVuelo.addActionListener((e) -> {
            int codigo = Integer.parseInt(JOptionPane.showInputDialog("Introduce el codigo del nuevo vuelo"));
            String origen = JOptionPane.showInputDialog("Introduce el aeropuerto origen");
            String destino = JOptionPane.showInputDialog("Introduce el aeropuerto destino");
            LocalDateTime salida = LocalDateTime.parse(JOptionPane.showInputDialog("Introduce la fecha y hora de salida. De la siguiente forma:'AÑO-MES-DIA-Thora:minuto'"));
            LocalDateTime llegada = LocalDateTime.parse(JOptionPane.showInputDialog("Introduce la fecha y hora de llegada. De la siguiente forma:'AÑO-MES-DIA-Thora:minuto'"));

            JOptionPane.showMessageDialog(null, "Vuelo añadido correctamente");


        });


        setVisible(true);

    }


}
