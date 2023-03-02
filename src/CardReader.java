import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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

                companyMenu(identyfikatorUSER);

            } else {
                System.out.println("Niepoprawny numer Identyfikatora");
                cardRead();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void companyMenu(int identyfikatorUSER) {
        System.out.println(" ");
        System.out.println("||¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯||");
        System.out.println("||      WIKTORtechnic      ||");
        System.out.println("||_________________________||");

        try {
            ResultSet result = QueryExecutor.executeSelect("SELECT * FROM `konta` WHERE identyfikator='" + identyfikatorUSER + "'");
            if (result.next()) {

                int idKonta = result.getInt("id_konta");

                try {
                    ResultSet result2 = QueryExecutor.executeSelect("SELECT * FROM `rejestrowane_godziny` WHERE `id_konta` = '" + idKonta + "'  AND `godzina_zakonczenia` is null ORDER BY `rejestrowane_godziny`.`id` DESC");

                    if (result2.next()) {
                        boolean godzina_zakonczenia = result2.getBoolean("godzina_zakonczenia");

                        if (!godzina_zakonczenia) {
                            System.out.println("|| 2. Zakończ pracę        ||");
                            System.out.println("|| 3. Wyjdź                ||");
                            System.out.println(" ¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯ ");

                            int choice;
                            while (true) {
                                System.out.print("Wybierz opcję: ");
                                try {
                                    choice = scanner.nextInt();
                                    scanner.nextLine();

                                    switch (choice) {
                                        case 2 -> {
                                            try {
                                                ResultSet result3 = QueryExecutor.executeSelect("SELECT * FROM `rejestrowane_godziny` WHERE id_konta='" + idKonta + "' ORDER BY `rejestrowane_godziny`.`id` DESC");

                                                if (result3.next()) {
                                                    Time startHour = result3.getTime("godzina_rozpoczecia");

                                                    LocalTime czas = LocalTime.now();
                                                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

                                                    QueryExecutor.executeQuery("UPDATE `rejestrowane_godziny` SET `godzina_zakonczenia`='" + czas.format(formatter) + "' WHERE `id_konta`= '" + idKonta + "' AND `godzina_rozpoczecia` = '" + startHour + "'");
                                                    System.out.println("Wprowadzono dane do systemu.");
                                                    StartPage.home();
                                                }
                                            } catch (SQLException e) {
                                                throw new RuntimeException(e);
                                            }
                                        }
                                        case 3 -> StartPage.home();
                                        default -> {
                                            System.out.println("Nieprawidłowy wybór. Spróbuj ponownie.");
                                            companyMenu(identyfikatorUSER);
                                        }
                                    }
                                    break;
                                } catch (InputMismatchException e) {
                                    System.out.println("Nierozpoznana akcja");
                                    scanner.next();
                                }
                            }
                        }
                    } else {
                        System.out.println("|| 1. Rozpocznij pracę     ||");
                        System.out.println("|| 3. Wyjdź                ||");
                        System.out.println(" ¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯ ");

                        int choice;
                        while (true) {
                            System.out.print("Wybierz opcję: ");
                            try {
                                choice = scanner.nextInt();
                                scanner.nextLine();

                                switch (choice) {
                                    case 1 -> {
                                        QueryExecutor.executeQuery("INSERT INTO `rejestrowane_godziny`(`id_konta`,`godzina_zakonczenia`) VALUES ('" + idKonta + "', NULL)");
                                        System.out.println("Wprowadzono dane do systemu.");
                                        companyMenu(identyfikatorUSER);
                                    }
                                    case 3 -> StartPage.home();
                                    default -> {
                                        System.out.println("Nieprawidłowy wybór. Spróbuj ponownie.");
                                        companyMenu(identyfikatorUSER);
                                    }
                                }
                                break;
                            } catch (InputMismatchException e) {
                                System.out.println("Nierozpoznana akcja");
                                scanner.next();
                            }
                        }
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else {
                cardRead();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
