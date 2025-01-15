package domain;

import java.time.LocalDateTime;
import java.util.HashSet;

public class Flight {
	private String codigo;
	private Airport origen;
	private Airport destino;
	private Airline airline;
	private LocalDateTime fechaDespegue;
	private LocalDateTime fechaAterrizaje;
	private int maxPasajeros;
	private int precio;
	private final HashSet<String> occupied;

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
