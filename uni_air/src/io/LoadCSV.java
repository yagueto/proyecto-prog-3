package io;

import db.AirlineDAO;
import db.AirportDAO;
import db.FlightDAO;
import domain.Airline;
import domain.Airport;
import domain.Flight;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.Scanner;

public interface LoadCSV {
    default void loadCSV() throws InterruptedException {
        Thread a = new Thread(() -> loadAirline());
        a.start();
        Thread b = new Thread(() -> loadAirports());
        b.start();

        a.join();
        b.join();

        Thread c = new Thread(() -> loadFlights());
        c.start();
    }

    static void loadFlights() {
        try {
            Scanner sc = new Scanner(new File("resources/1000_flights.csv"));
            sc.nextLine();

            while (sc.hasNext()) {
                String linea = sc.nextLine();
                String[] campos = linea.split(",");

                String airline = campos[4];
                String flightNumber = airline + Integer.parseInt(campos[5]);

                if (FlightDAO.getFlightDAO().get(flightNumber) == null) {
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
    }

    static void loadAirline() {
        try {
            Scanner sc = new Scanner(new File("resources/airlines.csv"));
            sc.nextLine();
            while (sc.hasNext()) {
                String linea = sc.nextLine();
                String[] campos = linea.split(",");

                String iata = campos[0];
                if (AirlineDAO.getAirlineDAO().get(iata) == null) {
                    String airline = campos[1];

                    AirlineDAO.getAirlineDAO().save(new Airline(iata, airline));
                }
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.err.println("Error al cargar los datos");
        }
    }

    static void loadAirports() {
        try {
            Scanner sc = new Scanner(new File("resources/airports.csv"));
            sc.nextLine();

            while (sc.hasNext()) {
                String linea = sc.nextLine();
                String[] campos = linea.split(",");

                String iata = campos[0];
                if (AirportDAO.getAirportDAO().get(iata) == null) {
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
        if (campos.length == 5) {
            nuevo = new Airport(iata, name, city, country);
        } else {
            double longitude = Double.parseDouble(campos[5]);
            double latitude = Double.parseDouble(campos[6]);
            nuevo = new Airport(iata, name, city, country, longitude, latitude);
        }
        return nuevo;
    }
}
