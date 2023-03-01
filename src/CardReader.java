import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CardReader {
    static Scanner scanner = new Scanner(System.in);
    public static void cardRead() {
        try {
            System.out.println(" ");
            System.out.println("||¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯||");
            System.out.println("||   WIKTORtechnic   ||");
            System.out.println("||___________________||");
            System.out.println(" ");

            int identyfikatorUSER;
            do {
                System.out.print("Podaj nr. identyfikatora: ");
                String input = scanner.nextLine();
                if (input.matches("\\d{1,10}")) {
                    identyfikatorUSER = Integer.parseInt(input);
                    break;
                }
                System.out.println("Numer identyfikatora musi zawierać od 1 do 10 cyfr.");
            } while (true);

            ResultSet result = QueryExecutor.executeSelect("SELECT * FROM `konta` WHERE identyfikator='" + identyfikatorUSER + "'");

            if (result.next()) {

                companyMenu();

            } else {
                System.out.println("Niepoprawny numer Identyfikatora");
                cardRead();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void companyMenu() {
        System.out.println(" ");
        System.out.println("||¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯||");
        System.out.println("||      WIKTORtechnic      ||");
        System.out.println("||_________________________||");
        System.out.println("|| 1. Rozpocznij pracę     ||");
        System.out.println("|| 2. Zakończ pracę        ||");
        System.out.println(" ¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯ ");

        int choice;
        while (true) {
            System.out.print("Wybierz opcję: ");
            try {
                choice = scanner.nextInt();
                switch (choice) {
                    case 1 -> System.out.println("warunek1");
                    case 2 -> System.out.println("warunek2");
                    default -> {
                        System.out.println("Nieprawidłowy wybór. Spróbuj ponownie.");
                        companyMenu();
                    }
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Nierozpoznana akcja");
                scanner.next();
            }
        }
    }


}
