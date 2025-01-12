package gui;

import db.UserDAO;
import domain.Customer;
import domain.User;

import javax.swing.*;

import org.jdatepicker.JDatePicker;

import java.awt.*;
import java.security.NoSuchAlgorithmException;
import java.util.GregorianCalendar;


public class SignInWindow extends AbstractWindow{

	private static final long serialVersionUID = 8522535521661703102L;
	
	
	
	
	
	

	public SignInWindow() {
	 JTextField txtNombreUsuario;
	 JPasswordField  txtpasswordUsuario;
	 JTextField txtDni;
	 JTextField txtApellido;
	 JTextField txtMail;
	 
	 
	 
		setTitle("Registro de Usuario");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10)); 

        
        
        JPanel pSur = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JPanel pCentro = new JPanel();
        pCentro.setLayout(new BoxLayout(pCentro, BoxLayout.Y_AXIS));
        
        JPanel pEste = new JPanel();
        JPanel pOeste = new JPanel();
        
        setTitle("Registro de Usuario");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10)); 

        
        JLabel lblTitulo = new JLabel("¡Bienvenido!", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        add(lblTitulo, BorderLayout.NORTH);

        
        JLabel imagenI= new JLabel(new ImageIcon("/images/Imagen_avion_2.png"));
        JLabel imagenD = new JLabel(new ImageIcon("/images/Imagen_avion_2.png"));
        
        
        
        pEste.add(imagenD);
        pOeste.add(imagenI);
        
        
        
        JLabel lblNombreUsuario = new JLabel("Introduce tu nombre: ");
        JLabel lblApellido = new JLabel("Introduce tu apellido: ");
        JLabel lblDni = new JLabel("Introduce tu DNI: ");
        JLabel lblMail = new JLabel("Introduce tu MAIL: ");
        JLabel lblPassword = new JLabel("Introduce tu contraseña: ");
        JLabel lblBirthDate = new JLabel("Introduce tu fecha de nacimiento: ");

        txtNombreUsuario = new JTextField(15);
        txtApellido = new JTextField(15);
        txtDni = new JTextField(15);
        txtMail = new JTextField(15);
        txtpasswordUsuario = new JPasswordField(15);
        JDatePicker datePicker = new JDatePicker();

        pCentro.add(lblNombreUsuario);
        pCentro.add(txtNombreUsuario);
        pCentro.add(Box.createRigidArea(new Dimension(25,25)));
        pCentro.add(lblApellido);
        pCentro.add(txtApellido);
        pCentro.add(Box.createRigidArea(new Dimension(25,25)));
        pCentro.add(lblDni);
        pCentro.add(txtDni);
        pCentro.add(Box.createRigidArea(new Dimension(25,25)));
        pCentro.add(lblMail);
        pCentro.add(txtMail);
        pCentro.add(Box.createRigidArea(new Dimension(25,25)));
        pCentro.add(lblPassword);
        pCentro.add(txtpasswordUsuario);
        pCentro.add(Box.createRigidArea(new Dimension(25,25)));
        pCentro.add(lblBirthDate);
        pCentro.add(datePicker);

       
        
        pCentro.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblNombreUsuario.setAlignmentX(Component.CENTER_ALIGNMENT);
        txtNombreUsuario.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblApellido.setAlignmentX(Component.CENTER_ALIGNMENT);
        txtApellido.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblDni.setAlignmentX(Component.CENTER_ALIGNMENT);
        txtDni.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblMail.setAlignmentX(Component.CENTER_ALIGNMENT);
        txtMail.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblPassword.setAlignmentX(Component.CENTER_ALIGNMENT);
        txtpasswordUsuario.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblBirthDate.setAlignmentX(Component.CENTER_ALIGNMENT);
        datePicker.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        
        add(pCentro, BorderLayout.CENTER);

        
        // Panel izquierdo (OESTE) con imagen
        JLabel leftImage = new JLabel(new ImageIcon("/images/Imagen_avion_sobre_mar.png")); 
        
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        leftPanel.add(leftImage);
        add(leftPanel, BorderLayout.WEST);
        
        // Panel derecho (ESTE) con imagen
        JLabel rightImage = new JLabel(new ImageIcon("/images/Imagen_avion_sobre_mar.png")); 
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        rightPanel.add(rightImage);
        add(rightPanel, BorderLayout.EAST);
        
        
        JButton btnRegistrarse = new JButton("REGISTRARME");
        JButton btnVolver = new JButton("VOLVER");
        pSur.add(btnRegistrarse);
        pSur.add(btnVolver);
        add(pSur, BorderLayout.SOUTH);
     /* Primera fila: Nombre
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
        pCentro.add(lblpasswordUsuario, gbc);
        gbc.gridx = 1; gbc.gridy = 5;
        pCentro.add(txtpasswordUsuario, gbc);
        
       
        gbc.gridx = 0; gbc.gridy = 6;
        pCentro.add(lblbirthDate, gbc);
        gbc.gridx = 1; gbc.gridy = 6;
        pCentro.add(datePicker, gbc);

        */
        
        btnVolver.addActionListener(e -> { 
        	new LoginWindow();
            dispose();
        });

       
        
       
        btnRegistrarse.addActionListener((e)->{
        	//Que guarde el dni, nombre, apellido, mail y contraseña en la base de datos
        	int dni = Integer.parseInt(txtDni.getText());
            String nombre = txtNombreUsuario.getText();
            String apellido = txtApellido.getText();
            String mail = txtMail.getText();
            //char[] passwordChars = txtpasswordUsuario.getPassword();
            String password= new String(txtpasswordUsuario.getPassword());
            String hashPassword = "";
			try {
				hashPassword = UserDAO.hashPassword(password);
			} catch (NoSuchAlgorithmException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
           // String password = txtpasswordUsuario.getPassword().toString();
           /* try {
				String hashPassword= UserDAO.hashPassword(password);
			} catch (NoSuchAlgorithmException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            String hashPassword = hashPassword.toString();*/
            
            GregorianCalendar fechaNa= (GregorianCalendar) datePicker.getModel().getValue();
            
			User usu = new Customer(dni, nombre, apellido, mail, hashPassword ,fechaNa.toZonedDateTime().toLocalDate() );
            if (UserDAO.getUserDAO().get(dni)!=null) {
                JOptionPane.showMessageDialog(null, "Lo sentimos, ese usuario ya existe", "Error de registro", JOptionPane.ERROR_MESSAGE);
            } else {
                UserDAO.getUserDAO().save(usu);
                new LoginWindow();
            	dispose();
                
                JOptionPane.showMessageDialog(null, "Registro correcto", "Registro", JOptionPane.INFORMATION_MESSAGE);
                
            }
        });
        
       
        
        
        
        /*public static String hashPassword(String password) throws NoSuchAlgorithmException {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        }*/

        setVisible(true);
        
    }
	






    
        
    }


