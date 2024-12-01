package gui;

import domain.Airline;
import domain.Airport;
import domain.Flight;
import domain.FlightModel;
import domain.FlightModel.TipoVentana;

import javax.swing.*;

import db.AirlineDAO;
import db.AirportDAO;
import db.DBManager;
import db.FlightDAO;

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
		FlightModel vuelos = new FlightModel(FlightDAO.getFlightDAO().getAll(), TipoVentana.ADMIN);

		JTable tabla = new JTable(vuelos);
		JScrollPane scrollPane = new JScrollPane(tabla);

		this.add(panel, BorderLayout.NORTH);
		this.add(btnAnadirVuelo, BorderLayout.SOUTH);

		this.add(scrollPane);

		btnAnadirVuelo.addActionListener((e) -> {

			String codigo = JOptionPane.showInputDialog("Introduce el codigo del nuevo vuelo");
			String origen = JOptionPane.showInputDialog("Introduce el aeropuerto origen");
			String destino = JOptionPane.showInputDialog("Introduce el aeropuerto destino");
			String aerolinea = JOptionPane.showInputDialog("Introduce la aerolinea");
			LocalDateTime salida = LocalDateTime.parse(JOptionPane.showInputDialog(
					"Introduce la fecha y hora de salida. De la siguiente forma:'AÑO-MES-DIA-Thora:minuto'"));
			LocalDateTime llegada = LocalDateTime.parse(JOptionPane.showInputDialog(
					"Introduce la fecha y hora de llegada. De la siguiente forma:'AÑO-MES-DIA-Thora:minuto'"));
			int maxPasajeros = Integer.parseInt(JOptionPane.showInputDialog("Introduce la capacidad del vuelo"));
			int precio = Integer.parseInt(JOptionPane.showInputDialog("Introduce el precio del billete"));

			Airport airport_origen = AirportDAO.getAirportDAO().get(origen);
			Airport airport_destino = AirportDAO.getAirportDAO().get(destino);
			Airline airline = AirlineDAO.getAirlineDAO().get(aerolinea);

			try {

				FlightDAO.getFlightDAO().save(new Flight(codigo, airport_origen, airport_destino, airline, salida,
						llegada, maxPasajeros, precio));
				JOptionPane.showMessageDialog(null, "Vuelo añadido correctamente");

			} catch (Exception e2) {

				e2.printStackTrace();
				System.err.println("Error al añadir el vuelo");

			}

		});

		SwingUtilities.invokeLater(() -> {
			vuelos.fireTableDataChanged();
		});

		setVisible(true);

	}

}
