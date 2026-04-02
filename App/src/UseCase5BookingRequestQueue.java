import java.util.LinkedList;
import java.util.Queue;

/**
 * MAIN CLASS - UseCase5BookingRequestQueue
 * Use Case 5: Booking Request (First-Come-First-Served)
 */
public class UseCase5BookingRequestQueue{

    public static void main(String[] args) {

        // Display application header
        System.out.println("Booking Request Queue");

        // Initialize booking queue
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Create booking requests
        Reservation r1 = new Reservation("Abhi", "Single");
        Reservation r2 = new Reservation("Suba", "Double");
        Reservation r3 = new Reservation("Vannathi", "Suite");

        // Add requests to the queue
        bookingQueue.addRequest(r1);
        bookingQueue.addRequest(r2);
        bookingQueue.addRequest(r3);

        // Process requests in FIFO order
        while (bookingQueue.hasPendingRequests()) {
            Reservation r = bookingQueue.getNextRequest();
            System.out.println("Processing booking for Guest: "
                    + r.getGuestName() + ", Room Type: "
                    + r.getRoomType());
        }
    }
}

/**
 * CLASS - Reservation
 * Represents a booking request.
 */
class Reservation {

    // Name of the guest making the booking
    private String guestName;

    // Requested room type
    private String roomType;

    // Constructor
    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    // Getter for guest name
    public String getGuestName() {
        return guestName;
    }

    // Getter for room type
    public String getRoomType() {
        return roomType;
    }
}

/**
 * CLASS - BookingRequestQueue
 * Manages booking requests using FIFO queue.
 */
class BookingRequestQueue {

    // Queue that stores booking requests
    private Queue<Reservation> requestQueue;

    // Constructor initializes queue
    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    // Add booking request
    public void addRequest(Reservation reservation) {
        requestQueue.offer(reservation);
    }

    // Get and remove next request
    public Reservation getNextRequest() {
        return requestQueue.poll();
    }

    // Check if queue has pending requests
    public boolean hasPendingRequests() {
        return !requestQueue.isEmpty();
    }
}