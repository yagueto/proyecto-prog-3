package domain;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Client extends User{

	private ZonedDateTime birthdate;
	private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-YYYY");

	public Client(int id, String name, String surname, String mail, ZonedDateTime birthdate) {
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
		return "Client [id=" + this.getDni() + ", name=" + this.getName() + ", surname=" + this.getSurname() + 
				", mail=" + this.getMail() + ", birthdate=" + dateFormatter.format(birthdate) + "]";
	}
	
	
	
}
