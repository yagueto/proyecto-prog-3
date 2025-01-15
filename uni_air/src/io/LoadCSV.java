package io;

import db.DBException;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Scanner;

public interface LoadCSV {
    default void loadCSV(Connection conn) {
        Thread a = new Thread(() -> loadAirline(conn));
        a.start();
        Thread b = new Thread(() -> loadAirports(conn));
        b.start();

        try {
            a.join();
            b.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Thread c = new Thread(() -> loadFlights(conn));
        c.start();
    }

    static void loadFlights(Connection conn) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(LoadCSV.class.getResourceAsStream("/1000_flights.csv"))));
            Scanner sc = new Scanner(reader);
            sc.nextLine();

            while (sc.hasNext()) {
                String linea = sc.nextLine();
                String[] campos = linea.split(",");

                String airline = campos[4];
                String flightNumber = airline + Integer.parseInt(campos[5]);

                int year = Integer.parseInt(campos[0]);
                int month = Integer.parseInt(campos[1]);
                int day = Integer.parseInt(campos[2]);
                String origin = campos[7];
                String destination = campos[8];
                int departure = Integer.parseInt(campos[9]);
                int arrival = Integer.parseInt(campos[10]);
                int price = Integer.parseInt(campos[11]);
                LocalDateTime dep = LocalDateTime.of(year, month, day, departure / 100, departure % 100);
                LocalDateTime arr = LocalDateTime.of(year, month, day, arrival / 100, arrival % 100);

                String stmStr = "INSERT INTO FLIGHT(ID, ORIGIN_AIRPORT, DEST_AIRPORT, AIRLINE_CODE, DEPARTURE_TIME, ARRIVAL_TIME, MAX_PASAJEROS, PRECIO) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement stm = conn.prepareStatement(stmStr);
                stm.setString(1, flightNumber);
                stm.setString(2, origin);
                stm.setString(3, destination);
                stm.setString(4, airline);
                stm.setString(5, dep.toString());
                stm.setString(6, arr.toString());
                stm.setInt(7, 150);
                stm.setInt(8, price);
                stm.executeUpdate();
            }
            sc.close();
        } catch (SQLException ignored) {
            // Ignoramos filas duplicadas
        }
    }

    static void loadAirline(Connection conn) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(LoadCSV.class.getResourceAsStream("/airlines.csv"))));
            Scanner sc = new Scanner(reader);
            sc.nextLine();
            while (sc.hasNext()) {
                String linea = sc.nextLine();
                String[] campos = linea.split(",");

                String iata = campos[0];
                String airline = campos[1];

                String stmStr = "INSERT INTO AIRLINE (IATA_CODE, NAME) VALUES (?, ?)";
                PreparedStatement stm = conn.prepareStatement(stmStr);

                stm.setString(1, iata);
                stm.setString(2, airline);
                stm.executeUpdate();
            }
            sc.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    static void loadAirports(Connection conn) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(LoadCSV.class.getResourceAsStream("/airports.csv"))));
            Scanner sc = new Scanner(reader);
            sc.nextLine();

            while (sc.hasNext()) {
                String linea = sc.nextLine();
                String[] campos = linea.split(",");

                String iata = campos[0];
                String name = campos[1];
                String city = campos[2];
                String country = campos[4];
                String stmStr = "INSERT INTO AIRPORT (IATA_CODE, FULL_NAME, CITY, COUNTRY, LONG, LAT) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement stm = conn.prepareStatement(stmStr);
                if (campos.length == 5) {
                    stm.setDouble(5, 0.0);
                    stm.setDouble(6, 0.0);
                } else {
                    double lon = Double.parseDouble(campos[5]);
                    double lat = Double.parseDouble(campos[6]);
                    stm.setDouble(5, lon);
                    stm.setDouble(6, lat);
                }
                stm.setString(1, iata);
                stm.setString(2, name);
                stm.setString(3, city);
                stm.setString(4, country);

                stm.executeUpdate();
            }
            sc.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
