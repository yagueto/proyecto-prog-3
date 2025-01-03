package db;

import domain.Booking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO implements Dao<Booking> {
    private static BookingDAO bookingDAO;

    private final Connection conn;
    private final PreparedStatement getByIdStatement;
    private final PreparedStatement getAllStatement;
    //private final PreparedStatement searchStatement;
    private final PreparedStatement saveStatement;
    //private final PreparedStatement updateStatement;
    private final PreparedStatement deleteStatement;

    private BookingDAO() {
        conn = DBManager.getDBManager().conn;
        try {
            this.getByIdStatement = conn.prepareStatement("SELECT * FROM BOOKING WHERE ID = ?");
            this.getAllStatement = conn.prepareStatement("SELECT * FROM BOOKING");
            this.saveStatement = conn.prepareStatement("INSERT INTO BOOKING (USER, FLIGHT) VALUES (?, ?)");
            //this.searchStatement = conn.prepareStatement("");
            //this.updateStatement = conn.prepareStatement("UPDATE BOOKING SET ID=?, USER=?, FLIGHT=? WHERE ID=?");
            this.deleteStatement = conn.prepareStatement("DELETE FROM BOOKING WHERE ID=?");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static BookingDAO getBookingDAO() {
        if (bookingDAO == null) {
            bookingDAO = new BookingDAO();
        }
        return bookingDAO;
    }

    @Override
    public Booking get(Object param) {
        if (!(param instanceof Integer in)) {
            throw new RuntimeException("Parámetro de búsqueda inválido. (Se esperaba (String) ID).");
        }
        try {
            getByIdStatement.setInt(1, in);
            ResultSet rs = getByIdStatement.executeQuery();
            if (rs.isBeforeFirst()) {
                return new Booking(UserDAO.getUserDAO().get(rs.getInt("USER")), FlightDAO.getFlightDAO().get(rs.getString("FLIGHT")), rs.getInt("ID"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<Booking> getAll() {
        ArrayList<Booking> bookings = new ArrayList<>();
        try {
            ResultSet rs = getAllStatement.executeQuery();
            while (rs.next()){
                Booking booking = new Booking(UserDAO.getUserDAO().get(rs.getInt("USER")), FlightDAO.getFlightDAO().get(rs.getInt("FLIGHT")), rs.getInt("ID"));
                bookings.add(booking);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return bookings;
    }

    @Override
    public void save(Booking booking) {
        try {
            saveStatement.setInt(1, booking.getCustomer().getDni());
            saveStatement.setString(2, booking.getFlight().getCodigo());
            saveStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Booking booking) {

    }

    @Override
    public void delete(Booking booking) {
        try {
            deleteStatement.setInt(1, booking.getId());
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("No se ha podido eliminar de la db");
        }
    }

    public List<Booking> getBy(Object param, BookingField type) {
        try (PreparedStatement statement = conn.prepareStatement("SELECT * FROM BOOKING WHERE " + type + " = ?")) {
            if (type == BookingField.FLIGHT) {
                statement.setString(1, (String) param);
            } else if (type == BookingField.ID || type == BookingField.USER) {
                statement.setInt(1, (Integer) param);
            } else {
                throw new IllegalArgumentException("Tipo de búsqueda no soportado: " + type);
            }
            ResultSet rs = statement.executeQuery();
            ArrayList<Booking> bookings = new ArrayList<>();
            while (rs.next()) {
                Booking booking = new Booking(UserDAO.getUserDAO().get(rs.getInt("USER")), FlightDAO.getFlightDAO().get(rs.getString("FLIGHT")), rs.getInt("ID"));
                bookings.add(booking);
            }
            return bookings;
        } catch (SQLException e) {
            throw new RuntimeException("Usuario inválido");
        }
    }
    public enum BookingField{
        ID, USER, FLIGHT
    }
}