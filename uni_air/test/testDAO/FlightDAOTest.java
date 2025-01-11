package testDAO;

import db.AirlineDAO;
import db.AirportDAO;
import db.FlightDAO;
import domain.Airline;
import domain.Airport;
import domain.Flight;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;
	
// TANTO LA INSERCION COMO LA OBTENCION DE VUELOS DE LA DB NO FUNCIONAN CORRECTAMENTE
// INDICAN QUE LA TABLA AIRLINE NO ESTÁ CREADA CUANDO DEBERIA DE ESTARLO YA QUE 
// LA FUNCION SAVE() OBTIENE DEL DBMANAGER DICHA CONNEXION Y CREACION

public class FlightDAOTest {

    private final FlightDAO flightDAO = FlightDAO.getFlightDAO();
    private final AirportDAO airportDAO = AirportDAO.getAirportDAO();
    private final AirlineDAO airlineDAO = AirlineDAO.getAirlineDAO();
    private Airport origin;
    private Airport destination;
    private Airline airline;
    
    @Before
    public void setUp() {
    	
        flightDAO.clearTable();
        airportDAO.deleteAll();
        airlineDAO.deleteAll();

        origin = new Airport("JFK", "John F. Kennedy International Airport", "New York", "USA", 73.7781, 40.6413);
        destination = new Airport("LAX", "Los Angeles International Airport", "Los Angeles", "USA", 118.4085, 33.9416);
        airline = new Airline("AA", "AMERICAN AIRLINES");
    
        airportDAO.save(origin);
        airportDAO.save(destination);
        airlineDAO.save(airline);
    }

    @Test
    public void testSaveFlight() {
        try {
        	Flight flight = new Flight("IB4321", origin, destination, airline, LocalDateTime.of(2023, 11, 20, 10, 0),  
                    LocalDateTime.of(2023, 11, 20, 14, 0), 150, 300     
            );

            flightDAO.save(flight);

            Flight retrievedFlight = flightDAO.get(flight.getCodigo());

            assertNotNull("El vuelo debería haberse guardado en la base de datos.", retrievedFlight);

            assertEquals("El código del vuelo debería coincidir.", flight.getCodigo(), retrievedFlight.getCodigo());
            assertEquals("El aeropuerto de origen debería coincidir.", flight.getOrigen().getIata(), retrievedFlight.getOrigen().getIata());
            assertEquals("El aeropuerto de destino debería coincidir.", flight.getDestino().getIata(), retrievedFlight.getDestino().getIata());
            assertEquals("El número máximo de pasajeros debería coincidir.", flight.getMaxPasajeros(), retrievedFlight.getMaxPasajeros());
            assertEquals("La aerolínea debería coincidir.", flight.getAirline().getIata(), retrievedFlight.getAirline().getIata());
        } catch (Exception e) {
            fail("Falló al guardar o recuperar el vuelo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void testGetFlight() {
        try {
        	Flight flight = new Flight("IB4321", origin, destination, airline, LocalDateTime.of(2023, 11, 20, 10, 0),  
                    LocalDateTime.of(2023, 11, 20, 14, 0), 150, 300  
            );
            flightDAO.save(flight);

            List<Flight> flights = flightDAO.getAll();

            assertFalse("La lista de vuelos no debería estar vacía.", flights.isEmpty());

            Flight retrievedFlight = flights.get(0);

            assertEquals("El código del vuelo debería coincidir.", flight.getCodigo(), retrievedFlight.getCodigo());
            assertEquals("El aeropuerto de origen debería coincidir.", flight.getOrigen().getIata(), retrievedFlight.getOrigen().getIata());
            assertEquals("El aeropuerto de destino debería coincidir.", flight.getDestino().getIata(), retrievedFlight.getDestino().getIata());
            assertEquals("La aerolínea debería coincidir.", flight.getAirline().getIata(), retrievedFlight.getAirline().getIata());
        } catch (Exception e) {
            fail("Falló al recuperar el vuelo: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
