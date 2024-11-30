package main;

import gui.LoginWindow;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("Lanzando...");
        
        //SwingUtilities.invokeLater(() -> new EmployeeWindow());
        SwingUtilities.invokeLater(() -> new LoginWindow());
    }

}
