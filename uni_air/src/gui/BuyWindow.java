package gui;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import main.Vuelo;

public class BuyWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5205219596554060756L;
	
	private final JTextField nameField;
	private final JTextField passportField;
	private final JTextField phoneField;
	
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
		JPanel flightDetailsPanel = new JPanel(); // TODO: mejorar display
		JTextArea flightDetailsField = new JTextArea();
		flightDetailsField.setText(v.toString()); // TODO: mejorar formato
		flightDetailsField.setLineWrap(true);
		flightDetailsField.setEditable(false);
		flightDetailsPanel.add(flightDetailsField);
		
		// Nombre completo:
		JPanel userNamePanel = new JPanel();
		JLabel fullNameLabel = new JLabel("Nombre completo:");
		nameField = new JTextField(15);
		userNamePanel.add(fullNameLabel);
		userNamePanel.add(nameField);

		// Email
		JPanel emailPanel = new JPanel();
		JLabel emailLabel = new JLabel("Correo electrónico:");
		JTextField emailField = new JTextField(15);
		emailPanel.add(emailLabel);
		emailPanel.add(emailField);

		// DNI / Pasaporte:
		JPanel passportPanel = new JPanel();
		JRadioButton radioDni = new JRadioButton("DNI");
		JRadioButton radioPassport = new JRadioButton("Pasaporte");
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
		JRadioButton creditCardRadio = new JRadioButton("Tarjeta de crédito");
		JRadioButton paypalRadio = new JRadioButton("PayPal");
		paymentPanel.add(paymentLabel);
		paymentPanel.add(creditCardRadio);
		paymentPanel.add(paypalRadio);
		
		// Botón de confirmación
		JPanel confirmPanel = new JPanel();
		JButton progressBar = new JButton("OK");
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
}
