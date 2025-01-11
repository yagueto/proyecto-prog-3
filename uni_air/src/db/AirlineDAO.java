package db;

import domain.Airline;

import java.security.InvalidParameterException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AirlineDAO implements Dao<Airline> {
    private static AirlineDAO airlineDAO;

    private final PreparedStatement getAirlineByIdStatement;
    private final PreparedStatement getAllAirlinesStatement;
    private final PreparedStatement saveAirlineStatement;

    private AirlineDAO() {
        Connection conn = DBManager.getDBManager().conn;
        try {
            this.getAirlineByIdStatement = conn.prepareStatement("SELECT NAME FROM AIRLINE WHERE IATA_CODE=?");
            this.getAllAirlinesStatement = conn.prepareStatement("SELECT IATA_CODE, NAME FROM AIRLINE ORDER BY IATA_CODE");
            this.saveAirlineStatement = conn.prepareStatement("INSERT INTO AIRLINE (IATA_CODE, NAME) VALUES (?, ?)");
        } catch (SQLException e) {
            throw new DBException("Error inesperado creando statements, posible error en la base de datos", e); // Should never happen
        }
    }

	public static AirlineDAO getAirlineDAO() {
        if (airlineDAO == null) {
            airlineDAO = new AirlineDAO();
        }
        return airlineDAO;
    }

    @Override
    public Airline get(Object param) {
        if (!(param instanceof String in)) {
            throw new InvalidParameterException("Parámetro de búsqueda inválido. (Se esperaba (String) IATA).");
        }
        try {
            getAirlineByIdStatement.setString(1, in);
            ResultSet rs = getAirlineByIdStatement.executeQuery();
            if (rs.isBeforeFirst() && rs.next()) {
                return new Airline(in, rs.getString("NAME"));
            }
        } catch (SQLException e) {
            throw new DBException("Error inesperado consultando la BBDD, posible error en la BBDD", e); // Should never happen
        }
        return null;
    }
    

    @Override
    public List<Airline> getAll() {
        ArrayList<Airline> airlines = new ArrayList<>();
        try {
            ResultSet rs = getAllAirlinesStatement.executeQuery();
            while (rs.next()) {
                airlines.add(new Airline(rs.getString("IATA_CODE"), rs.getString("NAME")));
            }
        } catch (SQLException e) {
            throw new DBException(e);
        }
        return airlines;
    }

    @Override
    public void save(Airline airline) {
        try {
        	System.out.println("Insertando aerolínea: IATA = " + airline.getIata() + ", NAME = " + airline.getName());
        	
            saveAirlineStatement.setString(1, airline.getIata());
            saveAirlineStatement.setString(2, airline.getName());
            
            int rowsAffected = saveAirlineStatement.executeUpdate();
            System.out.println("Filas afectadas: " + rowsAffected);
            
            saveAirlineStatement.executeUpdate();
            
        } catch (SQLException e) {
        	System.err.println("Error al guardar la aerolínea: " + e.getMessage());
            throw new DBException(e); 	
        }
    }

    @Override
    public void update(Airline airline) {
        throw new UnsupportedOperationException("No se pueden actualizar los datos de una aerolínea");
    }

    @Override
    public void delete(Airline airline) {
        throw new UnsupportedOperationException("No se pueden eliminar los datos de una aerolínea");
    }
}