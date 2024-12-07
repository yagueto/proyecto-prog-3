package domain;

public class Booking {
    private final User customer;
    private Flight flight;
    private final int id;

    public Booking(User customer, Flight flight, int id) {
        this.customer = customer;
        this.flight = flight;
        this.id = id;
    }

    public User getCustomer() {
        return customer;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public int getId() {
        return id;
    }
}
