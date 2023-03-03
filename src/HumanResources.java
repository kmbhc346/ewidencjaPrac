import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.InputMismatchException;
import java.util.Scanner;

public class HumanResources {
    static Scanner scanner = new Scanner(System.in);

    static LocalTime minTime = LocalTime.of(0, 0);
    static LocalTime maxTime = LocalTime.of(23, 59);


    public static void home(int identyfikatorDATABASE, String hasloDATABASE, String nameDATABASE) {
        while (true) {
            System.out.println(" ");
            System.out.println("||¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯||");
            System.out.println("||     WIKTORtechnic    ||");
            System.out.println("||         Zmiany       ||");
            System.out.println("||______________________||");
            System.out.println("||¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯||");
            System.out.println("||-> 1. Wyświetl zmiany ||");
            System.out.println("||-> 2. Dodaj zmianę    ||");
            System.out.println("||-> 3. Edytuj godziny  ||");
            System.out.println("||                      ||");
            System.out.println("||-> 4. Wróć            ||");
            System.out.println("||______________________||");

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
                    showShifts();
                    System.out.println(" ");
                    System.out.println("1. Wróć");
                    Settings.safeBack();
                }
                case 2 -> {
                    System.out.println(" ");
                    addShift();
                    System.out.println(" ");
                    System.out.println("1. Wróć");
                    Settings.safeBack();
                }
                case 3 -> {
                    System.out.println(" ");
                    updateShifts();
                    System.out.println(" ");
                    System.out.println("1. Wróć");
                    Settings.safeBack();
                }
                case 4 -> AdminPanel.humanResources(identyfikatorDATABASE, hasloDATABASE, nameDATABASE);
                default -> System.out.println("Nieprawidłowy wybór. Spróbuj ponownie.");
            }
        }
    }

    public static void showShifts() {
        try {
            ResultSet resultSelect = QueryExecutor.executeSelect("SELECT * FROM `zmiany`");

            while (resultSelect.next()) {

                String table1 = resultSelect.getString("id_zmiany");
                String table2 = resultSelect.getString("nazwa");
                String table3 = resultSelect.getString("godzina_rozpoczecia");
                String table4 = resultSelect.getString("godzina_zakonczenia");

                System.out.println("----------------------------------------------------------------------");
                System.out.println("Nr. zmiany: " + table1 + " || Zmiana: " + table2 + " || Godziny: " + table3 + " - " + table4);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("----------------------------------------------------------------------");
    }

    private static void addShift() {
        scanner.nextLine();
        showShifts();
        System.out.println(" ");

        System.out.print("Podaj nazwę zmiany: ");
        String shiftName = scanner.nextLine();
        System.out.print("Godzina rozpoczęcia (hh:mm): ");
        String startHour = scanner.nextLine();
        LocalTime startTime = null;
        while (startTime == null) {
            try {
                startTime = LocalTime.parse(startHour);
                if (startTime.isBefore(minTime) || startTime.isAfter(maxTime)) {
                    System.out.print("Nieprawidłowy zakres godziny, podaj w zakresie " + minTime + " - " + maxTime + ": ");
                    startHour = scanner.nextLine();
                    startTime = null;
                }
            } catch (Exception e) {
                System.out.print("Nieprawidłowy format godziny, podaj w formacie hh:mm: ");
                startHour = scanner.nextLine();
            }
        }

        System.out.print("Godzina zakończenia (hh:mm): ");
        String endHour = scanner.nextLine();
        LocalTime endTime = null;
        while (endTime == null) {
            try {
                endTime = LocalTime.parse(endHour);
                if (endTime.isBefore(minTime) || endTime.isAfter(maxTime)) {
                    System.out.print("Nieprawidłowy zakres godziny, podaj w zakresie " + minTime + " - " + maxTime + ": ");
                    endHour = scanner.nextLine();
                    endTime = null;
                }
            } catch (Exception e) {
                System.out.print("Nieprawidłowy format godziny, podaj w formacie hh:mm: ");
                endHour = scanner.nextLine();
            }
        }

        QueryExecutor.executeQuery("INSERT INTO `zmiany`(`nazwa`, `godzina_rozpoczecia`, `godzina_zakonczenia`) VALUES ('" + shiftName + "','" + startHour + "','" + endHour + "')");

        System.out.println(" ");
        System.out.println("Dodano zmianę");
    }

    private static void updateShifts() {
        showShifts();
        System.out.println(" ");


        int shiftNumber;
        while (true) {
            System.out.print("Podaj nr zmiany: ");
            try {
                shiftNumber = scanner.nextInt();

                try {
                    ResultSet resultSelect2 = QueryExecutor.executeSelect("SELECT * FROM `zmiany` WHERE `id_zmiany` = '" + shiftNumber + "'");

                    if (resultSelect2.next()) {
                        scanner.nextLine();
                        System.out.print("Godzina rozpoczęcia (hh:mm): ");
                        String startHour = scanner.nextLine();
                        LocalTime startTime = null;
                        while (startTime == null) {
                            try {
                                startTime = LocalTime.parse(startHour);
                                if (startTime.isBefore(minTime) || startTime.isAfter(maxTime)) {
                                    System.out.print("Nieprawidłowy zakres godziny, podaj w zakresie " + minTime + " - " + maxTime + ": ");
                                    startHour = scanner.nextLine();
                                    startTime = null;
                                }
                            } catch (Exception e) {
                                System.out.print("Nieprawidłowy format godziny, podaj w formacie hh:mm: ");
                                startHour = scanner.nextLine();
                            }
                        }

                        System.out.print("Godzina zakończenia (hh:mm): ");
                        String endHour = scanner.nextLine();
                        LocalTime endTime = null;
                        while (endTime == null) {
                            try {
                                endTime = LocalTime.parse(endHour);
                                if (endTime.isBefore(minTime) || endTime.isAfter(maxTime)) {
                                    System.out.print("Nieprawidłowy zakres godziny, podaj w zakresie " + minTime + " - " + maxTime + ": ");
                                    endHour = scanner.nextLine();
                                    endTime = null;
                                }
                            } catch (Exception e) {
                                System.out.print("Nieprawidłowy format godziny, podaj w formacie hh:mm: ");
                                endHour = scanner.nextLine();
                            }
                        }

                        QueryExecutor.executeQuery("UPDATE `zmiany` SET `godzina_rozpoczecia`='"+startHour+"',`godzina_zakonczenia`='"+endHour+"' WHERE `id_zmiany` = '"+shiftNumber+"' ");

                        System.out.println(" ");
                        System.out.println("Wprowadzono zmiany");

                    } else {
                        System.out.println("Podana zmiana nie istnieje.");
                        System.out.println(" ");
                        updateShifts();
                    }

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Wprowadzono nieprawidłowe dane, spróbuj jeszcze raz.");
                scanner.next();
            }
        }

    }
}
