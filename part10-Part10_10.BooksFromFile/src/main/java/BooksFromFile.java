
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BooksFromFile {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // test your method here

    }

    public static List<Book> readBooks(String file) {

        List<Book> books = new ArrayList<>();

        try {
            Files.lines((Paths.get(file)))
                    .map(row -> row.split(",")) //splits the row on the comma
                    
                    .filter(parts -> parts.length >= 4) // if there is less than 4 parts from spliting on the comma they are ignored
                    .map(parts -> new Book(parts[0], Integer.valueOf(parts[1]), Integer.valueOf(parts[2]), parts[3]))   //creates a book 
                    .forEach(Book -> books.add(Book)); //loops and adds Book to arrayList books

            // .forEach(row -> rows.add(row));
        } catch (Exception e) {

            System.out.println("Error reading file" + e.getMessage());
        }

        return books;

    }

}
