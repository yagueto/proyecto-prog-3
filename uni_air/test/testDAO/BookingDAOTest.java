package testDAO;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


import org.junit.Before;
import org.junit.Test;

import db.BookingDAO;

import domain.Airline;
import domain.Airport;
import domain.Booking;
import domain.Customer;
import domain.Flight;


// ID, ORIGIN_AIRPORT, DEST_AIRPORT, AIRLINE_CODE, DEPARTURE_TIME, ARRIVAL_TIME, MAX_PASAJEROS, PRECIO
public class BookingDAOTest {
	
	BookingDAO bookingDAO = BookingDAO.getBookingDAO();

	Customer customer;
	Flight flight;
	
	@Before
    public void setUp() {
        // Inicializar datos requeridos
        Airport origin = new Airport("JFK", "JFK IA", "NEW YORK", "USA", 73.7781, 40.6413);
        Airport destination = new Airport("LAX", "LAX IA", "LOS ANGELES", "USA", 118.4085, 33.9416);
        Airline airline = new Airline("AA", "AMERICAN AIRLINES");

        // Inicializar vuelo
        flight = new Flight(
        	"IB4321",
            origin,
            destination,
            airline,
            LocalDateTime.of(2023, 11, 20, 10, 0),  
            LocalDateTime.of(2023, 11, 20, 14, 0),  
            150,                                    
            300                                     
        );

        // Inicializar cliente
        customer = new Customer(1, "NAME", "SURNAME", "EJEMPLO@GMAIL.COM", "CONTRASEÑA", LocalDate.of(1990, 1, 1));
    }
	
	@Test
	public void testSaveBooking() {
		try {
					
	        Booking booking = new Booking(customer, flight, 1234);
	       
	        bookingDAO.delete(booking);

	        bookingDAO.save(booking);

	        Booking retrievedBooking = bookingDAO.get(1234);
	        assertNotNull("El booking debería estar en la base de datos.", retrievedBooking);
	        assertEquals(customer.getDni(), retrievedBooking.getCustomer().getDni());
	        assertEquals(flight.getCodigo(), retrievedBooking.getFlight().getCodigo());
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	        fail("Inserción de datos fallida: " + e.getMessage());
	    }
	}
	
	
	
	
}
