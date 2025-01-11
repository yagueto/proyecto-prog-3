package testDAO;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import db.DBManager;
import db.UserDAO;
import domain.Customer;
import domain.Employee;
import domain.User;
import domain.UserType;

public class UserDAOTest {
	
	private final UserDAO userDAO = UserDAO.getUserDAO();

	// EN LOS DOS EJEMPLOS PARA LOS DOS TIPOS DE USUARIO, INSERTANDO Y OBTENIENDO USUARIOS DE LA DB FUNCIONA TODO CORRECTAMENENTE	
	
    @Before
    public void setUp() {
    	userDAO.clearTable();
    }
	
	
    @Test
    public void testSaveCustomer() {
        try {

            Customer customer = new Customer(12345, "john", "Doe", "johndoe@example.com", "password123", LocalDate.of(1990, 1, 1));
            userDAO.save(customer);

            User retrievedUser = userDAO.get(customer.getDni());

            assertNotNull("El usuario debería haberse guardado en la base de datos.", retrievedUser);

            assertEquals("El DNI debería coincidir.", customer.getDni(), retrievedUser.getDni());
            assertEquals("El nombre debería coincidir.", customer.getName(), retrievedUser.getName());
            assertEquals("El apellido debería coincidir.", customer.getSurname(), retrievedUser.getSurname());
            assertEquals("El email debería coincidir.", customer.getMail(), retrievedUser.getMail());
            
            assertTrue("El usuario debería ser del tipo CUSTOMER.", retrievedUser instanceof Customer);
        } catch (Exception e) {
            fail("Falló al guardar o recuperar al usuario: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void testGetEmployee() {
        try {
            User employee = new Employee(54321, "Jane", "Smith", "janesmith@example.com", UserType.EMPLOYEE);
            userDAO.save(employee);

            User retrievedUser = userDAO.get(employee.getDni());

            assertNotNull("El empleado debería haberse recuperado de la base de datos.", retrievedUser);
            assertEquals("El DNI debería coincidir.", employee.getDni(), retrievedUser.getDni());
            assertEquals("El nombre debería coincidir.", employee.getName(), retrievedUser.getName());
            
            assertTrue("El usuario debería ser del tipo EMPLOYEE.", retrievedUser instanceof Employee);
        } catch (Exception e) {
            fail("Falló al recuperar al usuario: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
