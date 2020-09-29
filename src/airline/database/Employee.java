package airline.database;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class Employee extends Login {

    public static ArrayList<Employee> emps = new ArrayList<>();
    private final int ID;
    final String name;
    private final String email;
    private final String password;
    ArrayList<Integer> IDList = new ArrayList<>(100);

    public Employee(String name, String email, String password) {
        this.ID = GenID();
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public int getID() {
        return ID;
    }

    private int GenID() {
        Random rand = new Random();
        int id = rand.nextInt(9000) + 1000;//Generates ID between 1001 and 9999
        while (IDList.contains(id)) {//In the unlikely event 2 ids are generated the same
            id = rand.nextInt(10000);
        }
        IDList.add(id);

        return id;
    }

    @Override
    boolean checkPass(String pass, int ID) {
        if (this.password.equals(pass) && this.ID == ID) {
            System.out.println("Logging in...");
            return true;
        } else {
            return false;
        }
    }

    @Override
    void Login(String password, int ID) {
        if (checkPass(password, ID)) {
            printWelcome();
        } else {
            System.out.println("Incorrect ID/Password combo");//One possibility is to throw this into a while loop and allow multiple guesses, but that would also allow brute-force attacks.
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Employee other = (Employee) obj;
        return Objects.equals(this.password, other.password);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.password);
        return hash;
    }

    public void printAll() {
        System.out.printf("Employee:%s ID: %s%n", name, ID);
    }

    public void printWelcome() {
        System.out.println("Welcome " + name);
        System.out.println("Your ID is " + ID);
        System.out.println("It is currently");
        LocalDate today = LocalDate.now();
        System.out.println(today);

    }

}
