package domain.models;

import domain.Employee;
import domain.User;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class UserModel extends AbstractTableModel {
    public final List<User> users;

    public UserModel(List<User> users) {
        super();
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }

    @Override
    public int getRowCount() {
        return users.size();
    }

    @Override
    public int getColumnCount() {
        return 6;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        User user = users.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> user.getDni();
            case 1 -> user.getName();
            case 2 -> user.getSurname();
            case 3 -> user.getMail();
            case 4 -> user instanceof Employee;
            case 5 -> null;
            default -> throw new IllegalArgumentException("Número de columna inválido");
        };
    }

    @Override
    public String getColumnName(int column) {
        return switch (column) {
            case 0 -> "DNI";
            case 1 -> "Nombre";
            case 2 -> "Apellido";
            case 3 -> "Email";
            case 4 -> "¿Empleado?";
            case 5 -> "Gestionar";
            default -> throw new IllegalArgumentException("Número de columna inválido");
        };
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 5;
    }
}
