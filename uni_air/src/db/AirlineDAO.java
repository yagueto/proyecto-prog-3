package db;

import domain.Airline;

import java.security.InvalidParameterException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
            this.getAllAirlinesStatement = conn.prepareStatement("SELECT * FROM AIRLINE");
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
    public Airline get(int id) {
        throw new InvalidParameterException("Se debe buscar aerolínea por código IATA (String).");
    }

    @Override
    public Airline get(String param) {
        try {
            getAirlineByIdStatement.setString(1, param);
            ResultSet rs = getAirlineByIdStatement.executeQuery();
            if (rs.isBeforeFirst()) {
                return new Airline(param, rs.getString("NAME"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Airline> getAll() {
        // WIP
        return List.of();
    }

    @Override
    public void save(Airline airline) {
        // WIP
    }

    @Override
    public void update(Airline airline) {
        // WIP
    }

    @Override
    public void delete(Airline airline) {
        // WIP
    }
}
