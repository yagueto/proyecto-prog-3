package gui;

import db.DBManager;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class SignInWindow extends AbstractWindow{

	private static final long serialVersionUID = 8522535521661703102L;
	private final JTextField txtNombreUsuario;
	private final JPasswordField  txtContraseniaUsuario;
	private final JTextField txtDni;
	private final JTextField txtApellido;
	private final JTextField txtMail;

	public SignInWindow() {
        JPanel pNorte = new JPanel();
        JPanel pSur = new JPanel();
        JPanel pCentro = new JPanel(new GridLayout(3,0));
        
        JPanel pOeste = new JPanel();
        JPanel pEste = new JPanel();
        
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
        JLabel lblmail=  new JLabel("Introduce tu MAIL:");
        JLabel lblContraseniaUsuario = new JLabel("| Introduce tu contraseña: ");
        JLabel lblDni = new JLabel("Introduce tu DNI: ");
        JLabel lblApellido = new JLabel("Introduce tu Apellido: ");

        txtNombreUsuario = new JTextField(10);
        txtContraseniaUsuario = new JPasswordField(10);
        txtDni = new JTextField(10);
        txtApellido = new JTextField(10);
        txtMail = new JTextField(10);


        pSur.add(btnRegistrarse);
        pSur.add(btnVolver);

        pNorte.add(lblTitulo);

        pCentro.add(lblNombreUsuario);
        pCentro.add(txtNombreUsuario);
        pCentro.add(lblApellido);
        pCentro.add(txtApellido);
        pCentro.add(lblDni);
        pCentro.add(txtDni);
        pCentro.add(lblmail);
        pCentro.add(txtMail);
        pCentro.add(lblContraseniaUsuario);
        pCentro.add(txtContraseniaUsuario);


        btnVolver.addActionListener(e -> exit());

        /*
        ActionListener l = new ActionListener() {

        	
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = txtNombreUsuario.getText();
                @SuppressWarnings("deprecation") String contrasenia = txtContraseniaUsuario.getText();
                if (usuario.equals("USUARIO1") && contrasenia.equals("USUARIO1")) {

                    JOptionPane.showMessageDialog(null, "Has iniciado sesión correctamente como usuario");
                    SwingUtilities.invokeLater(UserWindow::new);
                    dispose();
                } else if (usuario.equals("ADMIN2") && contrasenia.equals("ADMIN2")) {
                    
                    JOptionPane.showMessageDialog(null, "Has iniciado sesión correctamente como administrador");
                    SwingUtilities.invokeLater(AdminWindow::new);
                    dispose();
                } else if (usuario.equals("EMPLEADO3") && contrasenia.equals("EMPLEADO3")) {

                    JOptionPane.showMessageDialog(null, "Has iniciado sesión correctamente como empleado");
                    SwingUtilities.invokeLater(EmployeeWindow::new);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Nombre de usuario y/o contraseña incorrectos", "ERROR", JOptionPane.ERROR_MESSAGE);
                    vaciarCampos();
                }
            }
            
            public void vaciarCampos() {
                txtNombreUsuario.setText("");
                txtContraseniaUsuario.setText("");
            }
        };
        */
        btnRegistrarse.addActionListener((e)->{
			int dni  = Integer.parseInt(txtDni.getText());
			String nombre = txtNombreUsuario.getText();
			String apellido = txtApellido.getText();
			String mail = txtMail.getText();
            String contrasenia = Arrays.toString(txtContraseniaUsuario.getPassword());
            if (DBManager.getDBManager().existeUsuario(dni)) {
				JOptionPane.showMessageDialog(null, "Lo sentimos, ese usuario ya existe", "Error de registro", JOptionPane.ERROR_MESSAGE);
			}else {
                DBManager.getDBManager().insertarUsuario(dni, nombre, apellido, mail, contrasenia);
				JOptionPane.showMessageDialog(null, "Registro correcto", "Registro", JOptionPane.INFORMATION_MESSAGE);
				
				
			}
		});

        setVisible(true);
        
    }

    
        
    }


