package airline.database;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.Scanner;

public class Payment {

    private final String cardNum;
    private final Date validation;
    private final String pin;

    public Payment(String cardNum, String pin, Date validation) {
        this.cardNum = cardNum;
        this.validation = validation;
        this.pin = pin;
    }

    public Payment(String cardNum, String pin) {//Some cards do not expire, for those we use an overloaded constructor
        this.cardNum = cardNum;
        this.pin = pin;
        Calendar myCalendar = new GregorianCalendar(9999, 0, 01);
        this.validation = myCalendar.getTime();//This may cause issues in the year 9999, but as any good developer does, I'll leave this for someone else to fix/maintain
    }

    public static boolean validCardnum(String num) {
        if (num.length() < 16 || num.length() > 16) {//Return false if card number is greater than or less than 16 digits long, or if card number is negative
            return false;
        }
        for (int i = 0; i < num.length(); i++) {//Checks for alphabetic characters
            char x = num.charAt(i);
            if ((x >= 'A' && x <= 'Z') || (x >= 'a' && x <= 'z')) {
                return false;
            }

        }
        return true;
    }

    public static boolean validpinNum(String num) {
        if (num.length() < 3 || num.length() > 3) {//Return false if pin is greater than or less than 3 digits long, or if pin is negative
            return false;
        }
        for (int i = 0; i < num.length(); i++) {//Checks for alphabetic characters
            char x = num.charAt(i);
            if ((x >= 'A' && x <= 'Z') || (x >= 'a' && x <= 'z')) {
                return false;
            }

        }
        return true;
    }

    public static boolean validChoice(char c) {//Ensures the choice is within the specified boundries
        return c == 'Y' || c == 'y' || c == 'N' || c == 'n';
    }

    public static void PaymentUI() {
        Scanner sc = new Scanner(System.in);
        Random rand = new Random();
        int price = rand.nextInt(1000) + 100;
        System.out.println("\n\nWelcome to Super Cheap Flights!");
        System.out.printf("The tickets today cost $%d.00 each%n", price);
        System.out.println("How many tickets would you like to purchase?");
        int choice = sc.nextInt();
        System.out.printf("Your total today is $%d.00 - %d x $%d.00%n", price * choice, choice, price);
        Payment p = Processing();
        System.out.println("Purchase successful!");
        String code = Comfirmation();
        System.out.println("Your confirmation code is: #" + code);
        System.out.println("Thank you for using Super Cheap Flights!");
    }

    public static String Comfirmation() {//Generates randomized confirmation code string
        Random rand = new Random();
        char var1 = (char) (rand.nextInt(26) + 'A');
        char var2 = (char) (rand.nextInt(26) + 'A');
        char var3 = (char) (rand.nextInt(26) + 'A');
        char var4 = (char) (rand.nextInt(26) + 'A');
        char var5 = (char) (rand.nextInt(26) + 'A');
        int num = rand.nextInt(900000) + 100000;
        String confirm = var1 + "" + var2 + "" + var3 + "" + var4 + "" + var5 + Integer.toString(num);
        return confirm;
    }

    public static Payment Processing() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your 16 digit card number:");
        String cardnum = sc.nextLine();
        while (!validCardnum(cardnum)) {
            System.out.println("Invalid card number, please re-enter:");
            cardnum = sc.nextLine();
        }
        System.out.println("Enter your 3 digit pin:");
        String pinNum = sc.nextLine();
        while (!validpinNum(pinNum)) {
            System.out.println("Invalid pin, please re-enter:");
            pinNum = sc.nextLine();
        }
        System.out.println("Does your card have an expiration date? (Y/N)");
        char choice = sc.next().charAt(0);
        while (!validChoice(choice)) {
            System.out.println("Invalid input, please re-enter:");
            choice = sc.next().charAt(0);
        }
        boolean expires;
        if (choice == 'Y' || choice == 'y') {
            expires = true;
        } else {
            expires = false;
        }
        if (expires == true) {
            String month = sc.nextLine();
            if (!validMonth(month)) {
                while (!validMonth(month)) {
                    System.out.println("Enter the two digit expiration month:");
                    month = sc.nextLine();
                }
            }
            System.out.println("Enter the 4 digit expiration year:");
            String year = sc.nextLine();
            while (!validYear(year)) {
                System.out.println("Invalid year");
                System.out.println("Please re-enter");
                year = sc.nextLine();
            }

            int Month = Integer.parseInt(month);
            int Year = Integer.parseInt(year);
            LocalDate d = LocalDate.of(Year, Month, 01);
            while (!validCard(d)) {
                System.out.println("Invalid expiration date");
                System.out.println("Please re-enter the expiration date");
                System.out.println("Enter the two digit expiration month:");
                month = sc.nextLine();
                while (!validMonth(month)) {
                    System.out.println("Invalid month");
                    System.out.println("Please re-enter");
                    month = sc.nextLine();
                }
                System.out.println("Enter the 4 digit expiration year:");
                year = sc.nextLine();
                while (!validYear(year)) {
                    System.out.println("Invalid year");
                    System.out.println("Please re-enter");
                    year = sc.nextLine();
                }

                Month = Integer.parseInt(month);
                Year = Integer.parseInt(year);
                d = LocalDate.of(Year, Month, 01);
            }
            Date d2 = ConvertDate(d);
            Payment p = new Payment(cardnum, pinNum, d2);
            return p;
        } else {
            Payment p = new Payment(cardnum, pinNum);
            return p;
        }
    }

    public static boolean validMonth(String month) {
        try {
            int Month = Integer.parseInt(month);
            if (Month > 12 || Month < 0) {
                return false;
            }
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static boolean validYear(String year) {
        try {
            int Year = Integer.parseInt(year);
            if (Year > 9999 || Year < 1000) {
                return false;
            }
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static boolean validCard(LocalDate d) {//This will check if the entered expiration date is valid
        LocalDate today = LocalDate.now();
        Date d1 = ConvertDate(today);//Converting LocalDate to Date for comaparison
        Date d2 = ConvertDate(d);
        if (d2.before(d1)) {
            return false;
        }
        return true;
    }

    public static Date ConvertDate(LocalDate d) {
        return java.sql.Date.valueOf(d);
    }
}
