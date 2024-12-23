package domain;

public class CheckIn {
    private Booking booking;
    private String seat;
    private int Id;

    public CheckIn(Booking booking, String seat, int id) {
        this.booking = booking;
        this.seat = seat;
        Id = id;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }
}
