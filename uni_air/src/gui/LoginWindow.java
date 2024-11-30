package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginWindow extends AbstractWindow {
    private static final long serialVersionUID = -1392377673421616906L;

    private final JTextField txtNombreUsuario;

    public LoginWindow() {
        JPanel pNorte = new JPanel();
        JPanel pSur = new JPanel();
        JPanel pCentro = new JPanel();
        JPanel pOeste = new JPanel();
        JPanel pEste = new JPanel();

        getContentPane().add(pNorte, BorderLayout.NORTH);
        getContentPane().add(pEste, BorderLayout.EAST);
        getContentPane().add(pOeste, BorderLayout.WEST);
        getContentPane().add(pSur, BorderLayout.SOUTH);
        getContentPane().add(pCentro, BorderLayout.CENTER);

        JButton btnIniciarSesion = new JButton("INICIAR SESIÓN");
        JButton btnCerrarSesion = new JButton("CERRAR SESIÓN");
        JButton btnRegistrarse= new JButton("REGISTRARSE");
        getRootPane().setDefaultButton(btnIniciarSesion);

        JLabel lblTitulo = new JLabel("¡Bienvenido!");
        JLabel lblNombreUsuario = new JLabel("Introduce tu nombre: ");
        JLabel lblContraseniaUsuario = new JLabel("Introduce tu contraseña: ");

        txtNombreUsuario = new JTextField(10);
        JPasswordField txtContraseniaUsuario = new JPasswordField(10);

        pSur.add(btnIniciarSesion);
        pSur.add(btnCerrarSesion);
        pSur.add(btnRegistrarse);

        pNorte.add(lblTitulo);

        pCentro.add(lblNombreUsuario);
        pCentro.add(txtNombreUsuario);
        pCentro.add(lblContraseniaUsuario);
        pCentro.add(txtContraseniaUsuario);


        btnCerrarSesion.addActionListener(e -> exit());

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
        btnIniciarSesion.addActionListener(l);
        btnRegistrarse.addActionListener(e -> SwingUtilities.invokeLater(SignInWindow::new));

        setVisible(true);
    }
}
