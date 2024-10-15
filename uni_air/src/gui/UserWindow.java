package gui;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

public class UserWindow extends AbstractWindow {
	private static final long serialVersionUID = 7345092960587394070L;
<<<<<<< HEAD
	
=======


>>>>>>> branch 'main' of https://github.com/yagueto/proyecto-prog-3
	public UserWindow() {
		super();
		JTabbedPane tabPane = new JTabbedPane();
		
		JPanel panelHistorial = new JPanel();
		tabPane.addTab("Mis Vuelos", panelHistorial);
		
		JTable table = new JTable();
		
		panelHistorial.add(table);
		add(tabPane);
		
		this.setVisible(true);
	}
	
}