package db;

import domain.Airport;

import java.security.InvalidParameterException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AirportDAO implements Dao<Airport> {
    private static AirportDAO airportDAO;

    private final PreparedStatement getAirportByIdStatement;
    private final PreparedStatement getAllAirportsStatement;
    private final PreparedStatement saveAirportStatement;
    private final PreparedStatement deleteAirportStatement;
    private final PreparedStatement deleteAllAirportsStatement;

    private AirportDAO() {
        Connection conn = DBManager.getDBManager().conn;
        try {
        	this.deleteAllAirportsStatement = conn.prepareStatement("DELETE FROM AIRPORT");
        	this.deleteAirportStatement = conn.prepareStatement("DELETE FROM AIRPORT WHERE IATA_CODE=?");
            this.getAirportByIdStatement = conn.prepareStatement("SELECT FULL_NAME, CITY, COUNTRY, LONG, LAT FROM " + "AIRPORT WHERE IATA_CODE=?");
            this.getAllAirportsStatement = conn.prepareStatement("SELECT IATA_CODE, FULL_NAME, CITY, COUNTRY, LONG," + " LAT FROM AIRPORT");
            this.saveAirportStatement = conn.prepareStatement("INSERT INTO AIRPORT (IATA_CODE, FULL_NAME, CITY, " + "COUNTRY, LONG, LAT) VALUES (?, ?, ?, ?, ?, ?)");
        } catch (SQLException e) {
            throw new DBException("Error inesperado creando statements, posible error en la base de datos", e); // Should never happen
        }
    }

    public static AirportDAO getAirportDAO() {
        if (airportDAO == null) {
            airportDAO = new AirportDAO();
        }
        return airportDAO;
    }

    @Override
    public Airport get(Object param) {
        if (!(param instanceof String iata)) {
            throw new InvalidParameterException("Parámetro de búsqueda inválido. (Se esperaba (String) IATA).");
        }
        try {
            getAirportByIdStatement.setString(1, iata);
            ResultSet rs = getAirportByIdStatement.executeQuery();

            if (rs.isBeforeFirst() && rs.next()) {
                return new Airport(iata, rs.getString("FULL_NAME"), rs.getString("CITY"), rs.getString("COUNTRY"), rs.getDouble("LONG"), rs.getDouble("LAT"));
            }
        } catch (SQLException e) {
            throw new DBException(e); // Should never happen
        }
        return null;
    }

    @Override
    public List<Airport> getAll() {
        ArrayList<Airport> airports = new ArrayList<>();
        try {
            ResultSet rs = getAllAirportsStatement.executeQuery();
            while (rs.next()) {
                airports.add(new Airport(rs.getString("IATA_CODE"), rs.getString("FULL_NAME"), rs.getString("CITY"), rs.getString("COUNTRY"), rs.getDouble("LONG"), rs.getDouble("LAT")));
            }
        } catch (SQLException e) {
            throw new DBException(e); // Should never happen
        }
        return airports;
    }

    @Override
    public void save(Airport airport) {
        try {
            saveAirportStatement.setString(1, airport.getIata());
            saveAirportStatement.setString(2, airport.getName());
            saveAirportStatement.setString(3, airport.getCity());
            saveAirportStatement.setString(4, airport.getCountry());
            saveAirportStatement.setDouble(5, airport.getLongitude());
            saveAirportStatement.setDouble(6, airport.getLatitude());
            saveAirportStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DBException(e); // Should never happen
        }
    }

    @Override
    public void update(Airport airport) {
        throw new UnsupportedOperationException("No se pueden actualizar los datos de un aeropuerto");
    }

    @Override
    public void delete(Airport airport) {
        //throw new UnsupportedOperationException("No se pueden eliminar aeropuertos");
    	try {
    		if(airport.getIata() == null) {
    			System.out.println("No existe un aeropuerto con ese IATA_CODE");
    		}else {
    			deleteAirportStatement.setString(1, airport.getIata());
    			int rowsDeleted = deleteAirportStatement.executeUpdate(); // Ejecutar el delete
                if (rowsDeleted == 0) {
                    System.out.println("No se encontró ningún aeropuerto con el código IATA proporcionado.");
                }
    		}
		} catch (SQLException e) {
			throw new DBException(e); // Should never happen
		}
    }
    public void deleteAll() throws SQLException {	
		deleteAllAirportsStatement.executeUpdate();		
    }
}