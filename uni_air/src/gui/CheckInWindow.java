package gui;

import db.BookingDAO;
import db.CheckInDAO;
import domain.Booking;
import domain.CheckIn;
import domain.Flight;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.event.*;
import java.util.ArrayList;
import java.awt.*;

public class CheckInWindow extends JFrame{
    /**
     *
     */
    private static final long serialVersionUID = 8110919845978625779L;

    private final ArrayList<Booking> bookings;

    public CheckInWindow(JFrame frame, Flight flight) {
        this.setSize(640, 480);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setTitle("Check-In");
        this.bookings = new ArrayList<>(BookingDAO.getBookingDAO().getBy(flight.getCodigo(), BookingDAO.BookingField.FLIGHT));

        JPanel panel = new JPanel();
        BoxLayout layout = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(layout);

        Border border = BorderFactory.createEmptyBorder(60, 10, 40, 10);
        panel.setBorder(BorderFactory.createTitledBorder(border, "CHECK-IN", TitledBorder.CENTER, TitledBorder.TOP, new Font("Arial", Font.PLAIN, 24)));

        JPanel panelInfor = new JPanel();
        JTextArea infor = new JTextArea();
        infor.setText(
                "Vuelo: " + flight.getCodigo() + "\n" +
                        "Origen: " + flight.getOrigen().getName() + "\n" +
                        "Destino: " + flight.getDestino().getName() + "\n" +
                        "Pasajeros por pasar: " + bookings.size()
        );
        infor.setEditable(false);
        infor.setOpaque(false);
        panelInfor.add(infor);
        panel.add(panelInfor);

        JPanel panelCodigo = new JPanel();
        JTextField codigoEmpleado  = new JTextField(20);
        panelCodigo.add(codigoEmpleado);
        panel.add(panelCodigo, BorderLayout.SOUTH);

        JLabel mensaje = new JLabel();
        mensaje.setForeground(Color.BLACK);
        JPanel panelmensaje = new JPanel();
        panelmensaje.add(mensaje);
        panel.add(panelmensaje);

        JLabel asiento = new JLabel();
        asiento.setForeground(Color.BLACK);
        JPanel panelAsiento = new JPanel();
        panelAsiento.add(asiento);
        panel.add(panelAsiento);


        this.addWindowListener(new WindowAdapter() {

            @Override
            public void windowOpened(WindowEvent e) {
                codigoEmpleado.requestFocus();
            }

            @Override
            public void windowClosing(WindowEvent e) {
                if (frame != null){
                    frame.setVisible(true);
                }
                dispose();
            }
        });

        codigoEmpleado.addActionListener(e -> {
            try{
                if(!codigoEmpleado.getText().isEmpty()){
                    int dni = Integer.parseInt(codigoEmpleado.getText());
                    boolean dniCorrecto = false;
                    for (Booking booking : bookings){
                        if (booking.getCustomer().getDni() == dni){
                            CheckIn nuevo = new CheckIn(booking);
                            CheckInDAO.getCheckInDAO().save(nuevo);

                            asiento.setText("Su asiento es el " + nuevo.getSeat());
                            codigoEmpleado.setBackground(Color.GREEN);
                            mensaje.setText("Check-In creado correctamente");
                            mensaje.setForeground(Color.GREEN);
                            dniCorrecto = true;
                            break;
                        }
                    }
                    if (!dniCorrecto){
                        codigoEmpleado.setBackground(Color.RED);
                        mensaje.setText("DNI no encontrado");
                        mensaje.setForeground(Color.RED);
                    }
                }
            } catch (NumberFormatException ex){
                codigoEmpleado.setBackground(Color.RED);
                mensaje.setText("Formato incorrecto");
            }
        });

        this.add(panel);
        setVisible(true);
    }
}
