package gui;

import javax.swing.JFrame;

/*
 * Clase de utilidades para la configuración de las ventanas.
 */

public class Config {
	
	/* 
	 * Configura las propiedades al crear la ventana.
	 * @param frame	El JFrame a configurar
	 */
	protected static void windowConfig(JFrame frame) {
		frame.setLocationRelativeTo(null);
		frame.setSize(640, 480);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// TODO: podría ser interesante añadir un icono a la ventana
		
	}
}
