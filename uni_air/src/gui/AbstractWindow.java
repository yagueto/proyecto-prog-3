package gui;

import javax.swing.JFrame;

/*
 * Clase de utilidades para la configuración de las ventanas.
 */

public abstract class AbstractWindow extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/* 
	 * Configura las propiedades al crear la ventana.
	 * @param frame	El JFrame a configurar
	 */
	public AbstractWindow () {
		this.setLocationRelativeTo(null);
		this.setSize(640, 480);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// TODO: podría ser interesante añadir un icono a la ventana
		
	}
}
