package airline.database;

import java.util.ArrayList;

public class Flight {

    public static ArrayList<Flight> flights = new ArrayList<>();
    private final String flightId;//Stores a flight ID consisting of 2 characters and 4 digits, "AB1234"
    private final Boolean Outgoing;// A flight can only be incoming ot outgoing, if true, flight is outgoing, if false, flight is incoming
    private final Boolean Type;//Domestic or International
    private final String aircraft;//Stores type of aircraft
    Boolean available;//If flight has seats avaliable, this will not be final because sometimes people cancel tickets and the status must change accordingly

    public Flight(String flightId, Boolean Outgoing, Boolean Type, String aircraft, Boolean available) {
        this.flightId = flightId;
        this.Outgoing = Outgoing;
        this.Type = Type;
        this.aircraft = aircraft;
        this.available = available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public void printIncoming() {
        if (!Outgoing) {
            print();
        }
    }

    public void printAvailable(Flight f) {
        if (available) {
            print();
        }
    }

    public void printOutgoing() {
        if (Outgoing) {
            print();
        }
    }

    public void print() {
        System.out.printf("Flight:%s", flightId);
        if (Outgoing) {
            System.out.printf(" - Outgoing");
        } else {
            System.out.printf(" - Incoming");
        }
        if (Type) {
            System.out.printf(" - Domestic");
        } else {
            System.out.printf(" - International");
        }
        System.out.printf(" - Aircaft:%s", aircraft);
        if (available) {
            System.out.printf(" - Has seats available");
        } else {
            System.out.printf(" - At full capacity");
        }
        System.out.println("");
    }

}
