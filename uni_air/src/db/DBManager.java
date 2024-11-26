package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBManager {

    private static Connection conn = null;

    public static void connect(String driver, String connection_string, String path) {
        System.out.println("Abriendo conexión a BBDD");
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(connection_string + path);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Error cargando el driver de la BD: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Error conectando a la BD" + e.getMessage());
        }
    }

    public static void disconnect() {
        try {
            conn.close();
            System.out.println("Cerrada conexión a BBDD");
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
