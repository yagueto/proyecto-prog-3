package main;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import domain.Airline;
import domain.Airport;
import domain.Flight;
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
        Thread a = new Thread(() -> Airline.loadAirline());
        a.start();
        Thread b = new Thread(() -> Airport.loadAirports());
        b.start();
        Thread c = new Thread(() -> Flight.loadFlights());
        try {
            a.join();
            b.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        c.start();
        try {
            c.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> new LoginWindow());
    }

}
