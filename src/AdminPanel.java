import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AdminPanel {
    static LocalDate currentDate = LocalDate.now();
    static Scanner scanner = new Scanner(System.in);

    public static void home(int identyfikatorDATABASE, String hasloDATABASE, String nameDATABASE) {

        while (true) {
            System.out.println(" ");
            System.out.println("||¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯||");
            System.out.println("||   WIKTORtechnic   ||");
            System.out.println("||___________________||");
            System.out.println("||----" + currentDate + "-----||");
            System.out.println("||___________________||");
            System.out.println("  Dzień dobry " + nameDATABASE);
            System.out.println("||¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯||");
            System.out.println("||-> 1. Kadry        ||");
            System.out.println("||-> 2. Pracownicy   ||");
            System.out.println("||-> 3. Ustawienia   ||");
            System.out.println("||                   ||");
            System.out.println("||-> 4. Wyloguj      ||");
            System.out.println("||___________________||");

            int choice;
            while (true) {
                System.out.print("Wybierz opcje: ");
                try {
                    choice = scanner.nextInt();
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Wybierz odpowiednią opcję.");
                    scanner.next();
                }
            }

            switch (choice) {
                case 1 -> {
                    System.out.println(" ");
                    humanResources(identyfikatorDATABASE, hasloDATABASE, nameDATABASE);
                    System.out.println(" ");
                    System.out.println("1. Wróć");
                    Settings.safeBack();
                }
                case 2 -> {
                    System.out.println(" ");
                    EmployeeManagement.employeeManagement(identyfikatorDATABASE, hasloDATABASE, nameDATABASE);
                    System.out.println(" ");
                    System.out.println("1. Wróć");
                    Settings.safeBack();
                }
                case 3 -> {
                    System.out.println(" ");
                    System.out.println("||¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯||");
                    System.out.println("||   Ustawienia   ||");
                    System.out.println("||________________||");

                    Settings.changePassword(identyfikatorDATABASE, hasloDATABASE);

                    System.out.println(" ");
                    System.out.println("1. Wróć");
                    Settings.safeBack();
                }
                case 4 -> {
                    System.out.println("Wylogowywanie");
                    StartPage.home();
                }
                default -> System.out.println("Nieprawidłowy wybór. Spróbuj ponownie.");
            }
        }
    }

    public static void humanResources(int identyfikatorDATABASE, String hasloDATABASE, String nameDATABASE) {
        while (true) {
            System.out.println(" ");
            System.out.println("||¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯||");
            System.out.println("||   WIKTORtechnic   ||");
            System.out.println("||       Kadry       ||");
            System.out.println("||___________________||");
            System.out.println("||¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯||");
            System.out.println("||-> 1. Zmiany       ||");
            System.out.println("||-> 2. Harmonogram  ||");
            System.out.println("||                   ||");
            System.out.println("||-> 3. Wróć         ||");
            System.out.println("||___________________||");

            int choice;
            while (true) {
                System.out.print("Wybierz opcje: ");
                try {
                    choice = scanner.nextInt();
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Wybierz odpowiednią opcję.");
                    scanner.next();
                }
            }

            switch (choice) {
                case 1 -> {
                    System.out.println(" ");
                    HumanResources.home(identyfikatorDATABASE, hasloDATABASE, nameDATABASE);
                    System.out.println(" ");
                    System.out.println("1. Wróć");
                    Settings.safeBack();
                }
                case 2 -> {
                    System.out.println(" ");
                    WorkSchedule.home(identyfikatorDATABASE, hasloDATABASE, nameDATABASE);
                    System.out.println(" ");
                    System.out.println("1. Wróć");
                    Settings.safeBack();
                }
                case 3 -> home(identyfikatorDATABASE, hasloDATABASE, nameDATABASE);
                default -> System.out.println("Nieprawidłowy wybór. Spróbuj ponownie.");
            }
        }
    }

}
