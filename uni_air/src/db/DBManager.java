package db;

import io.PropertiesManager;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;


public class DBManager {
    private static DBManager dbManager;
    protected Connection conn = null;


    private DBManager() {
        this.connect((String) PropertiesManager.getProperty("db_driver"), (String) PropertiesManager.getProperty("db_connectionString"), (String) PropertiesManager.getProperty("db_path"));
    }

    public synchronized static DBManager getDBManager() {
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

    private synchronized void connect(String driver, String connection_string, String path) {
        System.out.println("Abriendo conexión a BBDD");
        try {
            Class.forName(driver);
            File f;
            if (!(f = new File(path)).exists()) {
                if (!f.getParentFile().exists()) {
                    Files.createDirectories(f.getParentFile().toPath());
                }
                conn = DriverManager.getConnection(connection_string + path);
                this.createDB();
            } else {
                conn = DriverManager.getConnection(connection_string + path);
            }


        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "No se ha podido cargar el driver JDBC", "ERROR", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        } catch (SQLException e) {
            System.err.println("Error conectando a la BD: " + e.getMessage());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "No se ha podido crear la BD", "ERROR", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    public void createDB() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream("/db/initial_schema.sql"))))) {
            Statement statement = conn.createStatement();
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
            JOptionPane.showMessageDialog(null, "No se ha encontrado el archivo de configuración de la base de datos y ésta no existe. No se puede continuar", "ERROR", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        } catch (SQLException | IOException e) {
            JOptionPane.showMessageDialog(null, "El archivo de configuración de la base de datos está corrupto.", "ERROR", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }


}
