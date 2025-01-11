package domain;

import db.AirlineDAO;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Airline {

	private final String iata;
	private final String name;

	public Airline(String iata, String name) {
		super();
		this.iata = iata;
		this.name = name;
	}

	// Llenar lista de aerolineas desde archivo
	public static void loadAirline() {
		try {
			Scanner sc = new Scanner(new File("resources/airlines.csv"));
			sc.nextLine();
			while(sc.hasNext()){
				String linea = sc.nextLine();
				String[] campos = linea.split(",");

				String iata = campos[0];
				if (AirlineDAO.getAirlineDAO().get(iata) == null){
					String airline = campos[1];

					AirlineDAO.getAirlineDAO().save(new Airline(iata, airline));
				}
			}
			sc.close();
		} catch (FileNotFoundException e) {
			System.err.println("Error al cargar los datos");
		}
	}

	public String getIata() {
		return iata;
	}

	public String getName() {
		return name;
	}

	public String toString() {
		return "Airline [iata=" + iata + ", name=" + name + "]";
	}

}
