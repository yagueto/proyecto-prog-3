package domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Customer extends User {

	private LocalDate birthdate;
	private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

	public Customer(int id, String name, String surname, String mail, LocalDate birthdate) {
		super(id, mail, mail, mail);
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
		return "Customer [id=" + this.getDni() + ", name=" + this.getName() + ", surname=" + this.getSurname() +
				", mail=" + this.getMail() + ", birthdate=" + dateFormatter.format(birthdate) + "]";
	}
	
	
	
}
