package db;

import domain.Customer;
import domain.Employee;
import domain.User;
import domain.UserType;

import java.security.InvalidParameterException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements Dao<User> {
    private static User currentUser; // Current logged in User

    private static UserDAO userDAO;
    private final PreparedStatement getUserByIdStatement;
    private final PreparedStatement getAllUsersStatement;
    private final PreparedStatement saveUserStatement;
    private final PreparedStatement updateUserStatement;
    private final PreparedStatement deleteUserStatement;
    private final PreparedStatement getUserByMailStatement;
    private final PreparedStatement clearTableStatement;

    protected Connection conn = null;

    private UserDAO() {
        Connection conn = DBManager.getDBManager().conn;
        try {
            this.clearTableStatement = conn.prepareStatement("DELETE FROM USER");
            this.getUserByIdStatement = conn.prepareStatement("SELECT DNI, NAME, SURNAME, EMAIL, BIRTHDATE, PASSWORD, USER_TYPE FROM USER WHERE DNI=?");
            this.getAllUsersStatement = conn.prepareStatement("SELECT DNI, NAME, SURNAME, EMAIL, BIRTHDATE, USER_TYPE, PASSWORD FROM USER");
            this.saveUserStatement = conn.prepareStatement("INSERT INTO USER (DNI, NAME, SURNAME, EMAIL, BIRTHDATE, USER_TYPE, PASSWORD) VALUES (?, ?, ?, ?, ?, ?, ?)");
            this.updateUserStatement = conn.prepareStatement("UPDATE USER SET NAME=?, SURNAME=?, EMAIL=?, BIRTHDATE=?, USER_TYPE=?, PASSWORD=? WHERE DNI=?");
            this.deleteUserStatement = conn.prepareStatement("DELETE FROM USER WHERE DNI=?");
            this.getUserByMailStatement = conn.prepareStatement("SELECT PASSWORD FROM USER WHERE EMAIL=?");
        } catch (SQLException e) {
            throw new DBException("Error inesperado creando statements, posible error en la base de datos", e); // Should never happen
        }
    }

    public static UserDAO getUserDAO() {
        if (userDAO == null) {
            userDAO = new UserDAO();
        }
        return userDAO;
    }

    public static User getLoggedInUser() {
        return currentUser;
    }

    public static void setLoggedInUser(User user) {
        currentUser = user;
    }

    private static User handleUserType(UserType userType, ResultSet rs) throws SQLException, InvalidParameterException {
        switch (userType) {
            case ADMIN, EMPLOYEE -> {
                return new Employee(rs.getInt("DNI"), rs.getString("NAME"), rs.getString("SURNAME"), rs.getString("EMAIL"), userType);
            }
            case CUSTOMER -> {
                return new Customer(rs.getInt("DNI"), rs.getString("NAME"), rs.getString("SURNAME"), rs.getString("EMAIL"), rs.getString("PASSWORD"), LocalDate.parse(rs.getString("BIRTHDATE")));
            }
            default -> throw new InvalidParameterException("Tipo de usuario en la BBDD inválido: " + userType);
        }
    }

    public static boolean comprobarPassword(String mail, String password) throws DBException {
        UserDAO userDAO = UserDAO.getUserDAO();
        // recibe  password  y  tengo que hashearla

        try {
            // Asignar el correo al marcador de posición en la consulta preparada
            userDAO.getUserByMailStatement.setString(1, mail);

            String hashedInputpassword = hashPassword(password);
            // Ejecutar la consulta
            try (ResultSet rs = userDAO.getUserByMailStatement.executeQuery()) {
                // Verificar si el correo existe
                if (rs.next()) {
                    // Recuperar la contraseña almacenada
                    String bdPassword = rs.getString("PASSWORD");

                    // Comparar la contraseña proporcionada con la almacenada
                    if (bdPassword.equals(hashedInputpassword)) {
                        return true; // La contraseña es correcta
                    } else {
                        return false; // La contraseña no es correcta
                    }


                } else {
                    return false; // El correo no existe
                }
            }
        } catch (SQLException | NoSuchAlgorithmException e) {
            // Manejar cualquier excepción SQL
            throw new DBException("Error al validar el usuario", e);
        }
    }

    public static String hashPassword(String password) throws NoSuchAlgorithmException {

        MessageDigest algoritmo = MessageDigest.getInstance("SHA-256");
        byte[] hash = algoritmo.digest(password.getBytes());
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hexa = Integer.toHexString(0xff & b);
            if (hexa.length() == 1) hexString.append('0');
            hexString.append(hexa);
        }
        return hexString.toString();
    }

    @Override
    public User get(Object param) {
        if (!(param instanceof Integer dni)) {
            throw new InvalidParameterException("Parámetro inválido (se esperaba int).");
        }
        try {
            getUserByIdStatement.setInt(1, dni);
            ResultSet rs = getUserByIdStatement.executeQuery();
            if (rs.isBeforeFirst() && rs.next()) {
                UserType userType;
                int user_code = rs.getInt("USER_TYPE");
                if (user_code <= UserType.values().length) {
                    userType = UserType.values()[user_code];
                } else {
                    throw new DBException("Tipo de usuario inválido encontrado en la base de datos: " + user_code);
                }
                return handleUserType(userType, rs);
            }
        } catch (SQLException | InvalidParameterException e) {
            throw new DBException(e);
        }
        return null;
    }

    @Override
    public List<User> getAll() {
        ArrayList<User> users = new ArrayList<>();
        try {
            ResultSet rs = getAllUsersStatement.executeQuery();
            while (rs.next()) {
                UserType userType;
                int user_type = rs.getInt("USER_TYPE");
                if (user_type <= UserType.values().length) {
                    userType = UserType.values()[user_type];
                } else {
                    throw new RuntimeException("Tipo de usuario inválido (" + user_type + ").");
                }
                users.add(handleUserType(userType, rs));
            }
        } catch (SQLException | InvalidParameterException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    //comprobar si mail esta en la base de datos
    public boolean checkMail(String mail) {
        try {
            getUserByMailStatement.setString(1, mail);
            ResultSet rs = getUserByMailStatement.executeQuery();
            return rs.isBeforeFirst();
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }
//    public void save(Customer customer) {
//        String query = "INSERT INTO USER (DNI, NAME, SURNAME, EMAIL, PASSWORD) VALUES (?, ?, ?, ?, ?)";
//        try (PreparedStatement stmt = conn.prepareStatement(query)) {
//            stmt.setInt(1, customer.getDni());
//            stmt.setString(2, customer.getName());
//            stmt.setString(3, customer.getSurname());
//            stmt.setString(4, customer.getMail());
//            stmt.setString(5, customer.getPassword());
//            stmt.executeUpdate();
//        } catch (SQLException e) {
//            throw new DBException(e);
//        }
//    }

    @Override

    public void save(User user) {
        int user_type_code;
        if (user instanceof Customer) {
            user_type_code = UserType.CUSTOMER.ordinal();
        } else {
            user_type_code = ((Employee) user).getType().ordinal();
        }

        try {
            saveUserStatement.setInt(1, user.getDni());
            saveUserStatement.setString(2, user.getName());
            saveUserStatement.setString(3, user.getSurname());
            saveUserStatement.setString(4, user.getMail());
            if (user instanceof Customer) {
                saveUserStatement.setString(5, ((Customer) user).getBirthdate().toString()); // birthdate
            } else {
                saveUserStatement.setNull(5, Types.VARCHAR);
            }
            saveUserStatement.setInt(6, user_type_code);
            saveUserStatement.setString(7, user.getPassword());

            saveUserStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    // Actualizar datos de usuario: cambios de mail, contraseña... a implemetar más adelante
    @Override
    public void update(User user) {
        int user_type_code;
        if (user instanceof Customer) {
            user_type_code = UserType.CUSTOMER.ordinal();
        } else {
            user_type_code = ((Employee) user).getType().ordinal();
        }

        try {
            updateUserStatement.setString(1, user.getName());
            updateUserStatement.setString(2, user.getSurname());
            updateUserStatement.setString(3, user.getMail());
            if (user instanceof Customer) {
                updateUserStatement.setString(4, ((Customer) user).getBirthdate().toString()); // birthdate
            } else {
                updateUserStatement.setNull(4, Types.VARCHAR);
            }
            updateUserStatement.setInt(5, user_type_code);
            updateUserStatement.setString(6, user.getPassword());
            updateUserStatement.setInt(7, user.getDni());

            int r = updateUserStatement.executeUpdate();
            System.out.println(r);
        } catch (SQLException e) {
            throw new DBException(e);
        }

    }

    // Borrar User, tarea de administrador o usuario se da de baja
    @Override
    public void delete(User user) {
        try {
            deleteUserStatement.setInt(1, user.getDni());
            deleteUserStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public void clearTable() {
        try {
            clearTableStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

}
