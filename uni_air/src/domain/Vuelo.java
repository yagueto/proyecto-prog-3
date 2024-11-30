package domain;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Vuelo {
	private String codigo;
	private Airport origen;
	private Airport destino;
	private Airline airline;
	private LocalDateTime fechaDespegue;
	private LocalDateTime fechaAterrizaje;
	private int maxPasajeros;
	private int precio;
	private static ArrayList<Vuelo> vuelos;

	public Vuelo(String codigo, Airport origen, Airport destino, Airline airline, LocalDateTime fechaDespegue,
				 LocalDateTime fechaAterrizaje, int maxPasajeros, int precio) {
		this.codigo = codigo;
		this.origen = origen;
		this.destino = destino;
		this.airline = airline;
		this.fechaDespegue = fechaDespegue;
		this.fechaAterrizaje = fechaAterrizaje;
		this.maxPasajeros = maxPasajeros;
		this.precio = precio;
	}

	@Deprecated
	public static ArrayList<Vuelo> getVuelos() {
		if (vuelos == null) {
			System.out.println("Empieza a cargar vuelos");
			vuelos = new ArrayList<>();
			Thread t = new Thread(() -> loadVuelos());
			t.start();
			System.out.println("Vuelos cargados");
		} else {
			System.out.println("Vuelos ya cargados previamente, devolviendo");
		}
		return vuelos;
	}
	
	// Llenar lista de vuelos desde archivo
	@Deprecated
	private static void loadVuelos() {
		throw new RuntimeException("Ya no se usa esta función");
//		try {
//			Scanner sc = new Scanner(new File("resources/flights_part1.csv"));
//			sc.nextLine();
//
//			while(sc.hasNext()){
//				String linea = sc.nextLine();
//				String[] campos = linea.split(",");
//
//				int year = Integer.parseInt(campos[0]);
//				int month = Integer.parseInt(campos[1]);
//				int day = Integer.parseInt(campos[2]);
//				// campos[3] es el día de la semana, pero al tener la fecha no es un dato relevante
//				String airline = campos[4];
//				int flightNumber = Integer.parseInt(campos[5]);
//				String tailNumber = campos[6];
//				String origin = campos[7];
//				String destination = campos[8];
//				int departure = Integer.parseInt(campos[9]);
//				int arrival = Integer.parseInt(campos[10]);
//				int price = Integer.parseInt(campos[11]);
//				LocalDateTime dep = LocalDateTime.of(year, month, day, departure / 100, departure % 100);
//
//				vuelos.add(new Vuelo(flightNumber, origin, destination, dep, dep.plusHours(arrival / 100).plusMinutes(departure % 100), 120, 150, price));
//			}
//			sc.close();
//		} catch (FileNotFoundException e) {
//			System.err.println("Error al cargar los datos");
//		}
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
