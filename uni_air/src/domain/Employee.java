package domain;

public class Employee {

	private int dni;
	private String name;
	private String surname;
	private String mail;
	private EmployeeType type;

	public Employee(int id, String nom, String apellido, String mail, EmployeeType type) {
		super();
		this.dni = id;
		this.name = nom;
		this.surname = apellido;
		this.mail = mail;
		this.type = type;
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

	public EmployeeType getType() {
		return type;
	}

	public void setType(EmployeeType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Employee [dni=" + dni + ", name=" + name + ", surname=" + surname + ", mail=" + mail + ", type=" + type
				+ "]";
	}

	enum EmployeeType {
		STANDARD, ADMIN
	}
}
