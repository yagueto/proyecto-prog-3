package testDAO;

import db.BookingDAO;
import db.CheckInDAO;
import db.FlightDAO;
import db.UserDAO;
import domain.*;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;

public class CheckInDAOTest {

    private CheckInDAO checkInDAO = CheckInDAO.getCheckInDAO();
    private BookingDAO bookingDAO = BookingDAO.getBookingDAO();
    private UserDAO userDAO = UserDAO.getUserDAO();
    private FlightDAO flightDAO = FlightDAO.getFlightDAO();

    private Customer customer;
    private Flight flight;
    private Booking booking;

    @Before
    public void setUp() {

        // Creamos datos iniciales necesarios

        Airport origin = new Airport("JFK", "JFK IA", "NEW YORK", "USA", 73.7781, 40.6413);
        Airport destination = new Airport("LAX", "LAX IA", "LOS ANGELES", "USA", 118.4085, 33.9416);
        Airline airline = new Airline("AA", "AMERICAN AIRLINES");

        customer = new Customer(1, "John", "Doe", "johndoe@example.com", "password123", LocalDate.of(1990, 1, 1));
        flight = new Flight("FL123", origin, destination, airline, LocalDateTime.of(2023, 11, 20, 14, 0),
                LocalDateTime.of(2023, 11, 20, 18, 0), 100, 200
        );
        booking = new Booking(customer, flight, 1);

        flightDAO.save(flight);
        userDAO.save(customer);
        bookingDAO.save(booking);
    }

    @Test
    public void testSaveCheckIn() {
        try {
            CheckIn checkIn = new CheckIn(booking);

            checkInDAO.save(checkIn);

            CheckIn retrievedCheckIn = checkInDAO.get(checkIn.getId());

            assertNotNull("El check-in debería estar en la base de datos.", retrievedCheckIn);

            assertEquals("El asiento debería coincidir.", checkIn.getSeat(), retrievedCheckIn.getSeat());
            assertEquals("El ID de la reserva debería coincidir.", checkIn.getBooking().getId(), retrievedCheckIn.getBooking().getId());
        } catch (Exception e) {
            e.printStackTrace();
            fail("Falló al guardar el check-in: " + e.getMessage());
        }
    }

    @Test
    public void testGetCheckIn() {
        try {
            CheckIn checkIn = new CheckIn(booking);
            checkInDAO.save(checkIn);

            List<CheckIn> checkIns = checkInDAO.getAll();

            assertFalse("La lista de check-ins no debería estar vacía.", checkIns.isEmpty());

            CheckIn retrievedCheckIn = checkIns.get(0);

            assertEquals("El ID debería ser el mismo.", checkIn.getId(), retrievedCheckIn.getId());
            assertEquals("El asiento debería ser el mismo.", checkIn.getSeat(), retrievedCheckIn.getSeat());
        } catch (Exception e) {
            e.printStackTrace();
            fail("Falló al recuperar el check-in: " + e.getMessage());
        }
    }
}
