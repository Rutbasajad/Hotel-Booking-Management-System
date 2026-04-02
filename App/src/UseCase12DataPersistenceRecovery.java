import java.io.*;
import java.util.*;

/**
 * ============================================================
 * MAIN CLASS - UseCase12DataPersistenceRecovery
 * ============================================================
 * Use Case 12: Data Persistence & System Recovery
 */

public class UseCase12DataPersistenceRecovery {

    public static void main(String[] args) {

        String fileName = "system_state.ser";

        // Create services
        RoomInventory inventory = new RoomInventory();
        BookingHistory history = new BookingHistory();
        PersistenceService persistence = new PersistenceService();

        // Try loading previous state
        System.out.println("Loading previous system state...\n");
        SystemState loadedState = persistence.load(fileName);

        if (loadedState != null) {
            inventory = loadedState.inventory;
            history = loadedState.history;
            System.out.println("System state restored successfully!\n");
        } else {
            System.out.println("No previous data found. Starting fresh.\n");
        }

        // Simulate new bookings
        history.addReservation(new Reservation("R1", "Abhi", "Single"));
        inventory.decrement("Single");

        history.addReservation(new Reservation("R2", "Subha", "Suite"));
        inventory.decrement("Suite");

        // Display current state
        System.out.println("Current Bookings:");
        for (Reservation r : history.getAllReservations()) {
            System.out.println(r.reservationId + " | " + r.guestName + " | " + r.roomType);
        }

        // Save state before shutdown
        System.out.println("\nSaving system state...");
        persistence.save(new SystemState(inventory, history), fileName);
        System.out.println("State saved successfully!");
    }
}

/**
 * Reservation Class (Serializable)
 */
class Reservation implements Serializable {
    String reservationId;
    String guestName;
    String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

/**
 * Booking History (Serializable)
 */
class BookingHistory implements Serializable {

    private List<Reservation> reservations;

    public BookingHistory() {
        reservations = new ArrayList<>();
    }

    public void addReservation(Reservation r) {
        reservations.add(r);
    }

    public List<Reservation> getAllReservations() {
        return reservations;
    }
}

/**
 * Room Inventory (Serializable)
 */
class RoomInventory implements Serializable {

    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single", 2);
        inventory.put("Suite", 1);
    }

    public void decrement(String type) {
        inventory.put(type, inventory.get(type) - 1);
    }

    public Map<String, Integer> getInventory() {
        return inventory;
    }
}

/**
 * Wrapper class for full system state
 */
class SystemState implements Serializable {
    RoomInventory inventory;
    BookingHistory history;

    public SystemState(RoomInventory inventory, BookingHistory history) {
        this.inventory = inventory;
        this.history = history;
    }
}

/**
 * Persistence Service
 */
class PersistenceService {

    // Save state to file
    public void save(SystemState state, String fileName) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(state);
        } catch (IOException e) {
            System.out.println("Error saving state: " + e.getMessage());
        }
    }

    // Load state from file
    public SystemState load(String fileName) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            return (SystemState) ois.readObject();
        } catch (FileNotFoundException e) {
            return null; // no previous file
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading state. Starting fresh.");
            return null;
        }
    }
}