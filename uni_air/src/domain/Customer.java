package domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Customer extends User {

	private LocalDate birthdate;
	private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

	public Customer(String dni, String name, String surname, String mail, String password,LocalDate birthdate) {
		super(dni, name, surname, mail,password);
		this.birthdate = birthdate;
	}

	

	public LocalDate getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(LocalDate birthdate) {
		this.birthdate = birthdate;
	}

	@Override
	public String toString() {
		return "Customer [dni=" + this.getDni() + ", name=" + this.getName() + ", surname=" + this.getSurname() +
				", mail=" + this.getMail() + ", birthdate=" + dateFormatter.format(birthdate) + "]";
	}
	
	
	
}
