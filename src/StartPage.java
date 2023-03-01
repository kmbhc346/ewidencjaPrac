import java.util.InputMismatchException;
import java.util.Scanner;

public class StartPage {
    static Scanner scanner = new Scanner(System.in);

    public static void home() {
        System.out.println("||¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯||");
        System.out.println("||   Firma WIKTORtechnic   ||");
        System.out.println("||_________________________||");
        System.out.println("||     1. Czytnik kart     ||");
        System.out.println("|| 2. Logowanie do systemu ||");
        System.out.println(" ¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯ ");

        int choice;
        while (true) {
            System.out.print("Wybierz opcję: ");
            try {
                choice = scanner.nextInt();
                switch (choice) {
                    case 1 -> CardReader.cardRead();
                    case 2 -> Login.signIn();
                    default -> {
                        System.out.println("Nieprawidłowy wybór. Spróbuj ponownie.");
                        home();
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
