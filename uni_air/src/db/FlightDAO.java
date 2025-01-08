package db;

import domain.Airport;
import domain.Flight;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FlightDAO implements Dao<Flight> {
    private static FlightDAO flightDAO;

    private final PreparedStatement getByIdStatement;
    private final PreparedStatement getAllStatement;
    private final PreparedStatement searchStatement;
    private final PreparedStatement saveStatement;
    private final PreparedStatement updateStatement;
    private final PreparedStatement deleteStatement;

    private FlightDAO() {
        Connection conn = DBManager.getDBManager().conn;
        try {
            this.getByIdStatement = conn.prepareStatement("SELECT ID, ORIGIN_AIRPORT, DEST_AIRPORT, AIRLINE_CODE, DEPARTURE_TIME, ARRIVAL_TIME, MAX_PASAJEROS, PRECIO FROM FLIGHT WHERE ID=?");
            this.getAllStatement = conn.prepareStatement("SELECT ID, ORIGIN_AIRPORT, DEST_AIRPORT, AIRLINE_CODE, DEPARTURE_TIME, ARRIVAL_TIME, MAX_PASAJEROS, PRECIO FROM FLIGHT");
            this.saveStatement = conn.prepareStatement("INSERT INTO FLIGHT(ID, ORIGIN_AIRPORT, DEST_AIRPORT, AIRLINE_CODE, DEPARTURE_TIME, ARRIVAL_TIME, MAX_PASAJEROS, PRECIO) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            this.searchStatement = conn.prepareStatement("SELECT ID, ORIGIN_AIRPORT, DEST_AIRPORT, AIRLINE_CODE, DEPARTURE_TIME, ARRIVAL_TIME, MAX_PASAJEROS, PRECIO FROM FLIGHT WHERE ORIGIN_AIRPORT=? AND " + "DEST_AIRPORT=? AND DEPARTURE_TIME LIKE concat(?, '%')");
            this.updateStatement = conn.prepareStatement("UPDATE FLIGHT SET DEST_AIRPORT=?, DEPARTURE_TIME=?, PRECIO=? WHERE ID=?");
            this.deleteStatement = conn.prepareStatement("DELETE FROM FLIGHT WHERE ID=?");
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
    public Flight get(Object param) {
        if (!(param instanceof String in)) {
            throw new RuntimeException("Parámetro de búsqueda inválido. (Se esperaba (String) IATA).");
        }
        try {
            getByIdStatement.setString(1, in);
            ResultSet rs = getByIdStatement.executeQuery();
            if (rs.isBeforeFirst()) {
                return new Flight(rs.getString("ID"), AirportDAO.getAirportDAO().get(rs.getString("ORIGIN_AIRPORT")), AirportDAO.getAirportDAO().get(rs.getString("DEST_AIRPORT")), AirlineDAO.getAirlineDAO().get(rs.getString("AIRLINE_CODE")), LocalDateTime.parse(rs.getString("DEPARTURE_TIME")), LocalDateTime.parse(rs.getString("ARRIVAL_TIME")), rs.getInt("PRECIO"), rs.getInt("MAX_PASAJEROS"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<Flight> getAll() {
        ArrayList<Flight> flights = new ArrayList<>();
        try {
            getFlightsFromResultSet(flights, getAllStatement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return flights;
    }

    public List<Flight> search(Airport origin_airport, Airport destination_airport, LocalDate departure_date) {
        ArrayList<Flight> flights = new ArrayList<>();
        try {
            searchStatement.setString(1, origin_airport.getIata());
            searchStatement.setString(2, destination_airport.getIata());
            searchStatement.setString(3, departure_date.toString());
            getFlightsFromResultSet(flights, searchStatement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return flights;
    }

    private void getFlightsFromResultSet(ArrayList<Flight> flights, PreparedStatement searchStatement) throws SQLException {
        ResultSet rs = searchStatement.executeQuery();
        while (rs.next()) {
            flights.add(new Flight(rs.getString("ID"), AirportDAO.getAirportDAO().get(rs.getString("ORIGIN_AIRPORT")), AirportDAO.getAirportDAO().get(rs.getString("DEST_AIRPORT")), AirlineDAO.getAirlineDAO().get(rs.getString("AIRLINE_CODE")), LocalDateTime.parse(rs.getString("DEPARTURE_TIME")), LocalDateTime.parse(rs.getString("ARRIVAL_TIME")), rs.getInt("PRECIO"), rs.getInt("MAX_PASAJEROS")));
        }
    }

    @Override
    public void save(Flight flight) {
        try {
            saveStatement.setString(1, flight.getCodigo());
            saveStatement.setString(2, flight.getOrigen().getName());
            saveStatement.setString(3, flight.getDestino().getName());
            saveStatement.setString(4, flight.getAirline().getName());
            saveStatement.setString(5, flight.getFechaDespegue().toString());
            saveStatement.setString(6, flight.getFechaAterrizaje().toString());
            //saveStatement.setInt(7, vuelo.getPasajeros());
            saveStatement.setInt(7, flight.getMaxPasajeros());
            saveStatement.setInt(8, flight.getPrecio());
            saveStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    // Este métdodo lo implementaremos para que un admin o employye puede modificar vuelos debido a retrasos, cambios de precio...
    @Override
    public void update(Flight flight) {
        try {
            updateStatement.setString(3, flight.getDestino().getName());
            updateStatement.setString(5, flight.getFechaDespegue().format(null));
            updateStatement.setInt(8, flight.getPrecio());

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.err.println("No se ha podido actualizar en la db");
        }
    }

    // Este métdodo lo implementaremos para que un employee o admin pueda cancelar/borrar vuelos
    @Override
    public void delete(Flight flight) {
        try {
            deleteStatement.setString(1, flight.getCodigo());
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("No se ha podido eliminar de la db");

        }
    }
}
