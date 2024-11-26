package domain;

public class Employee extends User{

	
	private EmployeeType type;

	public Employee(int id, String name, String surname, String mail, EmployeeType type) {
		super(id, name, surname, mail);
		this.type = type;
	}

	public EmployeeType getType() {
		return type;
	}

	public void setType(EmployeeType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Employee [dni=" + this.getDni() + ", name=" + this.getName() + ", surname=" 
				+ this.getSurname() + ", mail=" + this.getMail() + ", type=" + type + "]";
	}

	enum EmployeeType {
		STANDARD, ADMIN
	}
}
