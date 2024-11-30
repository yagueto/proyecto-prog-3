package domain;

public class Employee extends User {
    private UserType type;

    public Employee(int dni, String name, String surname, String mail, UserType type) {
        super(dni, name, surname, mail);
        this.type = type;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Employee [dni=" + this.getDni() + ", name=" + this.getName() + ", surname="
                + this.getSurname() + ", mail=" + this.getMail() + ", type=" + type + "]";
    }


}