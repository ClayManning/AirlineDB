package airline.database;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class AirlineDatabase {

    public static void main(String[] args) {
        Flight.flights = genFlights();
        Employee.emps = genEmps();
        UI();
    }

    private static ArrayList<Flight> genFlights() {
        ArrayList<Flight> Flights = new ArrayList<>();
        ArrayList<String> planes = new ArrayList<>();
        try {//Reads in file containing plane names and adds them to  arraylist of planes
            try (Scanner s = new Scanner(new File("planes.txt"))) {
                while (s.hasNext()) {
                    planes.add(s.next());
                }
            }

            String id;
            int numFlights = 10;//Arbitrary number of flights to generate
            for (int i = 0; i < numFlights; i++) {
                Random rand = new Random();

                char first = (char) (rand.nextInt(26) + 'A');
                char second = (char) (rand.nextInt(26) + 'A');
                int num = rand.nextInt(9000) + 1000;
                id = first + "" + second + Integer.toString(num);
                String p = planes.get(rand.nextInt(planes.size()));
                Flight f = new Flight(id, rand.nextBoolean(), rand.nextBoolean(), p, rand.nextBoolean());
                Flights.add(f);
            }
        } catch (Exception e) {
            System.out.println("Error reading in planes from file");
            System.exit(0);
        }
        return Flights;
    }

    public static void UI() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to the Airline Database!");
        System.out.println("Your options are");
        System.out.println("1. Print all employees");
        System.out.println("2. Print all available flights");
        System.out.println("3. Print only outgoing flights");
        System.out.println("4. Print only incoming flights");
        System.out.println("5. If you are an employee you can login to view your profile");
        System.out.println("Please enter your choice:");
        char input1 = sc.next().charAt(0);
        while (!validMenu(input1)) {
            System.out.println("Invalid input, please enter a choice from the menu above");
            input1 = sc.next().charAt(0);
        }

        int input = Character.getNumericValue(input1);
        switch (input) {
            case 1:
                Employee.emps.forEach((x) -> {
                    x.printAll();
                });
                System.out.println("Exiting Application...");
                System.exit(0);
            case 2:
                Flight.flights.forEach((x) -> {
                    x.print();
                });
                System.out.println("Exiting Application...");
                System.exit(0);
            case 3:
                Flight.flights.forEach((x) -> {
                    x.printOutgoing();
                });
                if (flightAvailable(Flight.flights)) {//There might not be any flights available
                    System.out.println("Would you like to purchase a ticket? (Y/N)");
                    char choice = sc.next().charAt(0);
                    if (choice == 'Y' || choice == 'y') {
                        System.out.println("Redirecting to our payment service...");
                        Payment.PaymentUI();
                        System.exit(0);
                    } else {
                        System.out.println("Exiting Application...");
                        System.exit(0);
                    }
                }
                System.out.println("Exiting Application...");
                System.exit(0);
            case 4:
                Flight.flights.forEach((x) -> {
                    x.printIncoming();
                });
                System.out.println("Exiting Application...");
                System.exit(0);
            case 5:
                Employee.emps.forEach((x) -> {
                    x.printAll();
                });
                System.out.println("Enter your name:");
                String name = sc.next();
                if (validation(Employee.emps, name)) {
                    Employee e = Match(Employee.emps, name);
                    login(e);
                    System.out.println("Logging out...");
                    System.exit(0);
                } else {
                    System.out.println("Employee not found");
                    System.out.println("Exiting Application...");
                    System.exit(0);
                }
            default:
                System.out.println("Invalid input");
                System.out.println("Exiting Application...");
                System.exit(0);

        }

    }

    public static boolean validMenu(char c) {
        if (c == '1' || c == '2' || c == '3' || c == '4' || c == '5') {
            return true;
        }
        return false;
    }

    public static Employee Match(ArrayList<Employee> emps, String name) {
        for (Employee x : emps) {
            if (x.name.equals(name)) {
                return x;
            }
        }
        Employee e = new Pilot("NULL", "NULL", "NULL");
        return e;//This is returning a null employee, but because of the earlier validation, this return statement will never be reached
    }

    public static boolean flightAvailable(ArrayList<Flight> flights) {//Returns true if any flight has seats available
        for (Flight f : flights) {
            if (f.available == true) {
                return true;
            }

        }
        return false;
    }

    public static boolean validation(ArrayList<Employee> emps, String name) {//Checks for the string name in list of employees
        return emps.stream().anyMatch((g) -> (g.name.equals(name)));
    }

    public static void login(Employee emp) {
        Scanner sc = new Scanner(System.in);
        System.out.printf("Enter your 4 digit id:");
        String id = sc.nextLine();
        while (!validID(id)) {
            System.out.println("Invalid id");
            System.out.println("Please re-enter your id");
            id = sc.nextLine();
        }
        int ID = Integer.parseInt(id);
        System.out.println("Enter your password:");
        String password = sc.next();
        emp.Login(password, ID);

    }

    public static boolean validID(String id) {//Checks for invalid characters in ID
        try {
            int ID = Integer.parseInt(id);
            if (ID > 9999 || ID < 1000) {
                return false;
            }
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    private static ArrayList<Employee> genEmps() {
        ArrayList<Employee> e = new ArrayList<>();
        Employee Sarah = new FlightAttendant("Sarah", "sarah@hotmail.com", "123Abc");
        Employee Jamie = new FlightAttendant("Jamie", "jamie@gmail.com", "12432423r%$#");
        Employee John = new Pilot("John", "jonathanr@yahoo.com", "badpassword567");
        Employee Raj = new Pilot("Raj", "raj@gmail.com", "116302");
        Employee Otis = new FlightAttendant("Otis", "otis@hotmail.com", "939552");
        Employee Maegan = new Pilot("Maegan", "maegan@yahoo.com", "039849");
        Employee Jamel = new FlightAttendant("Jamel", "jamel@hotmail.com", "790999");
        Employee Fraser = new FlightAttendant("Fraser", "fraser@gmail.com", "050087");
        Employee Ellis = new Pilot("Ellis", "Ellis@yahoo.com", "34534612");
        Employee Gilberto = new FlightAttendant("Gilberto", "gb@gmail.com", "sefwgf445234");
        Employee Luther = new AirMarshall("Luther", "lym@hotmail.com", "3245245");
        Employee Cora = new AirMarshall("Cora", "cmf@hotmail.com", "34r5123e");
        Employee Levi = new AirTrafficController("Levi", "lrv@yahoo.com", "431234");
        Employee Randy = new AirTrafficController("Randy", "ryv@yahoo.com", "4r523413w");
        Employee Jason = new AirTrafficController("Jason", "jgm@yahoo.com", "23452354");
        Employee Emily = new AirMarshall("Emily", "emm@hotmail.com", "24352354");
        
        e.add(Jason);
        e.add(Emily);
        e.add(Randy);
        e.add(Levi);
        e.add(Cora);
        e.add(Luther);
        e.add(Gilberto);
        e.add(Ellis);
        e.add(Sarah);
        e.add(Jamie);
        e.add(John);
        e.add(Raj);
        e.add(Otis);
        e.add(Maegan);
        e.add(Jamel);
        e.add(Fraser);

        return e;
    }
}
