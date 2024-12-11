package io;

import javax.swing.*;
import java.io.*;
import java.util.Properties;

public class PropertiesManager {
    private static final String propertiesPath = "conf/config.properties";
    private static Properties properties;

    public static Object getProperty(String key) {
        if (properties == null) {
            properties = new Properties();
            loadConfigFromFile();
        }
        return properties.getOrDefault(key, null);
    }

    private static void loadConfigFromFile() {
        try (FileInputStream fis = new FileInputStream(propertiesPath)) {
            properties.load(fis);
            System.out.println("Se ha cargado el archivo de configuración.");
        } catch (FileNotFoundException e) {
            System.out.println("No existe el archivo de configuración, se crea un archivo con configuración por defecto");
            createDefaultConfiguration();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void createDefaultConfiguration() {
        properties.setProperty("db_driver", "org.sqlite.JDBC");
        properties.setProperty("db_connectionString", "jdbc:sqlite:");
        properties.setProperty("db_path", "resources/db/data.db");
        File directory = new File("conf/");
        if (!directory.exists()) {
            boolean success = directory.mkdir();
            if (!success) {
                JOptionPane.showMessageDialog(null, "ERROR", "No se ha podido crear el fichero de " +
                        "configuración. La carpeta " + directory.getParent() + " no existe y no se ha podido crear " +
                        "(¿Faltan permisos?). La aplicación no puede continuar.", JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
        }
        try (FileOutputStream salida = new FileOutputStream(propertiesPath)) {
            properties.store(salida, "Configuración por defecto de la app");
            System.out.println("Se ha creado el archivo de configuración.");
        } catch (IOException e) {
            System.err.println("ERROR: no se ha podido crear un archivo de configuración. El directorio 'conf' puede no existir o no haber suficientes permisos");
        }
        loadConfigFromFile();
    }
}
