package gui;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class EmployeeWindow extends AbstractWindow {
	static final long serialVersionUID = 4922652717777952972L;
	
	 
	public EmployeeWindow() {
		super();
		setTitle("Empleado");
		
		JPanel panel = new JPanel();
		panel.setBorder(CreateBorder("Empleado"));
		this.add(panel);
		
		setVisible(true);
		}

	
	
}
