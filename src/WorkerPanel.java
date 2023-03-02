import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public class WorkerPanel {
    static LocalDate currentDate = LocalDate.now();
    static Scanner scanner = new Scanner(System.in);

    public static void home(int identyfikatorDATABASE, String hasloDATABASE, String nameDATABASE, int idKontaDATABASE) {

        while (true) {
            System.out.println(" ");
            System.out.println("||¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯||");
            System.out.println("||   WIKTORtechnic   ||");
            System.out.println("||___________________||");
            System.out.println("||----" + currentDate + "-----||");
            System.out.println("||___________________||");
            System.out.println("   Dzień dobry " + nameDATABASE);
            System.out.println("||¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯||");
            System.out.println("||-> 1. Harmonogram  ||");
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
                    System.out.println(" ");
                    System.out.println("||¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯||");
                    System.out.println("||   Harmonogram   ||");
                    System.out.println("||_________________||");
                    System.out.println(" ");
                    try {

                        ResultSet resultSelect = QueryExecutor.executeSelect("SELECT zmiany.nazwa, harmonogram_pracy.data_od, harmonogram_pracy.data_do, zmiany.godzina_rozpoczecia, zmiany.godzina_zakonczenia FROM `harmonogram_pracy` INNER JOIN konta ON harmonogram_pracy.id_konta = konta.id_konta INNER JOIN zmiany ON harmonogram_pracy.id_zmiany = zmiany.id_zmiany WHERE harmonogram_pracy.id_konta = '"+idKontaDATABASE+"'");

                        while (resultSelect.next()) {

                            String table1 = resultSelect.getString("nazwa");
                            String table2 = resultSelect.getString("data_od");
                            String table3 = resultSelect.getString("data_do");
                            String table4 = resultSelect.getString("godzina_rozpoczecia");
                            String table5 = resultSelect.getString("godzina_zakonczenia");

                            System.out.println("-----------------------------------------------------------------------------------");
                            System.out.println("Zmiana: " + table1 + " || W dniach: " + table2 + " - " + table3 + " || Godziny: " + table4 + " - " + table5);

                        }

                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("-----------------------------------------------------------------------------------");
                    System.out.println(" ");
                    System.out.println("1. Wróć");
                    Settings.safeBack();

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
                    System.out.println(" ");
                    System.out.println("Wylogowywanie");
                    System.out.println(" ");
                    StartPage.home();
                }
                default -> System.out.println("Nieprawidłowy wybór. Spróbuj ponownie.");
            }
        }
    }
}
