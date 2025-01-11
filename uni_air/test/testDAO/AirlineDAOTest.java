package testDAO;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import db.AirlineDAO;
import db.DBManager;
import domain.Airline;

public class AirlineDAOTest { 
	// DOS ERRORES VISTOS EN LA BD TRAS DEPURACIÓN:
	// DESPUES DE HACER EL TEST SALTA EL throws new SQLException(e); EN EL MÉTODO SAVE DE AIRLINEDAO
	// CON LOS DATOS METIDOS, DE NO CREAR OTRA TABLA AIRLINE (funcionalidad de los tests) RESTRICCIONES COMO UNIQUE Y LA PK DAN ERROR AL INSERTAR EL NOMBRE Y IATA
	
	AirlineDAO airlineDAO = AirlineDAO.getAirlineDAO();
	
	@Before
	public void setupDatabase() {
	    try (Connection con = DBManager.getDBManager().conn) {
	        con.createStatement().execute(
	            "CREATE TABLE IF NOT EXISTS AIRLINE (" +
	            "IATA_CODE VARCHAR(20) PRIMARY KEY, " +
	            "NAME VARCHAR(40))"
	        );
	    } catch (SQLException e) {
	        fail("Error al configurar la base de datos: " + e.getMessage());
	    }
	}
	
    @Test
    public void testSaveSuccessfullInsertion() throws SQLException{	
    	try {	
            // Crear una aerolínea de prueba
            Airline airline = new Airline("AMS", "Airline1");

            // Guardar la aerolínea con el método save()
            airlineDAO.save(airline);

            // Verificar que la aerolínea se insertó correctamente
            Airline retrievedAirline = airlineDAO.get("AMS");
            assertNotNull("La aerolínea debería estar en la base de datos.", retrievedAirline);
            assertEquals("AMS", retrievedAirline.getIata());
            assertEquals("Amsterdam Airport Schiphol", retrievedAirline.getName());
		} catch (Exception e) {
			fail("Inserción de datos fallida");
		}
    	
    }
    
    @Test
    public void testSaveAndRetrieveAll() throws SQLException{
    	try {
    		// Insertar varias aerolíneas
            airlineDAO.save(new Airline("AMS", "Airline1"));
            airlineDAO.save(new Airline("JFK", "Airline2"));
            airlineDAO.save(new Airline("LHR", "Airline3"));

            // Recuperar todas las aerolíneas
            List<Airline> airlines = airlineDAO.getAll();
            
            // Verificar que se recuperaron correctamente
            assertEquals(3, airlines.size());
            assertEquals(airlines.get(0).getName(), "Airline1");
            assertEquals(airlines.get(0).getIata(), "AMS");
            assertEquals(airlines.get(1).getName(), "Airline2");
            assertEquals(airlines.get(1).getIata(), "JFK");
            assertEquals(airlines.get(2).getName(), "Airline3");
            assertEquals(airlines.get(2).getIata(), "LHR");
            
		} catch (Exception e) {
			e.printStackTrace();
			fail("Recuperación de datos fallida");
			e.getMessage();
		}   	
       
    }
    @After
    public void cleanup() {
        try {
            Connection conn = DBManager.getDBManager().conn;
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

	
}




























