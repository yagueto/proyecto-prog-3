package main;

import java.time.LocalDateTime;

public class Vuelo {
	private String codigo;
	private String origen;
	private String destino;
	private LocalDateTime fechaDespegue;
	private LocalDateTime fechaAterrizaje;
	private int maxPasajeros;
	private double precio;

	public Vuelo(String codigo, String origen, String destino, LocalDateTime fechaDespegue,
			LocalDateTime fechaAterrizaje, int maxPasajeros, double precio) {
		this.codigo = codigo;
		this.origen = origen;
		this.destino = destino;
		this.fechaDespegue = fechaDespegue;
		this.fechaAterrizaje = fechaAterrizaje;
		this.maxPasajeros = maxPasajeros;
		this.precio = precio;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
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

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}
}
