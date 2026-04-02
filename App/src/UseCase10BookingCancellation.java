import java.util.*;

/**
 * ============================================================
 * MAIN CLASS - UseCase10BookingCancellation
 * ============================================================
 * Use Case 10: Booking Cancellation & Inventory Rollback
 */

public class UseCase10BookingCancellation {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        BookingService bookingService = new BookingService(inventory);
        CancellationService cancellationService = new CancellationService(inventory);

        // Confirm bookings
        bookingService.book("R1", "Abhi", "Single");
        bookingService.book("R2", "Subha", "Suite");

        System.out.println("\n--- Cancellation Process ---\n");

        // Valid cancellation
        cancellationService.cancel("R1");

        // Invalid cancellation (already cancelled / not exists)
        cancellationService.cancel("R1");
        cancellationService.cancel("R5");
    }
}

/**
 * Reservation Class
 */
class Reservation {
    String reservationId;
    String guestName;
    String roomType;
    String roomId;

    public Reservation(String reservationId, String guestName, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
    }
}

/**
 * Room Inventory
 */
class RoomInventory {

    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single", 2);
        inventory.put("Suite", 1);
    }

    public boolean isAvailable(String type) {
        return inventory.getOrDefault(type, 0) > 0;
    }

    public void decrement(String type) {
        inventory.put(type, inventory.get(type) - 1);
    }

    public void increment(String type) {
        inventory.put(type, inventory.get(type) + 1);
    }
}

/**
 * Booking Service (for confirmed bookings)
 */
class BookingService {

    private RoomInventory inventory;
    private Map<String, Reservation> confirmedBookings = new HashMap<>();
    private int counter = 1;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void book(String id, String guest, String type) {

        if (!inventory.isAvailable(type)) {
            System.out.println("Booking failed for " + guest + ": No availability");
            return;
        }

        String roomId = type + "-" + counter++;

        inventory.decrement(type);

        Reservation r = new Reservation(id, guest, type, roomId);
        confirmedBookings.put(id, r);

        System.out.println("Booking Confirmed: " + id + " | Room ID: " + roomId);
    }

    public Map<String, Reservation> getConfirmedBookings() {
        return confirmedBookings;
    }
}

/**
 * Cancellation Service (Rollback logic using Stack)
 */
class CancellationService {

    private RoomInventory inventory;
    private Map<String, Reservation> confirmedBookings;
    private Stack<String> rollbackStack;

    public CancellationService(RoomInventory inventory) {
        this.inventory = inventory;
        this.rollbackStack = new Stack<>();
        this.confirmedBookings = new HashMap<>();
    }

    // Inject bookings (simple linking for this example)
    public void setBookings(Map<String, Reservation> bookings) {
        this.confirmedBookings = bookings;
    }

    public void cancel(String reservationId) {

        // First-time setup (link bookings if empty)
        if (confirmedBookings.isEmpty()) {
            System.out.println("System not linked with bookings.");
            return;
        }

        if (!confirmedBookings.containsKey(reservationId)) {
            System.out.println("Cancellation failed: Invalid reservation ID " + reservationId);
            return;
        }

        Reservation r = confirmedBookings.get(reservationId);

        // Push to rollback stack (LIFO)
        rollbackStack.push(r.roomId);

        // Restore inventory
        inventory.increment(r.roomType);

        // Remove booking
        confirmedBookings.remove(reservationId);

        System.out.println("Cancellation successful for " + reservationId
                + " | Released Room ID: " + r.roomId);
    }
}