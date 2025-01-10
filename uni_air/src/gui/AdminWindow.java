package gui;

import db.AirlineDAO;
import db.AirportDAO;
import db.FlightDAO;
import db.UserDAO;
import domain.*;
import domain.models.FlightModel;
import domain.models.FlightModel.TipoVentana;
import domain.models.UserModel;
import gui.misc.CellButtonRendererEditor;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class AdminWindow extends AbstractWindow {
    private static final long serialVersionUID = 8885137220929508082L;

    public AdminWindow() {
        super();

        JPanel panel = new JPanel(new BorderLayout());
        JPanel panelBotones = new JPanel();
        panel.setBorder(createBorder("Admin"));

        JButton btnAnadirVuelo = new JButton("Añadir vuelo");
        JButton btnGestionUsuarios = new JButton("Gestionar usuarios");
        FlightModel vuelos = new FlightModel(FlightDAO.getFlightDAO().getAll(), TipoVentana.ADMIN);

        JTable tabla = new JTable(vuelos);
        JScrollPane scrollPane = new JScrollPane(tabla);

        panelBotones.add(btnGestionUsuarios, BorderLayout.EAST);
        panelBotones.add(btnAnadirVuelo, BorderLayout.WEST);

        this.add(panel);
        panel.add(panelBotones, BorderLayout.SOUTH);

        panel.add(scrollPane, BorderLayout.CENTER);

        btnAnadirVuelo.addActionListener((e) -> {

            String codigo = JOptionPane.showInputDialog("Introduce el codigo del nuevo vuelo");
            String origen = JOptionPane.showInputDialog("Introduce el aeropuerto origen");
            String destino = JOptionPane.showInputDialog("Introduce el aeropuerto destino");
            String aerolinea = JOptionPane.showInputDialog("Introduce la aerolinea");
            LocalDateTime salida = LocalDateTime.parse(JOptionPane.showInputDialog("Introduce la fecha y hora de salida. De la siguiente forma:'AÑO-MES-DIA-Thora:minuto'"));
            LocalDateTime llegada = LocalDateTime.parse(JOptionPane.showInputDialog("Introduce la fecha y hora de llegada. De la siguiente forma:'AÑO-MES-DIA-Thora:minuto'"));
            int maxPasajeros = Integer.parseInt(JOptionPane.showInputDialog("Introduce la capacidad del vuelo"));
            int precio = Integer.parseInt(JOptionPane.showInputDialog("Introduce el precio del billete"));

            Airport airport_origen = AirportDAO.getAirportDAO().get(origen);
            Airport airport_destino = AirportDAO.getAirportDAO().get(destino);
            Airline airline = AirlineDAO.getAirlineDAO().get(aerolinea);

            try {

                FlightDAO.getFlightDAO().save(new Flight(codigo, airport_origen, airport_destino, airline, salida, llegada, maxPasajeros, precio));
                JOptionPane.showMessageDialog(null, "Vuelo añadido correctamente");

            } catch (Exception e2) {

                e2.printStackTrace();
                System.err.println("Error al añadir el vuelo");

            }

        });

        btnGestionUsuarios.addActionListener(e -> new UserManagementWindow());
        SwingUtilities.invokeLater(() -> {
            vuelos.fireTableDataChanged();
        });

        setVisible(true);

    }

}

class UserManagementWindow extends JFrame {
    private final String[] modifyOptions = {"Eliminar", "Cambiar tipo usuario", "Cambiar correo", "Cambiar contraseña"};
    private final UserModel userModel;

    public UserManagementWindow() {
        this.setSize(640, 480);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setTitle("Check-In");

        userModel = new UserModel(UserDAO.getUserDAO().getAll());

        JTable userTable = new JTable(userModel);

        CellButtonRendererEditor cellButtonRendererEditor = new CellButtonRendererEditor("Gestionar", (i) -> {
            modifyUser(((UserModel) (userTable.getModel())).getUsers().get(i));
            userModel.getUsers().clear();
            userModel.getUsers().addAll(UserDAO.getUserDAO().getAll());
        });
        userTable.getColumnModel().getColumn(4).setCellRenderer((table, value, isSelected, hasFocus, row, column) -> {
            Color color = Color.WHITE;
            switch (((UserType) value)) {
                case CUSTOMER -> color = Color.GREEN;
                case EMPLOYEE -> color = Color.YELLOW;
                case ADMIN -> color = Color.RED;
            }
            JLabel label = new JLabel(((UserType) value).name());
            label.setBackground(color);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setOpaque(true);
            return label;
        });
        userTable.getColumnModel().getColumn(5).setCellEditor(cellButtonRendererEditor);
        userTable.getColumnModel().getColumn(5).setCellRenderer(cellButtonRendererEditor);

        JScrollPane scrollPane = new JScrollPane(userTable);

        this.add(scrollPane);
        this.setVisible(true);
    }

    private void modifyUser(User user) {
        String opcion = (String) JOptionPane.showInputDialog(null, "Selecciona una opción", "Modificando usuario", JOptionPane.QUESTION_MESSAGE, null, modifyOptions, "");
        if (opcion == null) {
            return;
        }
        switch (opcion) {
            case "Eliminar":
                UserDAO.getUserDAO().delete(user);
                break;
            case "Cambiar tipo usuario":
                UserType new_type = (UserType) JOptionPane.showInputDialog(null, "Nuevo tipo de usuario:", "Modificando usuario", JOptionPane.QUESTION_MESSAGE, null, UserType.values(), "");
                switch (new_type) {
                    case ADMIN, EMPLOYEE -> {
                        Employee admin = new Employee(user);
                        admin.setType(new_type);
                        UserDAO.getUserDAO().update(admin);
                    }
                    case CUSTOMER -> {
                        Customer customer = new Customer(user);
                        customer.setBirthdate(LocalDate.now());
                        UserDAO.getUserDAO().update(customer);
                    }
                }
                break;
            case "Cambiar correo":
                String email = (String) JOptionPane.showInputDialog(null, "Correo electrónico:", "Modificando usuario", JOptionPane.QUESTION_MESSAGE);
                user.setMail(email);
                UserDAO.getUserDAO().update(user);
                break;
            case "Cambiar contraseña":
                JPasswordField pf = new JPasswordField();
                int okCxl = JOptionPane.showConfirmDialog(null, pf, "Insertar nueva contraseña", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (okCxl == JOptionPane.OK_OPTION) {
                    String password = new String(pf.getPassword());
                    // TODO: gestionar la nueva contraseña
                }
                break;
        }
    }

}