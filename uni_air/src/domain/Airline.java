package domain;

public class Airline {

	private String iata;
	private String name;

	public Airline(String iata, String name) {
		super();
		this.iata = iata;
		this.name = name;
	}

	public String getIata() {
		return iata;
	}

	public String getName() {
		return name;
	}

	public String toString() {
		return "Airline [iata=" + iata + ", name=" + name + "]";
	}

}
