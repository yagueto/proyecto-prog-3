package gui;

import db.DBManager;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
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
        this.setSize(1080, 720);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                exit();
            }
        });

        this.setTitle("Uni-Air");

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
        Thread t = new Thread(() -> {
            DBManager.disconnect();
            System.exit(0);
        });
        t.start();

    }

    public void resizeColumnWidth(JTable table) {
        final TableColumnModel columnModel = table.getColumnModel();
        for (int column = 0; column < table.getColumnCount(); column++) {
            int width = 15; // Min width
            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer renderer = table.getCellRenderer(row, column);
                Component comp = table.prepareRenderer(renderer, row, column);
                width = Math.max(comp.getPreferredSize().width +1 , width);
            }
            if(width > 300)
                width=300;
            columnModel.getColumn(column).setPreferredWidth(width);
        }
    }
}
