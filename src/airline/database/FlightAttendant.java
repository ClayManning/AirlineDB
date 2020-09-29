package airline.database;

public class FlightAttendant extends Employee {

    public FlightAttendant(String name, String email, String password) {
        super(name, email, password);
    }

    @Override
    public void printWelcome() {
        super.printWelcome();
        System.out.printf("Here is the list of flights you will be attending to%n");
        Flight.flights.forEach((x) -> {
            x.printOutgoing();
        });
    }
}
