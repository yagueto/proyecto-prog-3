package domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Customer extends User {

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private LocalDate birthdate;

    public Customer(int id, String name, String surname, String mail, String password, LocalDate birthdate) {
        super(id, name, surname, mail, password);
        this.birthdate = birthdate;
    }

    public Customer(User user) {
        super(user);
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    @Override
    public String toString() {
        return "Customer [id=" + this.getDni() + ", name=" + this.getName() + ", surname=" + this.getSurname() +
                ", mail=" + this.getMail() + ", birthdate=" + dateFormatter.format(birthdate) + "]";
    }


}
