import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

public class WorkSchedule {

    static Scanner scanner = new Scanner(System.in);

    public static void home(int identyfikatorDATABASE, String hasloDATABASE, String nameDATABASE) {
        while (true) {
            System.out.println(" ");
            System.out.println("||¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯||");
            System.out.println("||       WIKTORtechnic       ||");
            System.out.println("||        Harmonogram        ||");
            System.out.println("||___________________________||");
            System.out.println("||¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯||");
            System.out.println("||-> 1. Sprawdź harmonogramy ||");
            System.out.println("||-> 2. Dodaj harmonogram    ||");
            System.out.println("||-> 3. Edytuj harmonogram   ||");
            System.out.println("||-> 4. Sprawdź pracownika   ||");
            System.out.println("||                           ||");
            System.out.println("||-> 5. Wróć                 ||");
            System.out.println("||___________________________||");

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
                    showWorkSchedule();
                    System.out.println(" ");
                    System.out.println("1. Wróć");
                    Settings.safeBack();
                }
                case 2 -> {
                    System.out.println(" ");
                    addWorkSchedule();
                    System.out.println(" ");
                    System.out.println("1. Wróć");
                    Settings.safeBack();
                }
                case 3 -> {
                    System.out.println(" ");
                    editWorkSchedule();
                    System.out.println(" ");
                    System.out.println("1. Wróć");
                    Settings.safeBack();
                }
                case 4 -> {
                    System.out.println(" ");
                    checkWorker();
                    System.out.println(" ");
                    System.out.println("1. Wróć");
                    Settings.safeBack();
                }
                case 5 -> AdminPanel.humanResources(identyfikatorDATABASE, hasloDATABASE, nameDATABASE);
                default -> System.out.println("Nieprawidłowy wybór. Spróbuj ponownie.");
            }
        }
    }

    private static void showWorkSchedule() {
        HumanResources.showShifts();

        int shiftNumber;
        while (true) {
            System.out.println(" ");
            System.out.print("Nr. sprawdzanej zmiany: ");
            try {
                shiftNumber = scanner.nextInt();
                System.out.println(" ");
                System.out.println("----------------------------------------------------------------------------------------");
                try {
                    ResultSet resultSelect = QueryExecutor.executeSelect("SELECT konta.imie, konta.nazwisko, zmiany.nazwa, harmonogram_pracy.data_od, harmonogram_pracy.data_do, zmiany.godzina_rozpoczecia, zmiany.godzina_zakonczenia FROM `harmonogram_pracy` INNER JOIN konta ON harmonogram_pracy.id_konta = konta.id_konta INNER JOIN zmiany ON harmonogram_pracy.id_zmiany = zmiany.id_zmiany WHERE harmonogram_pracy.id_zmiany = '" + shiftNumber + "' ");

                    while (resultSelect.next()) {

                        String table1 = resultSelect.getString("imie");
                        String table2 = resultSelect.getString("nazwisko");
                        String table3 = resultSelect.getString("nazwa");
                        String table4 = resultSelect.getString("data_od");
                        String table5 = resultSelect.getString("data_do");
                        String table6 = resultSelect.getString("godzina_rozpoczecia");
                        String table7 = resultSelect.getString("godzina_zakonczenia");

                        System.out.println("Zmiana: " + table3 + " || " + table1 + " " + table2 + " || Od: " + table4 + " do: " + table5 + " || " + table6 + " - " + table7);
                    }

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("----------------------------------------------------------------------------------------");
                break;
            } catch (InputMismatchException e) {
                System.out.println("Wprowadzono nieprawidłowe dane, spróbuj jeszcze raz.");
                scanner.next();
            }
        }
    }

    private static void addWorkSchedule() {
        System.out.println(" ");
        EmployeeManagement.showWorkers();
        System.out.println(" ");
        HumanResources.showShifts();
        System.out.println(" ");


        int accountID;
        while (true) {
            System.out.print("Podaj id konta: ");
            try {
                accountID = scanner.nextInt();

                try {
                    ResultSet resultSelect = QueryExecutor.executeSelect("SELECT `id_konta`, `stanowisko` FROM `konta` WHERE `id_konta` = '" + accountID + "'");

                    if (resultSelect.next()) {
                        int shiftID;
                        while (true) {
                            System.out.print("Podaj nr zmiany: ");
                            try {
                                shiftID = scanner.nextInt();

                                try {
                                    ResultSet resultSelect2 = QueryExecutor.executeSelect("SELECT * FROM `zmiany` WHERE `id_zmiany` = '" + shiftID + "'");

                                    if (resultSelect2.next()) {
                                        scanner.nextLine();
                                        System.out.print("Początek (rrrr-mm-dd): ");
                                        String startDate = scanner.nextLine();
                                        LocalDate start = null;

                                        while (start == null) {
                                            if (startDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
                                                try {
                                                    start = LocalDate.parse(startDate);
                                                } catch (Exception e) {
                                                    System.out.print("Nieprawidłowa data, podaj w formacie rrrr-mm-dd: ");
                                                    startDate = scanner.nextLine();
                                                }
                                            } else {
                                                System.out.print("Nieprawidłowy format daty, podaj w formacie rrrr-mm-dd: ");
                                                startDate = scanner.nextLine();
                                            }
                                        }

                                        System.out.print("Koniec (rrrr-mm-dd): ");
                                        String endDate = scanner.nextLine();
                                        LocalDate end = null;

                                        while (end == null) {
                                            if (endDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
                                                try {
                                                    end = LocalDate.parse(endDate);
                                                } catch (Exception e) {
                                                    System.out.print("Nieprawidłowa data, podaj w formacie rrrr-mm-dd: ");
                                                    endDate = scanner.nextLine();
                                                }
                                            } else {
                                                System.out.print("Nieprawidłowy format daty, podaj w formacie rrrr-mm-dd: ");
                                                endDate = scanner.nextLine();
                                            }
                                        }

                                        QueryExecutor.executeQuery("INSERT INTO `harmonogram_pracy`(`id_konta`, `id_zmiany`, `data_od`, `data_do`) VALUES ('" + accountID + "','" + shiftID + "','" + startDate + "','" + endDate + "')");
                                        System.out.println(" ");
                                        System.out.println("Dodano harmonogram dla pracownika");

                                    } else {
                                        System.out.println("Podana zmiana nie istnieje.");
                                        System.out.println(" ");
                                        addWorkSchedule();
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

                    } else {
                        System.out.println("Nie ma osoby o takim id konta.");
                        System.out.println(" ");
                        addWorkSchedule();
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

    private static void editWorkSchedule() {
        System.out.println(" ");
        EmployeeManagement.showWorkers();
        System.out.println(" ");

        int accountID;
        while (true) {
            System.out.print("Podaj id konta: ");
            try {
                accountID = scanner.nextInt();
                try {
                    ResultSet resultSelect = QueryExecutor.executeSelect("SELECT `id_konta`, `stanowisko` FROM `konta` WHERE `id_konta` = '" + accountID + "'");

                    if (resultSelect.next()) {

                        System.out.println(" ");
                        System.out.println("----------------------------------------------------------------------------------------");
                        try {
                            ResultSet resultSelect3 = QueryExecutor.executeSelect("SELECT * FROM `harmonogram_pracy` WHERE `id_konta` ='" + accountID + "' ");

                            while (resultSelect3.next()) {

                                String table1 = resultSelect3.getString("id_harmonogramu");
                                String table2 = resultSelect3.getString("id_konta");
                                String table3 = resultSelect3.getString("id_zmiany");
                                String table4 = resultSelect3.getString("data_od");
                                String table5 = resultSelect3.getString("data_do");

                                System.out.println("ID: " + table1 +" || Id konta: " + table2 + " || Id zmiany: "+ table3 + " || W dniach od: " +table4 + " do: " + table5 );
                            }

                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        System.out.println("----------------------------------------------------------------------------------------");

                        int scheduleNumber;
                        while (true) {
                            System.out.println(" ");
                            System.out.print("Podaj ID harmonogramu, kóry chcesz edytować: ");
                            try {
                                scheduleNumber = scanner.nextInt();
                                System.out.println(" ");
                                try {
                                    ResultSet resultSelect4 = QueryExecutor.executeSelect("SELECT * FROM `harmonogram_pracy` WHERE `id_harmonogramu` ='" + scheduleNumber + "' ");

                                    if (resultSelect4.next()) {
                        int shiftID;
                                        while (true) {
                                            HumanResources.showShifts();
                                            System.out.println(" ");

                                            System.out.print("Podaj nr nowej zmiany: ");
                                            try {
                                                shiftID = scanner.nextInt();

                                                try {
                                                    ResultSet resultSelect2 = QueryExecutor.executeSelect("SELECT * FROM `zmiany` WHERE `id_zmiany` = '" + shiftID + "'");

                                                    if (resultSelect2.next()) {
                                                        scanner.nextLine();
                                                        System.out.print("Nowy początek (rrrr-mm-dd): ");
                                                        String startDate = scanner.nextLine();
                                                        LocalDate start = null;

                                                        while (start == null) {
                                                            if (startDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
                                                                try {
                                                                    start = LocalDate.parse(startDate);
                                                                } catch (Exception e) {
                                                                    System.out.print("Nieprawidłowa data, podaj w formacie rrrr-mm-dd: ");
                                                                    startDate = scanner.nextLine();
                                                                }
                                                            } else {
                                                                System.out.print("Nieprawidłowy format daty, podaj w formacie rrrr-mm-dd: ");
                                                                startDate = scanner.nextLine();
                                                            }
                                                        }

                                                        System.out.print("Nowy koniec (rrrr-mm-dd): ");
                                                        String endDate = scanner.nextLine();
                                                        LocalDate end = null;

                                                        while (end == null) {
                                                            if (endDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
                                                                try {
                                                                    end = LocalDate.parse(endDate);
                                                                } catch (Exception e) {
                                                                    System.out.print("Nieprawidłowa data, podaj w formacie rrrr-mm-dd: ");
                                                                    endDate = scanner.nextLine();
                                                                }
                                                            } else {
                                                                System.out.print("Nieprawidłowy format daty, podaj w formacie rrrr-mm-dd: ");
                                                                endDate = scanner.nextLine();
                                                            }
                                                        }
                                                        QueryExecutor.executeQuery("UPDATE `harmonogram_pracy` SET `id_zmiany`='" + shiftID + "',`data_od`='" + startDate + "',`data_do`='" + endDate + "'  WHERE `id_harmonogramu` = '"+scheduleNumber+"' ");
                                                        System.out.println(" ");
                                                        System.out.println("Zaktualizowano harmonogram dla pracownika");

                                                    } else {
                                                        System.out.println("Podana zmiana nie istnieje.");
                                                        System.out.println(" ");
                                                        addWorkSchedule();
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

                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                                break;
                            } catch (InputMismatchException e) {
                                System.out.println("Wprowadzono nieprawidłowe dane, spróbuj jeszcze raz.");
                            }
                        }

                    } else {
                        System.out.println("Nie ma osoby o takim id konta.");
                        System.out.println(" ");
                        addWorkSchedule();
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

    private static void checkWorker() {
        System.out.println(" ");
        EmployeeManagement.showWorkers();
        System.out.println(" ");

        int accountID;
        while (true) {
            System.out.print("Podaj id konta: ");
            try {
                accountID = scanner.nextInt();

                try {
                    ResultSet resultSelect = QueryExecutor.executeSelect("SELECT `id_konta`, `stanowisko` FROM `konta` WHERE `id_konta` = '" + accountID + "'");

                    if (resultSelect.next()) {

                        String stanowisko = resultSelect.getString("stanowisko");

                        if (stanowisko.equals("pracownik")) {
                            try {
                                ResultSet resultSelect2 = QueryExecutor.executeSelect("SELECT konta.imie, konta.nazwisko, zmiany.nazwa, harmonogram_pracy.data_od, harmonogram_pracy.data_do, rejestrowane_godziny.data, rejestrowane_godziny.godzina_rozpoczecia, rejestrowane_godziny.godzina_zakonczenia FROM `harmonogram_pracy` INNER JOIN konta ON harmonogram_pracy.id_konta = konta.id_konta INNER JOIN zmiany ON harmonogram_pracy.id_zmiany = zmiany.id_zmiany INNER JOIN rejestrowane_godziny ON harmonogram_pracy.id_konta = rejestrowane_godziny.id_konta WHERE harmonogram_pracy.id_konta = '" + accountID + "'");

                                while (resultSelect2.next()) {

                                    String table1 = resultSelect2.getString("imie");
                                    String table2 = resultSelect2.getString("nazwisko");
                                    String table3 = resultSelect2.getString("nazwa");
                                    String table4 = resultSelect2.getString("data_od");
                                    String table5 = resultSelect2.getString("data_do");
                                    String table6 = resultSelect2.getString("data");
                                    String table7 = resultSelect2.getString("godzina_rozpoczecia");
                                    String table8 = resultSelect2.getString("godzina_zakonczenia");

                                    System.out.println("----------------------------------------------------------------------");
                                    System.out.println("Dane: " + table1 + " " + table2 + " Zmiana: " + table3);
                                    System.out.println("Powinien pracować w dniach od: " + table4 + " do: " + table5);
                                    System.out.println("Pracował w dniu: " + table6 + " Wejście: " + table7 + " Wyjście: " + table8);
                                }

                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                            System.out.println("----------------------------------------------------------------------");
                        } else {
                            System.out.println("Konto administratora");
                            checkWorker();
                        }

                    } else {
                        System.out.println("Nie ma osoby o takim id konta.");
                        System.out.println(" ");
                        checkWorker();
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
