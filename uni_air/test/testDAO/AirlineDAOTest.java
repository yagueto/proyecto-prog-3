package testDAO;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import db.AirlineDAO;
import db.AirportDAO;
import db.DBManager;
import domain.Airline;
import domain.Airport;

public class AirlineDAOTest { 
	
	AirlineDAO airlineDAO = AirlineDAO.getAirlineDAO();
	
	// HAY UN ERROR EN LA CREACIÓN DE LOS STATEMENTS ES AIRLINEDAO (no lo he podido resolver todavia)
	// (da el error en ambos tests porque aplican la misma funcion save() en la que probablemente esté el error de SQL)
	
	@Test
	public void testSaveAirline() {
		try {
			
	        Airline airline = new Airline("COD", "AIRLINE");

	        airlineDAO.delete(airline);

	        airlineDAO.save(airline);

	        Airline retrievedAirline = airlineDAO.get("COD");
	        assertNotNull("La aerolinea debería estar en la base de datos.", retrievedAirline);
	        assertEquals("COD", retrievedAirline.getIata());
	        assertEquals("AIRLINE", retrievedAirline.getName());
	    } catch (Exception e) {
	        e.printStackTrace();
	        fail("Inserción de datos fallida: " + e.getMessage());
	    }
	}
	
	@Test
	public void testGetAirline() {
		try {
			airlineDAO.deleteAll();
			
			Airline airline = new Airline("ABC", "AIRLINE");
			
			airlineDAO.save(airline);
			
			List<Airline> airlines = airlineDAO.getAll();
			
			Airline retrievedAirline = airlines.get(0);
			
			assertEquals(retrievedAirline.getIata(), "ABC");
			assertEquals(retrievedAirline.getName(), "AIRLINE");
	        
			
		} catch (Exception e) {
			e.printStackTrace();
			fail("Recuperación de datos fallida");
			e.getMessage();
		}
	}

}

























