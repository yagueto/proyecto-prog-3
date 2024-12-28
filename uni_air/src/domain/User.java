package domain;

public abstract class User {

	private int dni;
	private String name;
	private String surname;
	private String mail;
	private String password;

	public User(int dni, String name, String surname, String mail, String password) {
		super();
		this.dni = dni;
		this.name = name;
		this.surname = surname;
		this.mail = mail;
	}

	public int getDni() {
		return dni;
	}

	public void setDni(int dni) {
		this.dni = dni;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User [dni=" + dni + ", name=" + name + ", surname=" + surname + ", mail=" + mail + ", password="
				+ password + "]";
	}
}