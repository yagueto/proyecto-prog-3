package testDAO;

import db.BookingDAO;
import db.FlightDAO;
import db.UserDAO;
import domain.*;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.Assert.*;


public class BookingDAOTest {

    BookingDAO bookingDAO = BookingDAO.getBookingDAO();
    Customer customer;
    Flight flight;

    @Before
    public void setUp() {

        bookingDAO.clearTable();
        UserDAO.getUserDAO().clearTable();
        FlightDAO.getFlightDAO().clearTable();

        Airport origin = new Airport("JFK", "JFK IA", "NEW YORK", "USA", 73.7781, 40.6413);
        Airport destination = new Airport("LAX", "LAX IA", "LOS ANGELES", "USA", 118.4085, 33.9416);
        Airline airline = new Airline("AA", "AMERICAN AIRLINES");

        flight = new Flight("IB4321", origin, destination, airline, LocalDateTime.of(2023, 11, 20, 10, 0),
                LocalDateTime.of(2023, 11, 20, 14, 0), 150, 300
        );

        // Inicializar cliente
        customer = new Customer(1, "NAME", "SURNAME", "EJEMPLO@GMAIL.COM", "CONTRASEÑA", LocalDate.of(1990, 1, 1));


        if (UserDAO.getUserDAO().get(customer.getDni()) == null) {
            UserDAO.getUserDAO().save(customer);
        }
        if (FlightDAO.getFlightDAO().get(flight.getCodigo()) == null) {
            FlightDAO.getFlightDAO().save(flight);
        }
    }

    @Test
    public void testSaveBooking() {
        try {

            Booking booking = new Booking(customer, flight, 4231);

            bookingDAO.delete(booking);

            bookingDAO.save(booking);

            Booking retrievedBooking = bookingDAO.get(4231);

            assertNotNull("El booking debería estar en la base de datos.", retrievedBooking);

            assertEquals(customer.getDni(), retrievedBooking.getCustomer().getDni());
            assertEquals(flight.getCodigo(), retrievedBooking.getFlight().getCodigo());

        } catch (Exception e) {
            e.printStackTrace();
            fail("Inserción de datos fallida: " + e.getMessage());
        }
    }

    @Test
    public void testGetBooking() {
        try {
            Booking booking = new Booking(customer, flight, 8765);

            bookingDAO.delete(booking);

            bookingDAO.save(booking);

            Booking retrievedBooking = bookingDAO.get(8765);

            assertNotNull("La reserva debería existir en la base de datos.", retrievedBooking);

            assertEquals("El ID de la reserva debería coincidir.", booking.getId(), retrievedBooking.getId());
            assertEquals("El dni del cliente debería coincidir.", booking.getCustomer().getDni(), retrievedBooking.getCustomer().getDni());
            assertEquals("El código del vuelo debería coincidir.", booking.getFlight().getCodigo(), retrievedBooking.getFlight().getCodigo());
        } catch (Exception e) {
            e.printStackTrace();
            fail("Error al recuperar la reserva: " + e.getMessage());
        }
    }


}
