/**
 * ------------------------------------------------------------
 * MAIN CLASS - UseCase2RoomInitialization
 * ------------------------------------------------------------
 *
 * Use Case 2: Basic Room Types & Static Availability
 *
 * This program demonstrates:
 * - Abstract classes
 * - Inheritance
 * - Polymorphism
 * - Static room availability using simple variables
 *
 * @author Developer
 * @version 2.1
 */

public class UC2BasicRoomTypesandStaticAvailablity {

    public static void main(String[] args) {

        System.out.println("Welcome to the Hotel Booking Management System");
        System.out.println("Version 2.1\n");

        // Create Room Objects
        Room singleRoom = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suiteRoom = new SuiteRoom();

        // Static availability variables
        int singleRoomAvailability = 10;
        int doubleRoomAvailability = 5;
        int suiteRoomAvailability = 2;

        // Display room details

        System.out.println("---- Single Room Details ----");
        singleRoom.displayRoomDetails();
        System.out.println("Available Rooms: " + singleRoomAvailability);
        System.out.println();

        System.out.println("---- Double Room Details ----");
        doubleRoom.displayRoomDetails();
        System.out.println("Available Rooms: " + doubleRoomAvailability);
        System.out.println();

        System.out.println("---- Suite Room Details ----");
        suiteRoom.displayRoomDetails();
        System.out.println("Available Rooms: " + suiteRoomAvailability);
        System.out.println();

        System.out.println("Room information displayed successfully.");
    }
}