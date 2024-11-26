package domain;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Client {

	private int dni;
	private String name;
	private String surname;
	private String mail;
	private ZonedDateTime birthdate;
	private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-YYYY");

	public Client(int id, String nom, String apellido, String mail, ZonedDateTime birthdate) {
		super();
		this.dni = id;
		this.name = nom;
		this.surname = apellido;
		this.mail = mail;
		this.birthdate = birthdate;
	}

	public int getId() {
		return dni;
	}

	public String getNom() {
		return name;
	}

	public void setNom(String nom) {
		this.name = nom;
	}

	public String getApellido() {
		return surname;
	}

	public void setApellido(String apellido) {
		this.surname = apellido;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public ZonedDateTime getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(ZonedDateTime birthdate) {
		this.birthdate = birthdate;
	}

	@Override
	public String toString() {
		return "Client [id=" + dni + ", name=" + name + ", surname=" + surname + ", mail=" + mail + ", birthdate="
				+ dateFormatter.format(birthdate) + "]";
	}
	
	
	
}
