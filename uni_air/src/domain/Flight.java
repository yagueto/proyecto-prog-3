package domain;

import db.AirlineDAO;
import db.AirportDAO;
import db.FlightDAO;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class Flight {
	private String codigo;
	private Airport origen;
	private Airport destino;
	private Airline airline;
	private LocalDateTime fechaDespegue;
	private LocalDateTime fechaAterrizaje;
	private int maxPasajeros;
	private int precio;
	private static ArrayList<Flight> flights;
	private HashSet<String> occupied;

	public Flight(String codigo, Airport origen, Airport destino, Airline airline, LocalDateTime fechaDespegue,
				  LocalDateTime fechaAterrizaje, int maxPasajeros, int precio) {
		this.codigo = codigo;
		this.origen = origen;
		this.destino = destino;
		this.airline = airline;
		this.fechaDespegue = fechaDespegue;
		this.fechaAterrizaje = fechaAterrizaje;
		this.maxPasajeros = maxPasajeros;
		this.precio = precio;
		this.occupied = new HashSet<>();
	}
	
	// Llenar lista de vuelos desde archivo
	public static void loadFlights() {
		Thread t = new Thread(() ->{
			try {
				Scanner sc = new Scanner(new File("resources/flights_part1.csv"));
				sc.nextLine();

				while(sc.hasNext()){
					String linea = sc.nextLine();
					String[] campos = linea.split(",");

					String airline = campos[4];
					String flightNumber = Integer.parseInt(campos[5]) + airline;

					if(FlightDAO.getFlightDAO().get(flightNumber) == null){
						int year = Integer.parseInt(campos[0]);
						int month = Integer.parseInt(campos[1]);
						int day = Integer.parseInt(campos[2]);
						Airline air = AirlineDAO.getAirlineDAO().get(airline);
						String origin = campos[7];
						Airport orig = AirportDAO.getAirportDAO().get(origin);
						String destination = campos[8];
						Airport dest = AirportDAO.getAirportDAO().get(destination);
						int departure = Integer.parseInt(campos[9]);
						int arrival = Integer.parseInt(campos[10]);
						int price = Integer.parseInt(campos[11]);
						LocalDateTime dep = LocalDateTime.of(year, month, day, departure / 100, departure % 100);

						FlightDAO.getFlightDAO().save(new Flight(flightNumber, orig, dest, air, dep.plusHours(arrival / 100).plusMinutes(departure % 100), dep.plusHours(arrival / 100).plusMinutes(departure % 100), 150, price));
					}
				}
				sc.close();
			} catch (FileNotFoundException e) {
				System.err.println("Error al cargar los datos");
			}
		});
		t.start();
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Airport getOrigen() {
		return origen;
	}

	public void setOrigen(Airport origen) {
		this.origen = origen;
	}

	public Airport getDestino() {
		return destino;
	}

	public void setDestino(Airport destino) {
		this.destino = destino;
	}

	public LocalDateTime getFechaDespegue() {
		return fechaDespegue;
	}

	public void setFechaDespegue(LocalDateTime fechaDespegue) {
		this.fechaDespegue = fechaDespegue;
	}

	public LocalDateTime getFechaAterrizaje() {
		return fechaAterrizaje;
	}

	public void setFechaAterrizaje(LocalDateTime fechaAterrizaje) {
		this.fechaAterrizaje = fechaAterrizaje;
	}

	public int getMaxPasajeros() {
		return maxPasajeros;
	}

	public void setMaxPasajeros(int maxPasajeros) {
		this.maxPasajeros = maxPasajeros;
	}

	public int getPrecio() {
		return precio;
	}

	public void setPrecio(int precio) {
		this.precio = precio;
	}

	public Airline getAirline() {
		return airline;
	}

	public void setAirline(Airline airline) {
		this.airline = airline;
	}

	public HashSet<String> getOccupied() {
		return occupied;
	}

	@Override
	public String toString() {
		return "Vuelo{" +
				"codigo=" + codigo +
				", origen=" + origen +
				", destino=" + destino +
				", airline=" + airline +
				", fechaDespegue=" + fechaDespegue +
				", fechaAterrizaje=" + fechaAterrizaje +
				", maxPasajeros=" + maxPasajeros +
				", precio=" + precio +
				'}';
	}


}
