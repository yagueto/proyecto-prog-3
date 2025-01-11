package domain;

import db.AirportDAO;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Airport {

    private String iata;
    private String name;
    private String city;
    private String country;
    private double longitude;
    private double latitude;

    public Airport(String iata, String name, String city, String country, double longitude, double latitude) {
        super();
        this.iata = iata;
        this.name = name;
        this.city = city;
        this.country = country;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Airport(String iata, String name, String city, String country) {
        this.iata = iata;
        this.name = name;
        this.city = city;
        this.country = country;
        this.longitude = 0.0;
        this.latitude = 0.0;
    }

    // Llenar lista de aeropuertos desde archivo
    public static void loadAirports() {
        try {
            Scanner sc = new Scanner(new File("resources/airports.csv"));
            sc.nextLine();

            while(sc.hasNext()){
                String linea = sc.nextLine();
                String[] campos = linea.split(",");

                String iata = campos[0];
                if(AirportDAO.getAirportDAO().get(iata) == null){
                    Airport nuevo = getAirport(campos, iata);
                    AirportDAO.getAirportDAO().save(nuevo);
                }
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.err.println("Error al cargar los datos");
        }
    }

    private static Airport getAirport(String[] campos, String iata) {
        String name = campos[1];
        String city = campos[2];
        String country = campos[4];
        Airport nuevo;
        if (campos.length <= 5){
            nuevo = new Airport(iata, name, city, country);
        } else {
            double longitude = Double.parseDouble(campos[5]);
            double latitude = Double.parseDouble(campos[6]);
            nuevo = new Airport(iata, name, city, country, longitude, latitude);
        }
        return nuevo;
    }

    public String getIata() {
        return iata;
    }

    public void setIata(String iata) {
        this.iata = iata;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return this.name;
    }


}
