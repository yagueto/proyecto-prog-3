package db;

import io.PropertiesManager;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class DBManager {
    private static DBManager dbManager;
    private Connection conn = null;

    private DBManager() {
        this.connect(
                (String) PropertiesManager.getProperty("db_driver"),
                (String) PropertiesManager.getProperty("db_connectionString"),
                (String) PropertiesManager.getProperty("db_path")
        );
    }

    public static DBManager getDBManager() {
        if (dbManager == null) {
            dbManager = new DBManager();
        }
        return dbManager;
    }

    public static void disconnect() {
        if (dbManager == null) {
            return;
        }
        try {
            dbManager.conn.close();
            System.out.println("Cerrada conexión a BBDD");
        } catch (SQLException e) {
            System.out.println("Error cerrando la conexión con la BD" + e.getMessage());
        }
    }

    private void connect(String driver, String connection_string, String path) {
        System.out.println("Abriendo conexión a BBDD");
        try {
            Class.forName(driver);
            if (!new File(path).exists()) {
                conn = DriverManager.getConnection(connection_string + path);
                this.createDB();
            } else {
                conn = DriverManager.getConnection(connection_string + path);
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Error cargando el driver de la BD: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Error conectando a la BD" + e.getMessage());
        }
    }

    public void createDB() {
        try {
            Statement statement = conn.createStatement();
            BufferedReader reader = new BufferedReader(new FileReader("resources/db/initial_schema.sql"));
            StringBuilder query = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                query.append(line);
                if (line.trim().endsWith(";")) {
                    statement.execute(query.toString().trim());
                    query = new StringBuilder();
                }
            }

            System.out.println("Tablas creadas");

        } catch (FileNotFoundException e) {
            System.err.println("No se ha encontrado el archivo básico de configuración de BBDD. No se puede crear.");
            throw new RuntimeException("No existe el archivo básico de configuración de BBDD.");
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

	/*
	 * OIHAN
	public static User login(String email, String password) {
		
	}
	*/


}
