import java.util.*;

/**
 * ============================================================
 * MAIN CLASS - UseCase11ConcurrentBookingSimulation
 * ============================================================
 * Use Case 11: Concurrent Booking Simulation (Thread Safety)
 */

public class UseCase11ConcurrentBookingSimulation {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();

        // Shared booking queue
        Queue<Reservation> queue = new LinkedList<>();

        // Simulate multiple booking requests
        queue.add(new Reservation("R1", "Abhi", "Single"));
        queue.add(new Reservation("R2", "Subha", "Single"));
        queue.add(new Reservation("R3", "Vannathi", "Suite"));
        queue.add(new Reservation("R4", "John", "Single"));
        queue.add(new Reservation("R5", "Meena", "Suite"));

        BookingProcessor processor = new BookingProcessor(queue, inventory);

        // Create multiple threads (simulating users)
        Thread t1 = new Thread(processor);
        Thread t2 = new Thread(processor);
        Thread t3 = new Thread(processor);

        t1.start();
        t2.start();
        t3.start();
    }
}

/**
 * Reservation Class
 */
class Reservation {
    String id;
    String guest;
    String roomType;

    public Reservation(String id, String guest, String roomType) {
        this.id = id;
        this.guest = guest;
        this.roomType = roomType;
    }
}

/**
 * Thread-safe Room Inventory
 */
class RoomInventory {

    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single", 2);
        inventory.put("Suite", 1);
    }

    // synchronized = critical section (thread-safe)
    public synchronized boolean allocateRoom(String type) {

        int available = inventory.getOrDefault(type, 0);

        if (available <= 0) {
            return false;
        }

        inventory.put(type, available - 1);
        return true;
    }
}

/**
 * Booking Processor (Runnable for threads)
 */
class BookingProcessor implements Runnable {

    private Queue<Reservation> queue;
    private RoomInventory inventory;

    public BookingProcessor(Queue<Reservation> queue, RoomInventory inventory) {
        this.queue = queue;
        this.inventory = inventory;
    }

    @Override
    public void run() {

        while (true) {

            Reservation r;

            // Critical section for queue access
            synchronized (queue) {
                if (queue.isEmpty()) {
                    return;
                }
                r = queue.poll();
            }

            // Allocate room safely
            boolean success = inventory.allocateRoom(r.roomType);

            if (success) {
                System.out.println(Thread.currentThread().getName()
                        + " → Booking Confirmed: " + r.id
                        + " | " + r.guest
                        + " | " + r.roomType);
            } else {
                System.out.println(Thread.currentThread().getName()
                        + " → Booking Failed: " + r.id
                        + " | No " + r.roomType + " rooms available");
            }
        }
    }
}