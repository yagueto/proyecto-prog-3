package main;

import javax.swing.SwingUtilities;

import gui.AbstractWindow;
import gui.LoginWindow;

public class Main {

	public static AbstractWindow w;

	public static void main(String[] args) {
		
		System.out.println("Lanzando...");
		//SwingUtilities.invokeLater(() -> new EmployeeWindow());
		SwingUtilities.invokeLater(() -> new LoginWindow());
	}

}
