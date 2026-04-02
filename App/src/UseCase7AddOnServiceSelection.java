import java.util.*;

/**
 * ============================================================
 * MAIN CLASS - UseCase7AddOnServiceSelection
 * ============================================================
 * Use Case 7: Add-On Service Selection
 */

public class UseCase7AddOnServiceSelection {

    public static void main(String[] args) {

        // Create reservations
        Reservation r1 = new Reservation("R1", "Abhi");
        Reservation r2 = new Reservation("R2", "Subha");

        // Create services
        AddOnService wifi = new AddOnService("WiFi", 200);
        AddOnService breakfast = new AddOnService("Breakfast", 300);
        AddOnService spa = new AddOnService("Spa", 500);

        AddOnServiceManager manager = new AddOnServiceManager();

        // Add services to reservations
        manager.addService(r1.getReservationId(), wifi);
        manager.addService(r1.getReservationId(), breakfast);

        manager.addService(r2.getReservationId(), spa);

        // Display results
        System.out.println("Add-On Services Summary\n");

        manager.printServices(r1.getReservationId());
        manager.printTotalCost(r1.getReservationId());

        System.out.println();

        manager.printServices(r2.getReservationId());
        manager.printTotalCost(r2.getReservationId());
    }
}

/**
 * Reservation Class
 */
class Reservation {
    private String reservationId;
    private String guestName;

    public Reservation(String reservationId, String guestName) {
        this.reservationId = reservationId;
        this.guestName = guestName;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }
}

/**
 * Add-On Service Class
 */
class AddOnService {
    private String serviceName;
    private double cost;

    public AddOnService(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getCost() {
        return cost;
    }
}

/**
 * Add-On Service Manager Class
 */
class AddOnServiceManager {

    // Map: Reservation ID -> List of Services
    private Map<String, List<AddOnService>> servicesMap;

    public AddOnServiceManager() {
        servicesMap = new HashMap<>();
    }

    // Add service to reservation
    public void addService(String reservationId, AddOnService service) {
        servicesMap
                .computeIfAbsent(reservationId, k -> new ArrayList<>())
                .add(service);
    }

    // Print services for a reservation
    public void printServices(String reservationId) {
        List<AddOnService> services = servicesMap.get(reservationId);

        System.out.println("Services for Reservation " + reservationId + ":");

        if (services == null || services.isEmpty()) {
            System.out.println("No services selected.");
            return;
        }

        for (AddOnService s : services) {
            System.out.println("- " + s.getServiceName() + " (Rs. " + s.getCost() + ")");
        }
    }

    // Calculate and print total cost
    public void printTotalCost(String reservationId) {
        List<AddOnService> services = servicesMap.get(reservationId);

        double total = 0;

        if (services != null) {
            for (AddOnService s : services) {
                total += s.getCost();
            }
        }

        System.out.println("Total Add-On Cost: Rs. " + total);
    }
}