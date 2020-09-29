package airline.database;

public class Pilot extends Employee {

    public Pilot(String name, String email, String password) {
        super(name, email, password);
    }

    @Override
    public void printWelcome() {
        super.printWelcome();
        System.out.printf("Here is the list of flights you will be piloting or co-piloting%n");
        Flight.flights.forEach((x) -> {
            x.printOutgoing();
        });
    }
}
