package gui;

import db.BookingDAO;
import db.UserDAO;
import domain.Booking;
import domain.Flight;

import javax.swing.*;
import java.awt.*;

public class BuyWindow extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = -5205219596554060756L;

    private final JTextField nameField;
    private final JTextField emailField;
    private final JTextField passportField;
    private final JTextField phoneField;

    private final JRadioButton paypalRadio;
    private final JRadioButton creditCardRadio;

    public BuyWindow(Flight flight) {
        super();
        this.setSize(640, 480);
        this.setLocationRelativeTo(null);

        // Panel al que se añaden los elementos
        JPanel panel = new JPanel();
        BoxLayout layout = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(layout);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Añade margen blanco alrededor de la ventana

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        // Detalles del vuelo
        JPanel flightDetailsPanel = getDetailsPanel(flight);

        // Nombre completo:
        JPanel userNamePanel = new JPanel();
        JLabel fullNameLabel = new JLabel("Nombre completo:");
        nameField = new JTextField(15);
        nameField.setText(UserDAO.getLoggedInUser().getName());
        nameField.setEditable(false);
        userNamePanel.add(fullNameLabel);
        userNamePanel.add(nameField);

        // Email
        JPanel emailPanel = new JPanel();
        JLabel emailLabel = new JLabel("Correo electrónico:");
        emailField = new JTextField(15);
        emailField.setText(UserDAO.getLoggedInUser().getMail());
        emailField.setEditable(false);
        emailPanel.add(emailLabel);
        emailPanel.add(emailField);

        // DNI / Pasaporte:
        JPanel passportPanel = new JPanel();
        JLabel passportLabel = new JLabel("DNI:                           ");
        passportField = new JTextField(15);
        passportField.setText(Integer.toString(UserDAO.getLoggedInUser().getDni()));
        passportField.setEditable(false);
        passportPanel.add(passportLabel, BorderLayout.WEST);
        passportPanel.add(passportField);

        // Número de teléfono
        JPanel phonePanel = new JPanel();
        JLabel phoneLabel = new JLabel("Número de teléfono:");
        phoneField = new JTextField(15);
        phonePanel.add(phoneLabel, BorderLayout.WEST);
        phonePanel.add(phoneLabel);
        phonePanel.add(phoneField);

        // Payment method
        JPanel paymentPanel = new JPanel();
        JLabel paymentLabel = new JLabel("Método de pago:");
        ButtonGroup buttonGroup = new ButtonGroup();
        creditCardRadio = new JRadioButton("Tarjeta de crédito");
        paypalRadio = new JRadioButton("PayPal");
        buttonGroup.add(creditCardRadio);
        buttonGroup.add(paypalRadio);
        paymentPanel.add(paymentLabel);
        paymentPanel.add(creditCardRadio);
        paymentPanel.add(paypalRadio);

        // Botón de confirmación
        JPanel confirmPanel = new JPanel();
        JButton progressBar = new JButton("OK");
        progressBar.addActionListener(e -> onSubmit(flight));
        confirmPanel.add(progressBar);

        panel.add(flightDetailsPanel);
        panel.add(userNamePanel);
        panel.add(emailPanel);
        panel.add(passportPanel);
        panel.add(phonePanel);
        panel.add(paymentPanel);
        panel.add(confirmPanel);

        add(scrollPane);

        this.setVisible(true);
    }

    private static JPanel getDetailsPanel(Flight v) {
        JPanel flightDetailsPanel = new JPanel();
        JTextArea flightDetailsField = new JTextArea();
        flightDetailsField.setText(
                "Vuelo " + v.getCodigo() +
                        ": \n- Origen: " + v.getOrigen().getName() +
                        "\n- Destino: " + v.getDestino().getName() +
                        "\n- Fecha despegue: " + v.getFechaDespegue() +
                        "\n- Fecha aterrizaje: " + v.getFechaAterrizaje()
        );
        flightDetailsField.setOpaque(false);
        flightDetailsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        flightDetailsField.setEditable(false);
        flightDetailsPanel.add(flightDetailsField);
        return flightDetailsPanel;
    }

    private void resetFieldFormat() {
        nameField.setBackground(null);
        emailField.setBackground(null);
        passportField.setBackground(null);
        phoneField.setBackground(null);
    }

    private void onSubmit(Flight flight) {
        resetFieldFormat();

        String error = "";
        if (nameField.getText().isEmpty()) {
            error = "El nombre está vacío";
            nameField.setBackground(Color.red);
        } else if (emailField.getText().isEmpty()) {
            error = "El correo electrónico está vacío";
            emailField.setBackground(Color.red);
        } else if (passportField.getText().isEmpty()) {
            error = "El DNI/Pasaporte está vacío";
            passportField.setBackground(Color.red);
        } else if (phoneField.getText().isEmpty()) {
            error = "No se ha especificado número de teléfono";
            phoneField.setBackground(Color.red);
        }

        if (!error.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    error + ", por favor, rellenelo antes de continuar",
                    "¡Campos vacíos!",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        if (!emailField.getText().matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            error = "Correo electrónico inválido";
            emailField.setBackground(Color.RED);
        } else if (!paypalRadio.isSelected() && !creditCardRadio.isSelected()) {
            error = "No se ha seleccionado método de pago";
        }

        if (!error.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    error + ", por favor, revise los datos antes de continuar",
                    "¡Campos inválidos!",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }
        boolean creditCard = creditCardRadio.isSelected();
        String paymentMethod = creditCard ? "tarjeta de crédito" : "cuenta de PayPal";
        String payment = JOptionPane.showInputDialog(this, "Introduce tu " + paymentMethod + ".", "Datos de pago", JOptionPane.QUESTION_MESSAGE);
        if (payment.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "El valor de la " + paymentMethod + " no puede estar vacío",
                    "¡Campos inválidos!",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }
        JOptionPane.showMessageDialog(this, "¡Compra realizada!", "Compra realizada correctamente", JOptionPane.INFORMATION_MESSAGE);
        Booking booking = new Booking(UserDAO.getLoggedInUser(), flight, 0);
        BookingDAO.getBookingDAO().save(booking);
        dispose();
    }
}
