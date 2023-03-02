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
            System.out.println("||-> 2. Ustawienia   ||");
            System.out.println("||-> 3. Wyloguj      ||");
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
                    //                       TO IDZIE DO ADMINISTRATORA  ResultSet resultSelect = QueryExecutor.executeSelect("SELECT konta.imie, konta.nazwisko, zmiany.nazwa, harmonogram_pracy.data_od, harmonogram_pracy.data_do, rejestrowane_godziny.data, rejestrowane_godziny.godzina_rozpoczecia, rejestrowane_godziny.godzina_zakonczenia FROM `harmonogram_pracy` INNER JOIN konta ON harmonogram_pracy.id_konta = konta.id_konta INNER JOIN zmiany ON harmonogram_pracy.id_zmiany = zmiany.id_zmiany INNER JOIN rejestrowane_godziny ON harmonogram_pracy.id_konta = rejestrowane_godziny.id_konta WHERE harmonogram_pracy.id_konta = '"+idKontaDATABASE+"'");
                }
                case 2 -> {
                    System.out.println("||¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯||");
                    System.out.println("||   Ustawienia   ||");
                    System.out.println("||________________||");

                    Settings.changePassword(identyfikatorDATABASE, hasloDATABASE );

                    System.out.println(" ");
                    System.out.println("1. Wróć");
                    Settings.safeBack();
                }
                case 3 -> {
                    System.out.println("Wylogowywanie");
                    StartPage.home();
                }
                default -> System.out.println("Nieprawidłowy wybór. Spróbuj ponownie.");
            }
        }
    }
}
