import java.util.InputMismatchException;
import java.util.Scanner;

public class Settings {
    static Scanner scanner = new Scanner(System.in);

    public static void changePassword(int identyfikatorDATABASE, String hasloDATABASE) {
        System.out.println(" ");
        System.out.println("||¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯||");
        System.out.println("||   Zmień hasło   ||");
        System.out.println("||_________________||");
        System.out.println(" ");

        System.out.print("Podaj obecne hasło: ");
        String stareHaslo = scanner.nextLine();

        if (stareHaslo.equals(hasloDATABASE)) {
            System.out.println("(Hasła powinny być różne)");
            System.out.print("Podaj nowe hasło: ");
            String noweHaslo = scanner.nextLine();

            if (!stareHaslo.equals(noweHaslo)) {
                String zapytanie = "UPDATE `konta` SET `haslo`='"+noweHaslo+"' WHERE identyfikator ='"+identyfikatorDATABASE+"'";
                QueryExecutor.executeQuery(zapytanie);

                System.out.println(" ");
                System.out.println("Zmieniono hasło");
                System.out.println("Zostaniesz wylogowany...");
                clearScreenWait();
                Login.signIn();
                System.out.println(" ");
            } else {
                System.out.println("Hasła nie mogą być takie same");
                changePassword(identyfikatorDATABASE, hasloDATABASE);
            }

        } else {
            System.out.println("Błędne hasło, podaj nowe");
            changePassword(identyfikatorDATABASE, hasloDATABASE);
        }

    }

    public static void safeBack() {
        while (true) {
            System.out.print("Wybierz opcje: ");
            try {
                int option = scanner.nextInt();
                scanner.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Nierozpoznana akcja");
                scanner.next();
            }
        }
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void clearScreenWait() {
        try {
            Thread.sleep(1500);
            System.out.print("\033[H\033[2J");
            System.out.flush();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
