package main;

import java.time.LocalDateTime;
import java.util.List;

import javax.swing.SwingUtilities;

import gui.AbstractWindow;
import gui.EmployeeWindow;
import gui.UserWindow;

public class Main {

	public static AbstractWindow w;
	public static List<Vuelo> vuelos = List.of(
			new Vuelo("IB1234", "LCG", "BIO", LocalDateTime.of(2024, 1, 1, 13, 0), LocalDateTime.of(2024, 1, 1, 13, 0), 19,
					20, 1),
			new Vuelo("IB1234", "LCG", "BIO", LocalDateTime.of(2024, 1, 1, 13, 0), LocalDateTime.of(2024, 1, 1, 13, 0), 19,
					20, 1),
			new Vuelo("IB1234", "LCG", "BIO", LocalDateTime.of(2024, 1, 1, 13, 0), LocalDateTime.of(2024, 1, 1, 13, 0), 19,
					20, 1),
			new Vuelo("IB1234", "LCG", "BIO", LocalDateTime.of(2024, 1, 1, 13, 0), LocalDateTime.of(2024, 1, 1, 13, 0), 19,
					20, 1)

	);

	public static void main(String[] args) {
		System.out.println("Lanzando...");
		// SwingUtilities.invokeLater(() -> new EmployeeWindow());
		// SwingUtilities.invokeLater(() -> new UserWindow());
	}

}
