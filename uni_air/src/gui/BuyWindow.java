package gui;

import main.Vuelo;

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

    private final JRadioButton radioDni;
    private final JRadioButton radioPassport;
    private final JRadioButton paypalRadio;
    private final JRadioButton creditCardRadio;

    public BuyWindow(Vuelo v) {
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
        JPanel flightDetailsPanel = getDetailsPanel(v);

        // Nombre completo:
        JPanel userNamePanel = new JPanel();
        JLabel fullNameLabel = new JLabel("Nombre completo:");
        nameField = new JTextField(15);
        userNamePanel.add(fullNameLabel);
        userNamePanel.add(nameField);

        // Email
        JPanel emailPanel = new JPanel();
        JLabel emailLabel = new JLabel("Correo electrónico:");
        emailField = new JTextField(15);
        emailPanel.add(emailLabel);
        emailPanel.add(emailField);

        // DNI / Pasaporte:
        JPanel passportPanel = new JPanel();
        radioDni = new JRadioButton("DNI");
        radioPassport = new JRadioButton("Pasaporte");
        passportPanel.add(radioDni);
        passportPanel.add(radioPassport);
        passportField = new JTextField(15);
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
        progressBar.addActionListener(e -> onSubmit());
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

    private static JPanel getDetailsPanel(Vuelo v) {
        JPanel flightDetailsPanel = new JPanel();
        JTextArea flightDetailsField = new JTextArea();
        flightDetailsField.setText(
                "Vuelo " + v.getCodigo() +
                        ": \n- Origen: " + v.getOrigen() +
                        "\n- Destino: " + v.getDestino() +
                        "\n- Fecha despegue: " + v.getFechaDespegue() +
                        "\n - Fecha aterrizaje: " + v.getFechaAterrizaje()
        );
        flightDetailsField.setOpaque(false);
        flightDetailsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        flightDetailsField.setEditable(false);
        flightDetailsPanel.add(flightDetailsField);
        return flightDetailsPanel;
    }

    private void onSubmit() {
        String error = "";
        if (nameField.getText().isEmpty()) {
            error = "El nombre está vacío";
        } else if (emailField.getText().isEmpty()) {
            error = "El correo electrónico está vacío";
        } else if (passportField.getText().isEmpty()) {
            error = "El DNI/Pasaporte está vacío";
        } else if (phoneField.getText().isEmpty()) {
            error = "No se ha especificado número de teléfono";
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

        // TODO: gestionar los datos
        dispose();
    }
}
