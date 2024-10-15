package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginWindow extends AbstractWindow {
	private static final long serialVersionUID = -1392377673421616906L;

	//Declaración de los componentes de la ventana
		private JButton btnIniciarSesion, btnCerrarSesion;
		private JPanel pNorte, pSur, pEste, pOeste, pCentro;
		private JLabel lblTitulo, lblNombreUsuario, lblContraseniaUsuario;
		private JTextField txtNombreUsuario;
		

	
	public LoginWindow() {
		super();
		// TODO Auto-generated constructor stub
	
	
	

		
	
		//La primera sentencia siempre es super();
		//Modificar el tamaño y posición inicial de la ventana
		
		//Instanciamos los paneles
		pNorte = new JPanel();
		pSur = new JPanel();
		pCentro = new JPanel();
		pOeste = new JPanel();
		pEste = new JPanel();
		//Modificamos el Layout del panel centro
		pCentro.setLayout(new BorderLayout(0, 0));
		
		
		
		//Añadimos los paneles al panel principal de la ventana
		getContentPane().add(pNorte, BorderLayout.NORTH);
		getContentPane().add(pEste, BorderLayout.EAST);
		getContentPane().add(pOeste, BorderLayout.WEST);
		getContentPane().add(pSur, BorderLayout.SOUTH);
		getContentPane().add(pCentro, BorderLayout.CENTER);
		
		//Instanciamos los componentes
		btnIniciarSesion = new JButton("INICIAR SESIÓN");
		btnCerrarSesion = new JButton("CERRAR SESIÓN");
		
		lblTitulo = new JLabel("¿Bienvenido!");
		lblNombreUsuario = new JLabel("Introduce tu nombre: ");
		lblContraseniaUsuario = new JLabel("Introduce tu contraseña: ");
		
		txtNombreUsuario = new JTextField(10);
		JPasswordField txtContraseniaUsuario = new JPasswordField(10);
		
		//Añadimos los componentes a la ventana
		pSur.add(btnIniciarSesion);
		pSur.add(btnCerrarSesion);
		
		pNorte.add(lblTitulo);
		
		//Para añadir en el panel central que es un GridLayout, tenemos que hacerlo
		//en el orden en el que queremos que aparezca, de izda a dcha y de arriba a abajo
		pCentro.add(lblNombreUsuario);
		pCentro.add(txtNombreUsuario);
		pCentro.add(lblContraseniaUsuario);
		pCentro.add(txtContraseniaUsuario);
		
		//Añadir los listeners a los componentes
		btnCerrarSesion.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// Aquí escribiremos el código que queremos que se ejecute cuando se haga click sobre el botón
				System.exit(0); //Cerrar la aplicación
			}
		});
		
		btnIniciarSesion.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//Vamos a comprobar que en el JTextField nombreUsuario ha escrito Marian
				// y que en el JTextField contraseniaUsuario ha escrito Newton
				String usuario = txtNombreUsuario.getText(); //Obtenemos el texto escrito en el cuadro de texto
				String contrasenia = txtContraseniaUsuario.getText(); //Obtenemos el texto escrito en el cuadro de texto
				if(usuario.equals("USUARIO 1") && contrasenia.equals("USUARIO1")) {
					//System.out.println("Has iniciado sesión correctamente");
					JOptionPane.showMessageDialog(null, "Has iniciado sesión correctamente");
					//Vaciamos los campos (cuadros de texto)
					/*txtNombreUsuario.setText("");
					txtContraseniaUsuario.setText("");*/
					vaciarCampos();
				}else {
					//System.out.println("Nombre de usuario y/o contraseña incorrectos");
					JOptionPane.showMessageDialog(null, "Nombre de usuario y/o contraseña incorrectos","ERROR",JOptionPane.ERROR_MESSAGE);
					/*txtNombreUsuario.setText("");
					txtContraseniaUsuario.setText("");*/
					vaciarCampos();
				}
			}

			public void vaciarCampos() {
				txtNombreUsuario.setText("");
				txtContraseniaUsuario.setText("");
			}
		});
		
		
		//La última sentencia siempre setVisible(true);
		setVisible(true);
		
		//Para no repetir código a la hora de vaciar los campos, vamos a crear un método que se encargue de ello
		
	}
	
	


}
