package main;

import com.formdev.flatlaf.themes.FlatMacLightLaf;


import gui.LoginWindow;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
    	
    	
    	
        System.out.println("Lanzando...");
        try {
            UIManager.setLookAndFeel(new FlatMacLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
            System.err.println("ERROR: No se ha encontrado la librería de Look-and-Feel FlatLAF. Utilizando el LAF " +
                    "por defecto.");
        }
        //SwingUtilities.invokeLater(() -> new EmployeeWindow());
        SwingUtilities.invokeLater(() -> new LoginWindow());
    }

}
