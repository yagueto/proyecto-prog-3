package db;

import domain.CheckIn;

import java.security.InvalidParameterException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CheckInDAO implements Dao<CheckIn> {
    private static CheckInDAO checkInDAO;

    private final PreparedStatement getByIdStatement;
    private final PreparedStatement getAllStatement;
    //private final PreparedStatement searchStatement;
    private final PreparedStatement saveStatement;
    private final PreparedStatement updateStatement;
    private final PreparedStatement deleteStatement;

    private CheckInDAO() {
        Connection conn = DBManager.getDBManager().conn;
        try {
            this.getByIdStatement = conn.prepareStatement("SELECT * FROM CHECK_IN WHERE BOOKING=?");
            this.getAllStatement = conn.prepareStatement("SELECT * FROM CHECK_IN");
            this.saveStatement = conn.prepareStatement("INSERT INTO CHECK_IN (BOOKING, SEAT) VALUES (?, ?)");
            //this.searchStatement = conn.prepareStatement("");
            this.updateStatement = conn.prepareStatement("UPDATE CHECK_IN SET BOOKING=?, SEAT=? WHERE BOOKING=?");
            this.deleteStatement = conn.prepareStatement("DELETE FROM CHECK_IN WHERE BOOKING=?");
        } catch (SQLException e) {
            throw new DBException("Error inesperado creando statements, posible error en la base de datos", e); // Should never happen
        }
    }

    public static CheckInDAO getCheckInDAO() {
        if (checkInDAO == null) {
            checkInDAO = new CheckInDAO();
        }
        return checkInDAO;
    }

    @Override
    public CheckIn get(Object param) {
        if (!(param instanceof Integer in)) {
            throw new InvalidParameterException("Parámetro de búsqueda inválido. (Se esperaba (Int) BOOKING).");
        }
        try {
            getByIdStatement.setInt(1, in);
            ResultSet rs = getByIdStatement.executeQuery();
            if (rs.isBeforeFirst() && rs.next()) {
                return new CheckIn(BookingDAO.getBookingDAO().get(rs.getInt("BOOKING")), rs.getString("SEAT"));
            }
        } catch (SQLException e) {
            throw new DBException(e);
        }
        return null;
    }

    @Override
    public List<CheckIn> getAll() {
        ArrayList<CheckIn> checkIns = new ArrayList<>();
        try {
            ResultSet rs = getAllStatement.executeQuery();
            while (rs.next()) {
                CheckIn checkIn = new CheckIn(BookingDAO.getBookingDAO().get(rs.getInt("BOOKING")), rs.getString("SEAT"));
                checkIns.add(checkIn);
            }
        } catch (SQLException e) {
            throw new DBException(e);
        }
        return checkIns;
    }

    @Override
    public void save(CheckIn checkIn) {
        try {
            saveStatement.setInt(1, checkIn.getBooking().getId());
            saveStatement.setString(2, checkIn.getSeat());
            saveStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }


    // metodo pendiente de implementación
    @Override
    public void update(CheckIn checkIn) {
        try {
            updateStatement.setString(2, checkIn.getSeat());
            updateStatement.setInt(1, checkIn.getBooking().getId());
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    @Override
    public void delete(CheckIn checkIn) {
        try {
            deleteStatement.setInt(1, checkIn.getBooking().getId());
            deleteStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }
}
