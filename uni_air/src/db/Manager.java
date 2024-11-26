package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class Manager {
	
	private Connection conn = null; 
	
	public void connect(String path) throws CustomException {
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:" + path);
		} catch (ClassNotFoundException e) {
			throw new CustomException("Error cargando el driver de la BD", e);
		} catch (SQLException e) {
			throw new CustomException("Error conectando a la BD", e);
		}
	}
	
	public void disconnect() throws CustomException {
		try {
			conn.close();
		} catch (SQLException e) {
			throw new CustomException("Error cerrando la conexión con la BD", e);
			
		}
	}
	
}
