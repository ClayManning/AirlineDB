package airline.database;

public class AirTrafficController extends Employee {

    public AirTrafficController(String name, String email, String password) {
        super(name, email, password);
    }

    @Override
    public void printWelcome() {
        super.printWelcome();
        System.out.printf("Here is a list of the incoming and outgoing flights%n");
        Flight.flights.forEach((x) -> {
            x.print();
        });
    }
}
