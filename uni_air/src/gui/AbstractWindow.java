package gui;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.border.Border;

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
	 * @param frame:	El JFrame a configurar
	 */
	
	public AbstractWindow () {
		this.setLocationRelativeTo(null);
		this.setSize(640, 480);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// TODO: añadir título a la ventana
		// TODO: podría ser interesante añadir un icono a la ventana
	}
	
	/*
	 * Crea un borde estándar para utilizar en el JFrame
	 * @param	title	el título a añadir al borde
	 * @return			el borde
	 */
	protected Border createBorder(String title) {
		Border border = BorderFactory.createTitledBorder(title);
		return border;
	}
	
	
}
