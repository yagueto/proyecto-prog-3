package main;

import com.formdev.flatlaf.themes.FlatMacLightLaf;

import db.DBManager;
import gui.LoginWindow;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
    	SwingUtilities.invokeLater(() -> new LoginWindow());
        System.out.println("Lanzando...");
        try {
            UIManager.setLookAndFeel(new FlatMacLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
            System.err.println("ERROR: No se ha encontrado la librería de Look-and-Feel FlatLAF. Utilizando el LAF " +
                    "por defecto.");
        }
        //SwingUtilities.invokeLater(() -> new EmployeeWindow());
       
        
		
    }

}
