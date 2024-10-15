package gui;

import javax.swing.JPanel;

public class EmployeeWindow extends AbstractWindow {
	static final long serialVersionUID = 4922652717777952972L;
	
	 
	public EmployeeWindow() {
		super();
		setTitle("Empleado");
		
		JPanel panel = new JPanel();
		panel.setBorder(createBorder("Empleado"));
		this.add(panel);
		
		setVisible(true);
		}

	
	
}
