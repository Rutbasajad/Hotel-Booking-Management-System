import java.util.*;

/**
 * ============================================================
 * MAIN CLASS - UseCase9ErrorHandlingValidation
 * ============================================================
 * Use Case 9: Error Handling & Validation
 */

public class UseCase9ErrorHandlingValidation {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        BookingService service = new BookingService(inventory);

        // Test cases (valid + invalid)
        List<Reservation> requests = Arrays.asList(
                new Reservation("R1", "Abhi", "Single"),   // valid
                new Reservation("R2", "Subha", "Suite"),   // valid
                new Reservation("R3", "John", "Deluxe"),   // invalid room type
                new Reservation("R4", "Meena", "Suite"),   // may become unavailable
                new Reservation("R5", "Raj", "Single"),
                new Reservation("R6", "Asha", "Single")    // may exceed inventory
        );

        for (Reservation r : requests) {
            try {
                service.processBooking(r);
            } catch (InvalidBookingException e) {
                System.out.println("Booking Failed for " + r.getGuestName()
                        + ": " + e.getMessage());
            }
        }
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
 * Custom Exception
 */
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

/**
 * Room Inventory with validation
 */
class RoomInventory {

    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single", 2);
        inventory.put("Suite", 1);
    }

    public void validateRoomType(String roomType) throws InvalidBookingException {
        if (!inventory.containsKey(roomType)) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }
    }

    public void checkAvailability(String roomType) throws InvalidBookingException {
        if (inventory.get(roomType) <= 0) {
            throw new InvalidBookingException("No rooms available for type: " + roomType);
        }
    }

    public void decrement(String roomType) throws InvalidBookingException {
        int count = inventory.get(roomType);

        if (count <= 0) {
            throw new InvalidBookingException("Cannot decrement. Inventory already zero.");
        }

        inventory.put(roomType, count - 1);
    }
}

/**
 * Booking Service with validation (Fail-Fast)
 */
class BookingService {

    private RoomInventory inventory;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void processBooking(Reservation reservation) throws InvalidBookingException {

        String type = reservation.getRoomType();

        // Step 1: Validate room type
        inventory.validateRoomType(type);

        // Step 2: Check availability
        inventory.checkAvailability(type);

        // Step 3: Allocate (update inventory safely)
        inventory.decrement(type);

        // Success message
        System.out.println("Booking Confirmed: "
                + reservation.getReservationId()
                + " | Guest: " + reservation.getGuestName()
                + " | Room Type: " + type);
    }
}