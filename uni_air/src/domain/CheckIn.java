package domain;

import java.util.HashSet;

public class CheckIn {
    private Booking booking;
    private String seat;
    private int Id;

    public CheckIn(Booking booking) {
        this.booking = booking;
        String asiento = selectSeat(booking.getFlight().getOccupied(), booking.getFlight().getMaxPasajeros());
        this.seat = asiento;
        booking.getFlight().getOccupied().add(asiento);
        Id = booking.getId();
    }

    public CheckIn(Booking booking, String seat){
        this.booking = booking;
        this.seat = seat;
        booking.getFlight().getOccupied().add(seat);
        Id = booking.getId();
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

    private static String selectSeat(HashSet<String> occupiedSeats, int maxPassengers) {
        return seatFinder(maxPassengers / 6, 6, occupiedSeats, 1, 'A');
    }
    private static String seatFinder(int totalRows, int totalColumns, HashSet<String> occupiedSeats, int currentRow, char currentColumn){
        String currentSeat = currentRow + String.valueOf(currentColumn);
        if (currentRow > totalRows) {
            return "No hay asientos disponibles.";
        }
        if (!occupiedSeats.contains(currentSeat)) {
            return currentSeat;
        }
        currentColumn++;
        if (currentColumn > 'A' + totalColumns - 1) {
            currentColumn = 'A';
            currentRow++;
        }
        return seatFinder(totalRows, totalColumns, occupiedSeats, currentRow, currentColumn);
    }
}
