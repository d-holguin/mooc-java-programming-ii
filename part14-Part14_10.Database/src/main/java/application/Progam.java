package application;

import java.sql.SQLException;
import java.util.Scanner;

public class Progam {

    public static void main(String[] args) throws SQLException {
        String databasePath = "jdbc:h2:./todo-database";
        if (args.length > 0) {
            databasePath = args[0];
        }

        TodoDao database = new TodoDao(databasePath);
        Scanner scanner = new Scanner(System.in);

        new UserInterface(scanner, database).start();
    }
}
