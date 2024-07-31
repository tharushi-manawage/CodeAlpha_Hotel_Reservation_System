import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

class Room {
    private int roomNumber;
    private String category;
    private double price;
    private boolean isAvailable;

    public Room(int roomNumber, String category, double price) {
        this.roomNumber = roomNumber;
        this.category = category;
        this.price = price;
        this.isAvailable = true;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public String toString() {
        return "Room " + roomNumber + " (" + category + ") - $" + price;
    }
}

class Booking {
    private int bookingId;
    private Room room;
    private String guestName;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private double totalCost;

    public Booking(int bookingId, Room room, String guestName, LocalDate checkInDate, LocalDate checkOutDate) {
        this.bookingId = bookingId;
        this.room = room;
        this.guestName = guestName;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.totalCost = room.getPrice() * getNumberOfNights();
    }

    public int getBookingId() {
        return bookingId;
    }

    public Room getRoom() {
        return room;
    }

    public String getGuestName() {
        return guestName;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public double getTotalCost() {
        return totalCost;
    }

    private long getNumberOfNights() {
        return ChronoUnit.DAYS.between(checkInDate, checkOutDate);
    }

    @Override
    public String toString() {
        return "\n\tBooking ID: " + bookingId + "\n" +
               "\tRoom: " + room + "\n" +
               "\tGuest Name: " + guestName + "\n" +
               "\tCheck-in Date: " + checkInDate + "\n" +
               "\tCheck-out Date: " + checkOutDate + "\n" +
               "\tTotal Cost: $" + totalCost;
    }
}

class Hotel {
    private List<Room> rooms;
    private List<Booking> bookings;
    private Map<String, Double> paymentMethods;
    private int nextBookingId;

    public Hotel() {
        rooms = new ArrayList<>();
        bookings = new ArrayList<>();
        paymentMethods = new HashMap<>();
        nextBookingId = 1;

        rooms.add(new Room(101, "Single", 100.0));
        rooms.add(new Room(102, "Double", 150.0));
        rooms.add(new Room(103, "Suite", 250.0));

        paymentMethods.put("Cash", 0.0);
        paymentMethods.put("Credit Card", 2.5);
        paymentMethods.put("Debit Card", 1.5);
    }

    public void searchRooms(String category) {
        System.out.println("\n\t\tAvailable rooms in " + category + " category: ");
        for (Room room : rooms) {
            if (room.getCategory().equalsIgnoreCase(category) && room.isAvailable()) {
                System.out.println("\t\t" + room);
            }
        }
    }

    public boolean makeReservation(int roomNumber, String guestName, LocalDate checkInDate, LocalDate checkOutDate) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber && room.isAvailable()) {
                Booking booking = new Booking(nextBookingId++, room, guestName, checkInDate, checkOutDate);
                bookings.add(booking);
                room.setAvailable(false);
                System.out.println("\n\tReservation made successfully! Booking ID: " + booking.getBookingId());
                return true;
            }
        }
        System.out.println("\n\tRoom not available or not found!");
        return false;
    }

    public void viewBookingDetails(int bookingId) {
        for (Booking booking : bookings) {
            if (booking.getBookingId() == bookingId) {
                System.out.println(booking);
                return;
            }
        }
        System.out.println("\n\tBooking not found!");
    }

    public void processPayment(int bookingId, String paymentMethod) {
        Booking booking = findBookingById(bookingId);
        if (booking != null) {
            double totalCost = booking.getTotalCost();
            Double fee = paymentMethods.get(paymentMethod);
            if (fee != null) {
                System.out.println("\n\tPayment processed successfully! Total amount paid: $" + (totalCost + fee));
            } else {
                System.out.println("\n\tInvalid payment method!");
            }
        } else {
            System.out.println("\n\tBooking not found!");
        }
    }

    private Booking findBookingById(int bookingId) {
        for (Booking booking : bookings) {
            if (booking.getBookingId() == bookingId) {
                return booking;
            }
        }
        return null;
    }
}

public class HotelReservationSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Hotel hotel = new Hotel();

        while (true) {
			System.out.println("\t+--------------------------------------------------------------------+");
			System.out.println("\t|\t\t      HOTEL RESERVATION SYSTEM\t\t\t     |");
			System.out.println("\t+--------------------------------------------------------------------+");
			
            System.out.println("\t\t[1] Search Available Rooms\t[2] Make Reservation\n\t\t[3] View Booking Details\t[4] Process Payment\n\t\t[5] Exit");
            
            System.out.print("\n\tChoose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine();
            
            System.out.println("\n");

            switch (option) {
                case 1:
					System.out.println("\t+--------------------------------------------------------------------+");
					System.out.println("\t|\t\t\tSEARCH AVAILABLE ROOMS\t\t\t     |");
					System.out.println("\t+--------------------------------------------------------------------+");
					
                    System.out.print("\t\tEnter room category (Single/Double/Suite): ");
                    String category = scanner.nextLine();
                    
                    hotel.searchRooms(category);
                    
                    System.out.println("\n");
                    break;
                case 2:
					System.out.println("\t+--------------------------------------------------------------------+");
					System.out.println("\t|\t\t\t  MAKE RESERVATION\t\t\t     |");
					System.out.println("\t+--------------------------------------------------------------------+");
                
                    System.out.print("\t\tEnter room number to book: ");
                    int roomNumber = scanner.nextInt();
                    scanner.nextLine();
                    
                    System.out.print("\n\t\tEnter guest name: ");
                    String guestName = scanner.nextLine();
                    
                    System.out.print("\t\tEnter check-in date (YYYY-MM-DD): ");
                    String checkInStr = scanner.nextLine();
                    
                    System.out.print("\t\tEnter check-out date (YYYY-MM-DD): ");
                    String checkOutStr = scanner.nextLine();
                    
                    try {
                        LocalDate checkInDate = LocalDate.parse(checkInStr);
                        LocalDate checkOutDate = LocalDate.parse(checkOutStr);
                        hotel.makeReservation(roomNumber, guestName, checkInDate, checkOutDate);
                    } catch (Exception e) {
                        System.out.println("\n\tInvalid date format! Please use YYYY-MM-DD.");
                    }
                    
                    System.out.println("\n");
                    break;
                case 3:
					System.out.println("\t+--------------------------------------------------------------------+");
					System.out.println("\t|\t\t\tVIEW BOOKING DETAILS\t\t\t     |");
					System.out.println("\t+--------------------------------------------------------------------+");
                
                    System.out.print("\t\tEnter booking ID to view: ");
                    int bookingId = scanner.nextInt();
                    scanner.nextLine();
                    
                    hotel.viewBookingDetails(bookingId);
                    
                    System.out.println("\n");
                    break;
                case 4:
					System.out.println("\t+--------------------------------------------------------------------+");
					System.out.println("\t|\t\t\t  PROCESS PAYMENT\t\t\t     |");
					System.out.println("\t+--------------------------------------------------------------------+");
                
                    System.out.print("\t\tEnter booking ID to process payment: ");
                    bookingId = scanner.nextInt();
                    scanner.nextLine();
                    
                    System.out.print("\t\tEnter payment method (Cash/Credit Card/Debit Card): ");
                    String paymentMethod = scanner.nextLine();
                    hotel.processPayment(bookingId, paymentMethod);
                    
                    System.out.println("\n");
                    break;
                case 5:
                    System.out.println("\tExiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("\tInvalid option! Please try again.");
            }
        }
    }
}