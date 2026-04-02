import java.util.*;

/**
 * ============================================================
 * MAIN CLASS - UseCase8BookingHistoryReport
 * ============================================================
 * Use Case 8: Booking History & Reporting
 */

public class UseCase8BookingHistoryReport {

    public static void main(String[] args) {

        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        // Simulating confirmed bookings
        Reservation r1 = new Reservation("R1", "Abhi", "Single");
        Reservation r2 = new Reservation("R2", "Subha", "Suite");
        Reservation r3 = new Reservation("R3", "Vannathi", "Single");

        // Add to booking history (in order)
        history.addReservation(r1);
        history.addReservation(r2);
        history.addReservation(r3);

        System.out.println("=== Booking History ===\n");
        reportService.printAllBookings(history);

        System.out.println("\n=== Booking Summary Report ===\n");
        reportService.generateSummary(history);
    }
}

/**
 * Reservation Class
 */
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

/**
 * BookingHistory Class
 * Stores confirmed bookings in order
 */
class BookingHistory {

    private List<Reservation> reservations;

    public BookingHistory() {
        reservations = new ArrayList<>();
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    public List<Reservation> getAllReservations() {
        return reservations;
    }
}

/**
 * BookingReportService Class
 * Generates reports from booking history
 */
class BookingReportService {

    // Print all bookings
    public void printAllBookings(BookingHistory history) {
        List<Reservation> list = history.getAllReservations();

        for (Reservation r : list) {
            System.out.println("Reservation ID: " + r.getReservationId()
                    + ", Guest: " + r.getGuestName()
                    + ", Room Type: " + r.getRoomType());
        }
    }

    // Generate summary report
    public void generateSummary(BookingHistory history) {

        List<Reservation> list = history.getAllReservations();

        Map<String, Integer> countByRoomType = new HashMap<>();

        for (Reservation r : list) {
            String type = r.getRoomType();
            countByRoomType.put(type, countByRoomType.getOrDefault(type, 0) + 1);
        }

        System.out.println("Total Bookings: " + list.size());

        for (String type : countByRoomType.keySet()) {
            System.out.println(type + " Rooms Booked: " + countByRoomType.get(type));
        }
    }
}