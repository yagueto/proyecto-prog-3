package domain;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Customer extends User {

	private ZonedDateTime birthdate;
	private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

	public Customer(int id, String name, String surname, String mail, ZonedDateTime birthdate) {
		super(id, mail, mail, mail);
		this.birthdate = birthdate;
	}

	public ZonedDateTime getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(ZonedDateTime birthdate) {
		this.birthdate = birthdate;
	}

	@Override
	public String toString() {
		return "Customer [id=" + this.getDni() + ", name=" + this.getName() + ", surname=" + this.getSurname() +
				", mail=" + this.getMail() + ", birthdate=" + dateFormatter.format(birthdate) + "]";
	}
	
	
	
}
