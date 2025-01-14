package gui;

import db.DBException;
import db.UserDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class LoginWindow extends AbstractWindow {
    private static final long serialVersionUID = -1392377673421616906L;

    private final JTextField txtMail;
    private final JPasswordField txtPassword;

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
        JButton btnRegistrarse = new JButton("REGISTRARSE");
        getRootPane().setDefaultButton(btnIniciarSesion);

        JLabel lblTitulo = new JLabel("¡Bienvenido!");
        JLabel lblMail = new JLabel("Introduce tu mail: ");
        JLabel lblPassword = new JLabel("Introduce tu contraseña: ");

        txtMail = new JTextField(10);
        txtPassword = new JPasswordField(10);

        pSur.add(btnIniciarSesion);
        pSur.add(btnCerrarSesion);
        pSur.add(btnRegistrarse);

        pNorte.add(lblTitulo);

        pCentro.add(lblMail);
        pCentro.add(txtMail);
        pCentro.add(lblPassword);
        pCentro.add(txtPassword);


        btnCerrarSesion.addActionListener(e -> exit());

        ActionListener l = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = txtMail.getText();
                String password = new String(txtPassword.getPassword());
                //comprueba si el usuario y la contraseña estan en la base de datos
                if (usuario.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Introduce un nombre de usuario y una contraseña", "ERROR",
                            JOptionPane.ERROR_MESSAGE);
                } else if (usuario.equals("USUARIO1") && password.equals("USUARIO1")) {

                    JOptionPane.showMessageDialog(null, "Has iniciado sesión correctamente como usuario");
                    SwingUtilities.invokeLater(UserWindow::new);
                    dispose();
                } else if (usuario.equals("ADMIN2") && password.equals("ADMIN2")) {

                    JOptionPane.showMessageDialog(null, "Has iniciado sesión correctamente como administrador");
                    SwingUtilities.invokeLater(AdminWindow::new);
                    dispose();
                } else if (usuario.equals("EMPLEADO3") && password.equals("EMPLEADO3")) {

                    JOptionPane.showMessageDialog(null, "Has iniciado sesión correctamente como empleado");
                    SwingUtilities.invokeLater(EmployeeWindow::new);
                    dispose();
                } else {
                    try {

                        if (UserDAO.comprobarPassword(usuario, password) == true) {
                            JOptionPane.showMessageDialog(null, "Has iniciado sesión correctamente como usuario");
                            SwingUtilities.invokeLater(UserWindow::new);
                            dispose();

                        } else if (UserDAO.comprobarPassword(usuario, password) == false) {
                            JOptionPane.showMessageDialog(null, "Nombre de usuario y/o contraseña incorrectos", "ERROR", JOptionPane.ERROR_MESSAGE);
                            vaciarPassword();
                        }
                    } catch (DBException | HeadlessException ex) {
                        JOptionPane.showMessageDialog(null, "Error al validar el usuario: " + ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }


            public void vaciarPassword() {

                txtPassword.setText("");
            }
        };
        btnIniciarSesion.addActionListener(l);
        btnRegistrarse.addActionListener(e -> {
            new SignInWindow();
            dispose();
        });


        setVisible(true);
    }
}