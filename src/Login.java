import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Login {
    static Scanner scanner = new Scanner(System.in);
    public static void signIn() {
        try {
            System.out.println("-----------------------");
            System.out.println("Funeral Central System");
            System.out.println("-----------------------");
            System.out.print("Podaj login: ");
            String loginUSER = scanner.nextLine();
            System.out.print("Podaj hasło: ");
            String hasloUSER = scanner.nextLine();

            ResultSet result = QueryExecutor.executeSelect("SELECT * FROM `konta` WHERE login='" + loginUSER + "'");

            if (result.next()) {
                String loginDATABASE = result.getString("login");
                String hasloDATABASE = result.getString("haslo");
                String nameDATABASE = result.getString("imie");
                String positionDATABASE = result.getString("stanowisko");

                if (loginUSER.equals(loginDATABASE) && hasloUSER.equals(hasloDATABASE)) {
                    switch (positionDATABASE) {
//                        case "administrator" -> ;

//                        case "worker" ->;
                    }
                } else {
                    System.out.println("Niepoprawne hasło");
                    signIn();
                }
            } else {
                System.out.println("Niepoprawny login lub hasło");
                signIn();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
