package db;

import domain.CheckIn;

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

    private CheckInDAO(){
        Connection conn = DBManager.getDBManager().conn;
        try {
            this.getByIdStatement = conn.prepareStatement("SELECT * FROM CHECK_IN WHERE ID=?");
            this.getAllStatement = conn.prepareStatement("SELECT * FROM CHECK_IN");
            this.saveStatement = conn.prepareStatement("INSERT INTO CHECK_IN (ID, SEAT, BOOKING) VALUES (?, ?, ?)");
            //this.searchStatement = conn.prepareStatement("");
            this.updateStatement = conn.prepareStatement("UPDATE CHECK_IN SET ID=?, SEAT=?, BOOKING=? WHERE ID=?");
            this.deleteStatement = conn.prepareStatement("DELETE FROM CHECK_IN WHERE ID=?");
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
            throw new RuntimeException("Parámetro de búsqueda inválido. (Se esperaba (String) ID).");
        }
        try {
            getByIdStatement.setInt(1, in);
            ResultSet rs = getByIdStatement.executeQuery();
            if (rs.isBeforeFirst()) {
                return new CheckIn(BookingDAO.getBookingDAO().get(rs.getInt("BOOKING")), rs.getString("SEAT"), rs.getInt("ID"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<CheckIn> getAll() {
        ArrayList<CheckIn> checkIns = new ArrayList<>();
        try {
            ResultSet rs = getAllStatement.executeQuery();
            while (rs.next()){
                CheckIn checkIn = new CheckIn(BookingDAO.getBookingDAO().get(rs.getInt("BOOKING")), rs.getString("SEAT"), rs.getInt("ID"));
                checkIns.add(checkIn);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return checkIns;
    }

    @Override
    public void save(CheckIn checkIn) {
        try {
            saveStatement.setInt(1, checkIn.getId());
            saveStatement.setString(2, checkIn.getSeat());
            saveStatement.setInt(3, checkIn.getBooking().getId());
            saveStatement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // método pendiente de implementación
    @Override
    public void update(CheckIn checkIn) {
        try {
            updateStatement.setInt(1, checkIn.getId());
            updateStatement.setString(2, checkIn.getSeat());
            updateStatement.setInt(3, checkIn.getBooking().getId());
        } catch (SQLException e){
            e.printStackTrace();

            System.out.println("No se ha podido actualizar en la base de datos");

        }
    }

    @Override
    public void delete(CheckIn checkIn) {
        try {
            deleteStatement.setInt(1, checkIn.getId());
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("No se ha podido eliminar de la db");
        }
    }
}
