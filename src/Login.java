import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Login {
    static Scanner scanner = new Scanner(System.in);
    public static void signIn() {
        try {
            System.out.println(" ");
            System.out.println("||¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯||");
            System.out.println("||    Oddział Kadr    ||");
            System.out.println("||____________________||");
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

            System.out.print("Podaj hasło: ");
            String hasloUSER = scanner.nextLine();

            ResultSet result = QueryExecutor.executeSelect("SELECT * FROM `konta` WHERE identyfikator='" + identyfikatorUSER + "'");

            if (result.next()) {
                int idKontaDATABASE = result.getInt("id_konta");
                int identyfikatorDATABASE = result.getInt("identyfikator");
                String hasloDATABASE = result.getString("haslo");
                String nameDATABASE = result.getString("imie");
                String positionDATABASE = result.getString("stanowisko");

                if (identyfikatorUSER == identyfikatorDATABASE && hasloUSER.equals(hasloDATABASE)) {
                    switch (positionDATABASE) {
                        case "administrator" -> AdminPanel.home(identyfikatorDATABASE, hasloDATABASE,nameDATABASE);

                        case "pracownik" -> WorkerPanel.home(identyfikatorDATABASE, hasloDATABASE, nameDATABASE, idKontaDATABASE);
                    }
                } else {
                    System.out.println("Niepoprawne hasło");
                    signIn();
                }
            } else {
                System.out.println("Niepoprawny identyfikator lub hasło");
                signIn();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
