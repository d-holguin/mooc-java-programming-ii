package application;

import java.sql.SQLException;
import java.util.Scanner;

public class UserInterface {

    private Scanner scanner;
    private TodoDao database;

    public UserInterface(Scanner scanner, TodoDao database) {
        this.scanner = scanner;
        this.database = database;
    }

    public void start() throws SQLException {
        while (true) {
            System.out.println("");
            System.out.println("Enter command:");
            System.out.println("1) list");
            System.out.println("2) add");
            System.out.println("3) mark as done");
            System.out.println("4) remove");
            System.out.println("x) quit");

            System.out.print("> ");
            String command = this.scanner.nextLine();
            if (command.equals("x")) {
                break;
            }

            switch (command) {
                case "1":
                    System.out.println("Listing the database contents");
                    System.out.println(database.list());

                    break;
                case "2":
                    System.out.println("Adding a new todo");
                    System.out.println("Enter name");
                    String todoName = scanner.nextLine();
                    System.out.println("Enter description");
                    String todoDescrip = scanner.nextLine();

                    database.add(new Todo(todoName, todoDescrip, Boolean.FALSE));

                    break;
                case "3":
                    System.out.println("Which todo should be marked as done (give the id)?");
                    String todoID = scanner.nextLine();

                    database.markAsDone(Integer.valueOf(todoID));

                    break;
                case "4":
                    System.out.println("Which todo should be removed as (give the id)?");
                    todoID = scanner.nextLine();

                    database.remove(Integer.valueOf(todoID));

                    break;

                default:
                    System.out.println("Unknown command");

            }

            // implement the functionality here
        }

        System.out.println("Thank you!");
    }

}
