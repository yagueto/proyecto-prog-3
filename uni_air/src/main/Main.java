package main;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import gui.AbstractWindow;
import gui.EmployeeWindow;
import gui.UserWindow;

public class Main {

	public static AbstractWindow w;

	public static void main(String[] args) {
		System.out.println("Lanzando...");
		//SwingUtilities.invokeLater(() -> new EmployeeWindow());
		//SwingUtilities.invokeLater(() -> new UserWindow());
	}

}
