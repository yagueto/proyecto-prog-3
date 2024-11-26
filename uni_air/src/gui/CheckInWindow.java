package gui;

import domain.Vuelo;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class CheckInWindow extends JFrame{
    public CheckInWindow(JFrame frame, Vuelo vuelo){
        this.setSize(640, 480);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setTitle("Check-In");

        JPanel panel = new JPanel();
        BoxLayout layout = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(layout);

        Border border = BorderFactory.createEmptyBorder(60, 10, 40, 10);
        panel.setBorder(BorderFactory.createTitledBorder(border, "CHECK-IN", TitledBorder.CENTER, TitledBorder.TOP, new Font("Arial", Font.PLAIN, 24)));

        JPanel panelInfor = new JPanel();
        JTextArea infor = new JTextArea();
        infor.setText(
                "Vuelo: " + vuelo.getCodigo() + " | " +
                "Origen: " + vuelo.getOrigen() + " | " +
                "Destino: " + vuelo.getDestino()
        );
        infor.setEditable(false);
        infor.setOpaque(false);
        panelInfor.add(infor);
        panel.add(panelInfor);

        JPanel panelCodigo = new JPanel();
        JTextField codigoEmpleado  = new JTextField(20);
        panelCodigo.add(codigoEmpleado);
        panel.add(panelCodigo, BorderLayout.SOUTH);

        this.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                if (frame != null){
                    frame.setVisible(true);
                }
                dispose();
            }
        });

        this.add(panel);
        setVisible(true);
    }
}