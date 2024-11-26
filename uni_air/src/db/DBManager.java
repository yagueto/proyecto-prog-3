package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBManager {
	
	private static Connection conn = null; 
	
	public static void connect(String path) {
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:" + path);
		} catch (ClassNotFoundException e) {
			System.out.println("Error cargando el driver de la BD" + e.getMessage());
			
		} catch (SQLException e) {
			System.out.println("Error conectando a la BD" + e.getMessage());
		}
	}
	
	public static void disconnect() {
		try {
			conn.close();
		} catch (SQLException e) {
			System.out.println("Error cerrando la conexión con la BD" + e.getMessage());
			
		}
	}
	
	/*
	 * OIHAN
	public static User login(String email, String password) {
		
	}
	*/

	
}
