package testDAO;

import db.AirportDAO;
import domain.Airport;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class AirportDAOTest {

    AirportDAO airportDAO = AirportDAO.getAirportDAO();

    // AMBOS TESTS CORREN SIN FALLAR
    // COMPROBADA LA INSERCIÓN Y DEVOLUCION/SELECCION DE DATOS DE LA DB

    @Test
    public void testSaveAirport() {
        try {

            Airport airport = new Airport("ABC", "AIRPORT", "CITY", "COUNTRY", 45.0, 25.0);

            airportDAO.delete(airport);

            airportDAO.save(airport);

            Airport retrievedAirport = airportDAO.get("ABC");
            assertNotNull("El aeropuerto debería estar en la base de datos.", retrievedAirport);
            assertEquals("ABC", retrievedAirport.getIata());
            assertEquals("AIRPORT", retrievedAirport.getName());
        } catch (Exception e) {
            e.printStackTrace();
            fail("Inserción de datos fallida: " + e.getMessage());
        }
    }

    @Test
    public void testGetAirport() {
        try {
            airportDAO.deleteAll();

            Airport airport = new Airport("DFG", "PALOMA", "BILBAO", "ESPANA", 40.0, 20.0);

            airportDAO.save(airport);

            List<Airport> airports = airportDAO.getAll();

            Airport retrievedAirport = airports.get(0);

            assertEquals(retrievedAirport.getIata(), "DFG");
            assertEquals(retrievedAirport.getName(), "PALOMA");
            assertEquals(retrievedAirport.getCity(), "BILBAO");
            assertEquals(retrievedAirport.getCountry(), "ESPANA");
            assertEquals(40.0, retrievedAirport.getLongitude(), 0.001);
            assertEquals(20.0, retrievedAirport.getLatitude(), 0.001);


        } catch (Exception e) {
            e.printStackTrace();
            fail("Recuperación de datos fallida");
            e.getMessage();
        }
    }

}
