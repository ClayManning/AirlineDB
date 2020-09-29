package airline.database;

public class AirMarshall extends Employee {

    public AirMarshall(String name, String email, String password) {
        super(name, email, password);
    }

    @Override
    public void printWelcome() {
        super.printWelcome();
        System.out.printf("Here is a list of your flight schedule, Marshall%n");
        Flight.flights.forEach((x) -> {
            x.printOutgoing();
        });
    }
}
