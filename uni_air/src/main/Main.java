package main;

import db.DBManager;
import gui.AbstractWindow;
import gui.LoginWindow;
import io.PropertiesManager;

import javax.swing.*;

public class Main {

    public static AbstractWindow w;

    public static void main(String[] args) {
        DBManager.connect(
                (String) PropertiesManager.getProperty("db_driver"),
                (String) PropertiesManager.getProperty("db_connectionString"),
                (String) PropertiesManager.getProperty("db_path")
        );
        System.out.println("Lanzando...");
        //SwingUtilities.invokeLater(() -> new EmployeeWindow());
        SwingUtilities.invokeLater(() -> new LoginWindow());

    }

}
