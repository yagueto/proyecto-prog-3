package db;

import domain.Airline;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AirlineDAO implements Dao<Airline> {
    private static AirlineDAO airlineDAO;

    private final Connection conn;
    private final PreparedStatement getAirlineByIdStatement;
    private final PreparedStatement getAllAirlinesStatement;
    private final PreparedStatement saveAirlineStatement;
    private final PreparedStatement updateAirlineStatement;
    private final PreparedStatement deleteAirlineStatement;

    private AirlineDAO() {
        this.conn = DBManager.getDBManager().conn;
        try {
            this.getAirlineByIdStatement = conn.prepareStatement("SELECT NAME FROM AIRLINE WHERE IATA_CODE=?");
            this.getAllAirlinesStatement = conn.prepareStatement("SELECT IATA_CODE, NAME FROM AIRLINE");
            this.saveAirlineStatement = conn.prepareStatement("INSERT INTO AIRLINE (IATA_CODE, NAME) VALUES (?, ?)");
            this.updateAirlineStatement = conn.prepareStatement("UPDATE AIRLINE SET NAME=? WHERE IATA_CODE=?");
            this.deleteAirlineStatement = conn.prepareStatement("DELETE FROM AIRLINE WHERE IATA_CODE=?");
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
        String in = (String) param;
        try {
            getAirlineByIdStatement.setString(1, in);
            ResultSet rs = getAirlineByIdStatement.executeQuery();
            if (rs.isBeforeFirst()) {
                return new Airline(in, rs.getString("NAME"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
            throw new RuntimeException(e);
        }
        return airlines;
    }

    @Override
    public void save(Airline airline) {
        try {
            saveAirlineStatement.setString(1, airline.getIata());
            saveAirlineStatement.setString(2, airline.getName());
            saveAirlineStatement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Airline airline) {
        try {
            updateAirlineStatement.setString(1, airline.getIata());
            updateAirlineStatement.setString(2, airline.getName());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Airline airline) {
        try {
            deleteAirlineStatement.setString(1, airline.getIata());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
