package gui;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import main.Vuelo;

public class BuyWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5205219596554060756L;
	
	private JLabel fullNameLabel;

	
	public BuyWindow(Vuelo v) {
		super();
		this.setSize(320, 240);
		this.setLocationRelativeTo(null);
		
		CardLayout cardLayout = new CardLayout();
		
		JPanel panel = new JPanel(cardLayout);
		JScrollPane scrollPane = new JScrollPane(panel);
		
		JPanel userDataPanel = new JPanel();
		fullNameLabel = new JLabel("Nombre completo:");
		userDataPanel.add(fullNameLabel);
		userDataPanel.add(new JTextField());

		// TODO
		JPanel testPanel = new JPanel();
		testPanel.add(new JProgressBar());
		testPanel.add(new JTextField());

		panel.add(userDataPanel);
		panel.add(testPanel);
		add(scrollPane);
		
		this.setVisible(true);
		
		SwingUtilities.invokeLater(() -> {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("nos fuimos");
			cardLayout.next(panel);
		});
	}
}
