package gui;

import db.DBManager;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/*
 * Clase de utilidades para la configuración de las ventanas.
 */

public abstract class AbstractWindow extends JFrame {

    /**
     * UID serial
     */
    private static final long serialVersionUID = 14165416645L;

    /*
     * Configura las propiedades al crear la ventana.
     * @param frame:	El JFrame a configurar
     */
    public AbstractWindow() {
        this.setSize(640, 480);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                exit();
            }
        });

        // TODO: añadir título a la ventana
        // TODO: podría ser interesante añadir un icono a la ventana
    }

    /*
     * Crea un borde estándar para utilizar en el JFrame
     * @param	title	el título a añadir al borde
     * @return			el borde
     */
    protected Border createBorder(String title) {
        return BorderFactory.createTitledBorder(title);
    }

    protected void exit() {
        DBManager.disconnect();
        dispose();
    }
}
