package db;

import io.PropertiesManager;

import java.io.*;
import java.sql.*;


public class DBManager {
    private static DBManager dbManager;
    protected Connection conn = null;


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

    public void insertarUsuario(int dni, String name, String surname, String mail, String password) {
		String sql = "INSERT INTO Usuario VALUES (?, ?, ?, ?, ?)";
		try {
            PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, dni);
			ps.setString(2, name);
			ps.setString(3, surname);
			ps.setString(4, mail);
			ps.setString(5, password);
			ps.execute();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

    public boolean existeUsuario(int dni) {
		boolean existe = false;
		String sql = "SELECT * FROM Usuario WHERE id = ?";
		try {
            PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, dni);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) { 
				existe = true;
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return existe;
	}
}
