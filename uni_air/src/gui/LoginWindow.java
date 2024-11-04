package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginWindow extends AbstractWindow {
    private static final long serialVersionUID = -1392377673421616906L;


    private final JButton btnIniciarSesion;
    private final JButton btnCerrarSesion;
    private final JPanel pNorte;
    private final JPanel pSur;
    private final JPanel pEste;
    private final JPanel pOeste;
    private final JPanel pCentro;
    private final JLabel lblTitulo;
    private final JLabel lblNombreUsuario;
    private final JLabel lblContraseniaUsuario;
    private final JTextField txtNombreUsuario;


    public LoginWindow() {
        super();

        pNorte = new JPanel();
        pSur = new JPanel();
        pCentro = new JPanel();
        pOeste = new JPanel();
        pEste = new JPanel();

        getContentPane().add(pNorte, BorderLayout.NORTH);
        getContentPane().add(pEste, BorderLayout.EAST);
        getContentPane().add(pOeste, BorderLayout.WEST);
        getContentPane().add(pSur, BorderLayout.SOUTH);
        getContentPane().add(pCentro, BorderLayout.CENTER);


        btnIniciarSesion = new JButton("INICIAR SESIÓN");
        btnCerrarSesion = new JButton("CERRAR SESIÓN");

        lblTitulo = new JLabel("¡Bienvenido!");
        lblNombreUsuario = new JLabel("Introduce tu nombre: ");
        lblContraseniaUsuario = new JLabel("Introduce tu contraseña: ");

        txtNombreUsuario = new JTextField(10);
        JPasswordField txtContraseniaUsuario = new JPasswordField(10);


        pSur.add(btnIniciarSesion);
        pSur.add(btnCerrarSesion);

        pNorte.add(lblTitulo);

        pCentro.add(lblNombreUsuario);
        pCentro.add(txtNombreUsuario);
        pCentro.add(lblContraseniaUsuario);
        pCentro.add(txtContraseniaUsuario);


        btnCerrarSesion.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        btnIniciarSesion.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = txtNombreUsuario.getText(); //Obtenemos el texto escrito en el cuadro de texto
                String contrasenia = txtContraseniaUsuario.getText(); //Obtenemos el texto escrito en el cuadro de texto
                if (usuario.equals("USUARIO1") && contrasenia.equals("USUARIO1")) {
                    //System.out.println("Has iniciado sesión correctamente");
                    JOptionPane.showMessageDialog(null, "Has iniciado sesión correctamente como usuario");
                    SwingUtilities.invokeLater(UserWindow::new);

                    vaciarCampos();
                } else if (usuario.equals("ADMIN2") && contrasenia.equals("ADMIN2")) {
                    //System.out.println("Has iniciado sesión correctamente");
                    JOptionPane.showMessageDialog(null, "Has iniciado sesión correctamente como administrador");
                    SwingUtilities.invokeLater(AdminWindow::new);
                    vaciarCampos();
                } else if (usuario.equals("EMPLEADO3") && contrasenia.equals("EMPLEADO3")) {

                    JOptionPane.showMessageDialog(null, "Has iniciado sesión correctamente como empleado");
                    SwingUtilities.invokeLater(EmployeeWindow::new);

                    vaciarCampos();
                } else {
                    JOptionPane.showMessageDialog(null, "Nombre de usuario y/o contraseña incorrectos", "ERROR", JOptionPane.ERROR_MESSAGE);
                    vaciarCampos();
                }
            }

            public void vaciarCampos() {
                txtNombreUsuario.setText("");
                txtContraseniaUsuario.setText("");
            }
        });


        setVisible(true);


    }


}
