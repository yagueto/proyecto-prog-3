package db;

import domain.Customer;
import domain.Employee;
import domain.User;
import domain.UserType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements Dao<User> {
    private static UserDAO userDAO;
    protected Connection conn = null;

    private final PreparedStatement getUserByIdStatement;
    private final PreparedStatement getAllUsersStatement;
    private final PreparedStatement saveUserStatement;
    private final PreparedStatement updateUserStatement;
    private final PreparedStatement deleteUserStatement;

    private UserDAO() {
        Connection conn = DBManager.getDBManager().conn;
        try {
            this.getUserByIdStatement = conn.prepareStatement("SELECT NAME FROM USER WHERE DNI=?");
            this.getAllUsersStatement = conn.prepareStatement("SELECT DNI, NAME, SURNAME, EMAIL, BIRTHDATE, USER_TYPE FROM USER");
            this.saveUserStatement = conn.prepareStatement("INSERT INTO USER (DNI, NAME, SURNAME, EMAIL, BIRTHDATE, " +
                      "USER_TYPE) VALUES (?, ?, ?, ?, ?, ?)");
            this.updateUserStatement = conn.prepareStatement("UPDATE USER SET NAME=?, BIRTHDATE=?, EMAIL=?, " +
                    "SURNAME=?, USER_TYPE=? WHERE DNI=?");
            this.deleteUserStatement = conn.prepareStatement("DELETE FROM USER WHERE DNI=?");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static UserDAO getUserDAO() {
        if (userDAO == null) {
            userDAO = new UserDAO();
        }
        return userDAO;
    }


    @Override
    public User get(Object param) {
        if (!(param instanceof String dni)) {
            throw new RuntimeException("Parámetro inváido (se esperaba string).");
        }
        try {
            getUserByIdStatement.setString(1, dni);
            ResultSet rs = getUserByIdStatement.executeQuery();
            if (rs.isBeforeFirst()) {
                UserType userType;
                int user_type = rs.getInt("USER_TYPE");
                if (user_type <= UserType.values().length) {
                    userType = UserType.values()[user_type];
                } else {
                    throw new RuntimeException("Tipo de usuario inválido (" + user_type + ").");
                }
                switch (userType) {
                    case UserType.ADMIN, UserType.EMPLOYEE -> {
                        return new Employee(rs.getInt("DNI"), rs.getString("NAME"), rs.getString("SURNAME"), rs.getString("EMAIL"), userType);
                    }
                    case UserType.CUSTOMER -> {
                        return new Customer(rs.getInt("DNI"), rs.getString("NAME"), rs.getString("SURNAME"), rs.getString("EMAIL"), rs.getString("PASSWORD"), LocalDate.parse(rs.getString("BIRTHDATE")));
                    	//return new Customer(rs.getInt("DNI"), rs.getString("NAME"), rs.getString("SURNAME"), rs.getString("EMAIL"), LocalDate.parse(rs.getString("BIRTHDATE")));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
                switch (userType) {
                    case UserType.ADMIN, UserType.EMPLOYEE -> users.add(new Employee(rs.getInt("DNI"), rs.getString("NAME"), rs.getString("SURNAME"), rs.getString("EMAIL"), userType));
                    case UserType.CUSTOMER -> users.add(new Customer(rs.getInt("DNI"), rs.getString("NAME"), rs.getString("SURNAME"), rs.getString("EMAIL"), rs.getString("PASSWORD"), LocalDate.parse(rs.getString("BIRTHDATE"))));
                    //case UserType.CUSTOMER -> users.add(new Customer(rs.getInt("DNI"), rs.getString("NAME"), rs.getString("SURNAME"), rs.getString("EMAIL"), rs.getPassword("PASSWORD"),LocalDate.parse(rs.getString("BIRTHDATE"))));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    

    public boolean existeUsuario(int dni) {
		boolean existe = false;
		String sql = "SELECT COUNT(*) FROM Usuario WHERE id = ?";
		try {
            PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, dni);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) { 
				existe = true;
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return existe;
	}
    @Override
    public void save(User user) {
    	 try {
             saveUserStatement.setInt(1, user.getDni());
             saveUserStatement.setString(2, user.getName());
             saveUserStatement.setString(3, user.getSurname());
             saveUserStatement.setString(4, user.getMail());
             saveUserStatement.setString(5, user.getPassword());
           
             saveUserStatement.executeQuery();
         } catch (SQLException e) {
             throw new RuntimeException(e);
         }
    }
    public void save(Customer customer) {
        String query = "INSERT INTO usuarios (dni, nombre, apellido, mail, contrasenia) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, customer.getDni());
            stmt.setString(2, customer.getName());
            stmt.setString(3, customer.getSurname());
            stmt.setString(4, customer.getMail());
            stmt.setString(5, customer.getPassword());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // Actualizar datos de usuario: cambios de mail, contraseña... a implemetar más adelante
    @Override
    public void update(User user) {
    	try {
			updateUserStatement.setString(2, user.getName());
			updateUserStatement.setString(3, user.getSurname());
			updateUserStatement.setString(4, user.getMail());
			updateUserStatement.setString(5, user.getPassword());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
    	
    }
    // Borrar User, tarea de administrador o usuario se da de baja
    @Override
    public void delete(User user) {
    	try {
            deleteUserStatement.setInt(1, user.getDni());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
