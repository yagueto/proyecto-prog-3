package db;

import domain.Vuelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FlightDAO implements Dao<Vuelo> {
    private static FlightDAO flightDAO;

    private final PreparedStatement getByIdStatement;
    private final PreparedStatement getAllStatement;
    private final PreparedStatement saveStatement;
    //private final PreparedStatement updateStatement;
    //private final PreparedStatement deleteStatement;

    private FlightDAO() {
        Connection conn = DBManager.getDBManager().conn;
        try {
            this.getByIdStatement = conn.prepareStatement("SELECT ID, ORIGIN_AIRPORT, DEST_AIRPORT, AIRLINE_CODE, DEPARTURE_TIME, ARRIVAL_TIME, MAX_PASAJEROS, PRECIO FROM FLIGHT WHERE ID=?");
            this.getAllStatement = conn.prepareStatement("SELECT ID, ORIGIN_AIRPORT, DEST_AIRPORT, AIRLINE_CODE, DEPARTURE_TIME, ARRIVAL_TIME, MAX_PASAJEROS, PRECIO FROM FLIGHT");
            this.saveStatement = conn.prepareStatement("INSERT INTO FLIGHT(ID, ORIGIN_AIRPORT, DEST_AIRPORT, AIRLINE_CODE, DEPARTURE_TIME, ARRIVAL_TIME, MAX_PASAJEROS, PRECIO) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            //this.updateStatement = conn.prepareStatement("UPDATE FLIGHT SET NAME=? WHERE ID=?");
            //this.deleteStatement = conn.prepareStatement("DELETE FROM FLIGHT WHERE ID=?");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static FlightDAO getFlightDAO() {
        if (flightDAO == null) {
            flightDAO = new FlightDAO();
        }
        return flightDAO;
    }

    @Override
    public Vuelo get(Object param) {
        if (!(param instanceof Integer in)) {
            throw new RuntimeException("Parámetro de búsqueda inválido. (Se esperaba (String) IATA).");
        }
        try {
            getByIdStatement.setInt(1, in);
            ResultSet rs = getByIdStatement.executeQuery();
            if (rs.isBeforeFirst()) {
                return new Vuelo(rs.getString("ID"), AirportDAO.getAirportDAO().get(rs.getString("ORIGIN_AIRPORT")),
                        AirportDAO.getAirportDAO().get(rs.getString("DEST_AIRPORT")), AirlineDAO.getAirlineDAO().get(rs.getString("AIRLINE_CODE")), LocalDateTime.parse(rs.getString("DEPARTURE_TIME")), LocalDateTime.parse(rs.getString("ARRIVAL_TIME")), rs.getInt("PRECIO"), rs.getInt("MAX_PASAJEROS"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<Vuelo> getAll() {
        ArrayList<Vuelo> vuelos = new ArrayList<>();
        try {
            ResultSet rs = getAllStatement.executeQuery();
            while (rs.next()) {
                vuelos.add(new Vuelo(rs.getString("ID"), AirportDAO.getAirportDAO().get(rs.getString("ORIGIN_AIRPORT")),
                        AirportDAO.getAirportDAO().get(rs.getString("DEST_AIRPORT")), AirlineDAO.getAirlineDAO().get(rs.getString("AIRLINE_CODE")), LocalDateTime.parse(rs.getString("DEPARTURE_TIME")), LocalDateTime.parse(rs.getString("ARRIVAL_TIME")), rs.getInt("PRECIO"), rs.getInt("MAX_PASAJEROS")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(vuelos);
        return vuelos;
    }

    @Override
    public void save(Vuelo vuelo) {
        try {
            saveStatement.setString(1, vuelo.getCodigo());
            saveStatement.setString(2, vuelo.getOrigen().getName());
            saveStatement.setString(3, vuelo.getDestino().getName());
            saveStatement.setString(4, vuelo.getAirline().getName());
            saveStatement.setString(5, vuelo.getFechaDespegue().toString());
            saveStatement.setString(6, vuelo.getFechaAterrizaje().toString());
            //saveStatement.setInt(7, vuelo.getPasajeros());
            saveStatement.setInt(7, vuelo.getMaxPasajeros());
            saveStatement.setInt(8, vuelo.getPrecio());
            saveStatement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Vuelo vuelo) {

    }

    @Override
    public void delete(Vuelo vuelo) {

    }
}
