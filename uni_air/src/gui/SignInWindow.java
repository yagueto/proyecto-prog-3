package gui;

import db.UserDAO;
import domain.Customer;
import domain.User;

import javax.swing.*;

import org.jdatepicker.JDatePicker;

import java.awt.*;

import java.util.Arrays;
import java.util.GregorianCalendar;


public class SignInWindow extends AbstractWindow{

	private static final long serialVersionUID = 8522535521661703102L;
	private final JTextField txtNombreUsuario;
	private final JPasswordField  txtContraseniaUsuario;
	private final JTextField txtDni;
	private final JTextField txtApellido;
	private final JTextField txtMail;
	
	
	
	
	

	public SignInWindow() {
		
		setTitle("Registro de Usuario");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
       
        
        JPanel pNorte = new JPanel();
        JPanel pSur = new JPanel();
        JPanel pCentro = new JPanel(new GridBagLayout());
        JPanel pOeste = new JPanel();
        JPanel pEste = new JPanel();
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(40, 10, 10, 10); // Margen entre componentes
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        
        getContentPane().add(pNorte, BorderLayout.NORTH);
        getContentPane().add(pEste, BorderLayout.EAST);
        getContentPane().add(pOeste, BorderLayout.WEST);
        getContentPane().add(pSur, BorderLayout.SOUTH);
        getContentPane().add(pCentro, BorderLayout.CENTER);

        JButton btnRegistrarse = new JButton("REGISTRARME");
        JButton btnVolver = new JButton("VOLVER");
        getRootPane().setDefaultButton(btnRegistrarse);

        JLabel lblTitulo = new JLabel("¡Bienvenido!");
        JLabel lblNombreUsuario = new JLabel("Introduce tu nombre: ");
        JLabel lblMail=  new JLabel("Introduce tu MAIL:");
        JLabel lblContraseniaUsuario = new JLabel("| Introduce tu contraseña: ");
        JLabel lblDni = new JLabel("Introduce tu DNI: ");
        JLabel lblApellido = new JLabel("Introduce tu Apellido: ");
        JLabel lblbirthDate = new JLabel("Introduce tu fecha de nacimiento: ");
        
        JDatePicker datePicker= new  JDatePicker();
        

        txtNombreUsuario = new JTextField(10);
        txtContraseniaUsuario = new JPasswordField(10);
        txtDni = new JTextField(10);
        txtApellido = new JTextField(10);
        txtMail = new JTextField(10);
       
        


        pSur.add(btnRegistrarse);
        pSur.add(btnVolver);

        pNorte.add(lblTitulo);

        
        
     // Primera fila: Nombre
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        pCentro.add(lblNombreUsuario, gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        pCentro.add(txtNombreUsuario, gbc);

        // Segunda fila: Apellido
        gbc.gridx = 0; gbc.gridy = 2;
        pCentro.add(lblApellido, gbc);
        gbc.gridx = 1; gbc.gridy = 2;
        pCentro.add(txtApellido, gbc);

        // Tercera fila: DNI
        gbc.gridx = 0; gbc.gridy = 3;
        pCentro.add(lblDni, gbc);
        gbc.gridx = 1; gbc.gridy = 3;
        pCentro.add(txtDni, gbc);

        // Cuarta fila: Mail
        gbc.gridx = 0; gbc.gridy = 4;
        pCentro.add(lblMail, gbc);
        gbc.gridx = 1; gbc.gridy = 4;
        pCentro.add(txtMail, gbc);

        
        gbc.gridx = 0; gbc.gridy = 5;
        pCentro.add(lblContraseniaUsuario, gbc);
        gbc.gridx = 1; gbc.gridy = 5;
        pCentro.add(txtContraseniaUsuario, gbc);
        
       
        gbc.gridx = 0; gbc.gridy = 6;
        pCentro.add(lblbirthDate, gbc);
        gbc.gridx = 1; gbc.gridy = 6;
        pCentro.add(datePicker, gbc);

        
        
        btnVolver.addActionListener(e -> { 
        	new LoginWindow();
            dispose();
        });

       
        
        // 
        
        btnRegistrarse.addActionListener((e)->{
        	//Que guarde el dni, nombre, apellido, mail y contraseña en la base de datos
        	int dni = Integer.parseInt(txtDni.getText());
            String nombre = txtNombreUsuario.getText();
            String apellido = txtApellido.getText();
            String mail = txtMail.getText();
            String contrasenia = Arrays.toString(txtContraseniaUsuario.getPassword());
            GregorianCalendar fechaNa= (GregorianCalendar) datePicker.getModel().getValue();
            
			User usu = new Customer(dni, nombre, apellido, mail, contrasenia,fechaNa.toZonedDateTime().toLocalDate() );
            if (UserDAO.getUserDAO().get(dni)!=null) {
                JOptionPane.showMessageDialog(null, "Lo sentimos, ese usuario ya existe", "Error de registro", JOptionPane.ERROR_MESSAGE);
            } else {
                UserDAO.getUserDAO().save(usu);
                new LoginWindow();
            	dispose();
                
                JOptionPane.showMessageDialog(null, "Registro correcto", "Registro", JOptionPane.INFORMATION_MESSAGE);
                
            }
        });
        
        

        setVisible(true);
        
    }

    
        
    }


