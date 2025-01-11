package testDAO;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import db.DBManager;

public class AirportDAOTest {
	
	@Before
	public void setupDatabase() {
	    try (Connection con = DBManager.getDBManager().conn) {
	        con.createStatement().execute(
	            ""
	        );
	    } catch (SQLException e) {
	        fail("Error al configurar la base de datos: " + e.getMessage());
	    }
	}
	
	@Test
	public void testSave() {
		fail("Not yet implemented");
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
