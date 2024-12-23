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

    private final PreparedStatement getByStatement;
    private final PreparedStatement getAllStatement;
    //private final PreparedStatement searchStatement;
    private final PreparedStatement saveStatement;
    //private final PreparedStatement updateStatement;
    private final PreparedStatement deleteStatement;

    private BookingDAO() {
        Connection conn = DBManager.getDBManager().conn;
        try {
            this.getByStatement = conn.prepareStatement("SELECT * FROM BOOKING WHERE ?=?");
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
            getByStatement.setString(1, "ID");
            getByStatement.setInt(2, in);
            ResultSet rs = getByStatement.executeQuery();
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

    public List<Booking> getBy(Object param, String type) {
        List<String> validTypes = List.of("ID", "FLIGHT", "USER");
        if (validTypes.contains(type)) {
            try {
                if (type.equals("FLIGHT")){
                    String parametro = (String) param;
                    getByStatement.setString(2, parametro);
                } else {
                    Integer parametro = (Integer) param;
                    getByStatement.setInt(2, parametro);
                }
                getByStatement.setString(1, validTypes.get(validTypes.indexOf(type)));
                ResultSet rs = getByStatement.executeQuery();
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
        // TODO: revisar esto
        System.err.println(type + " no es un tipo de búsqueda válida para Booking");
        return null;
    }
}