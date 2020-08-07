package application;

import application.TodoDao;
import application.UserInterface;
import application.Todo;
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import java.io.File;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import java.util.stream.Collectors;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

@Points("14-10")
public class DatabaseTest {

    @Rule
    public MockStdio io = new MockStdio();

    private TodoDao database;
    private String databaseFile;

    @Before
    public void setup() {
        this.databaseFile = "test-" + UUID.randomUUID().toString().substring(0, 6);
        database = new TodoDao("jdbc:h2:./" + databaseFile);
    }

    @After
    public void teardown() {
        try {
            new File(databaseFile + ".mv.db").delete();
            new File(databaseFile + ".trace.db").delete();
        } catch (Exception e) {

        }

    }

    @Test(timeout = 2500)
    public void contentIsAddedToDatabase() throws SQLException {
        String input = "1\nx\n";

        Scanner s = new Scanner(input);
        database.add(new Todo("Socrates", "is mortal", Boolean.FALSE));
        database.add(new Todo("Beethoven", "is a dog", Boolean.FALSE));

        UserInterface ui = new UserInterface(s, database);

        try {
            ui.start();
        } catch (SQLException ex) {
            fail("An error occurred in the execution of the program: " + ex.getMessage());
        }

        String error = "With two todos added to the database: Socrates and Beethoven, they and their descriptions should appear in the listing.\n"
                + "The input was:\n" + input + "\n"
                + "Try listing the todos with the code:\n"
                + "Scanner s = new Scanner(System.in);\n"
                + "TodoDao td = new TodoDao(\"jdbc:h2:./databaseFile\");\n"
                + "td.add(new Todo(\"Socrates\", \"is mortal\", Boolean.FALSE));\n"
                + "td.add(new Todo(\"six\", \"is a dog\", Boolean.FALSE));\n"
                + "UserInterface ui = new UserInterface(s, td);\n"
                + "ui.start();";

        assertTrue(error, lines().stream().filter(l -> l.contains("Socrates") && l.contains("is mortal")).count() == 1);
        assertTrue(error, lines().stream().filter(l -> l.contains("Beethoven") && l.contains("is a dog")).count() == 1);
    }

    @Test(timeout = 2500)
    public void contentsInDatabaseAreListedRandom() throws SQLException {
        String input = "1\nx\n";

        Scanner s = new Scanner(input);
        String s1 = UUID.randomUUID().toString().substring(0, 4);
        String s2 = UUID.randomUUID().toString().substring(0, 4);

        database.add(new Todo(s1, s1 + " is something", Boolean.FALSE));
        database.add(new Todo(s2, s2 + " is another thing", Boolean.FALSE));
        UserInterface ui = new UserInterface(s, database);

        try {
            ui.start();
        } catch (SQLException ex) {
            fail("An error occurred when executing the program: " + ex.getMessage());
        }

        String error = "With two todos added to the database: " + s1 + " and " + s2 + ", they and their descriptions should appear in the listing.\n"
                + "The input was\n" + input + "\n"
                + "Try to list the todos with the code:\n"
                + "Scanner s = new Scanner(System.in);\n"
                + "TodoDao td = new TodoDao(\"jdbc:h2:./databaseFile\");\n"
                + "td.add(new Todo(\"" + s1 + "\", \"" + s1 + " is something\", Boolean.FALSE));\n"
                + "td.add(new Todo(\"" + s2 + "\", \"" + s2 + " is another thing\", Boolean.FALSE));\n"
                + "UserInterface ui = new UserInterface(s, td);\n"
                + "ui.start();";

        assertTrue(error, lines().stream().filter(l -> l.contains(s1) && l.contains(s1 + " is something")).count() == 1);
        assertTrue(error, lines().stream().filter(l -> l.contains(s2) && l.contains(s2 + " is another thing")).count() == 1);
    }

    @Test(timeout = 2500)
    public void dataIsNotListedUnlessRequested() throws SQLException {
        String input = "x\n";

        Scanner s = new Scanner(input);
        String s1 = UUID.randomUUID().toString().substring(0, 4);
        String s2 = UUID.randomUUID().toString().substring(0, 4);

        database.add(new Todo(s1, s1 + " is something", Boolean.FALSE));
        database.add(new Todo(s2, s2 + " is another thing", Boolean.FALSE));
        UserInterface ui = new UserInterface(s, database);

        try {
            ui.start();
        } catch (SQLException ex) {
            fail("An error occurred when executing the program: " + ex.getMessage());
        }

        String error = "The contents of the database should not appear in the output unless it is explicitly asked for.\n"
                + "The input was\n" + input + "\n"
                + "Try to list the todos with the code:\n"
                + "Scanner s = new Scanner(System.in);\n"
                + "TodoDao td = new TodoDao(\"jdbc:h2:./databaseFile\");\n"
                + "td.add(new Todo(\"" + s1 + "\", \"" + s1 + " is something\", Boolean.FALSE));\n"
                + "td.add(new Todo(\"" + s2 + "\", \"" + s2 + " is another thing\", Boolean.FALSE));\n"
                + "UserInterface ui = new UserInterface(s, td);\n"
                + "ui.start();";

        assertTrue(error, lines().stream().filter(l -> l.contains(s1) && l.contains(s1 + " is something")).count() == 0);
        assertTrue(error, lines().stream().filter(l -> l.contains(s2) && l.contains(s2 + " is another thing")).count() == 0);
    }

    @Test(timeout = 2500)
    public void addingAddsDataToDatabase() throws SQLException {
        String name = UUID.randomUUID().toString().substring(0, 4);
        String description = UUID.randomUUID().toString().substring(0, 4);
        String input = "2\n" + name + "\n" + description + "\nx\n";

        Scanner s = new Scanner(input);
        UserInterface ui = new UserInterface(s, database);

        try {
            ui.start();
        } catch (SQLException ex) {
            fail("An error occurred when executing the program: " + ex.getMessage());
        }

        String error = "The todos added in the user interface should end up in the database.\n"
                + "The input was\n" + input + "\n";

        assertFalse(error + "Now the database was empty.", database.list().isEmpty());
        assertFalse(error + "Now there were too many todos in the database.", database.list().size() > 1);

        Todo t = database.list().get(0);
        assertEquals(error + "Now the name was " + t.getName(), name, t.getName());
        assertEquals(error + "Now the description was " + t.getDescription(), description, t.getDescription());
        assertFalse(error + "Expected the todo not to be done (done = false).\n Now the value of the todo's done variable was " + t.getDone(), t.getDone());
    }

    @Test(timeout = 2500)
    public void markAsDone() throws SQLException {
        database.add(new Todo("exam", "rehearse for the exam", false));
        assertFalse("When a todo is stored in the database, it should be marked as not-done (done = false). ", database.list().get(0).getDone());

        Todo todo = database.list().get(0);
        int id = todo.getId();

        String input = "3\n" + id + "\nx\n";
        Scanner s = new Scanner(input);
        UserInterface ui = new UserInterface(s, database);

        try {
            ui.start();
        } catch (SQLException ex) {
            fail("An error occurred when executing the program: " + ex.getMessage());
        }

        String error = "The changes made with the user interface should be reflected in the database.\n"
                + "When the database contains the todo: " + todo + "\n"
                + "And the input is:\n" + input + "\n";

        assertTrue(error + "The number of todos in the database should not change.", database.list().size() == 1);
        assertTrue(error + "The value of the done variable should be updated to true.", database.list().get(0).getDone());

    }

    @Test(timeout = 2500)
    public void markAsDoneMultiple() throws SQLException {
        database.add(new Todo("exam 1", "rehearse for the exam", false));
        database.add(new Todo("exam 2", "rehearse for the exam", false));
        database.add(new Todo("exam 3", "rehearse for the exam", false));
        database.add(new Todo("exa, 4", "rehearse for the exam", false));
        assertTrue("When a todo is stored in the database, it should be marked as not-done (done = false). ", database.list().stream().filter(t -> t.getDone()).count() == 0);

        List<Integer> ids = database.list().stream().map(t -> t.getId()).distinct().unordered().collect(Collectors.toList());
        assertEquals("After four todos have been added in the database, there should be four different ids in there.\nThis error should not occur unless you've modified the files that you should not have touched.", 4, ids.size());

        String input = "3\n" + ids.get(0) + "\n3\n" + ids.get(1) + "\nx\n";
        Scanner s = new Scanner(input);
        UserInterface ui = new UserInterface(s, database);

        try {
            ui.start();
        } catch (SQLException ex) {
            fail("An error occurred when executing the program: " + ex.getMessage());
        }

        for (int id : ids) {
            assertTrue("The id values of the todos in the database should not change when their information is updated.", database.list().stream().filter(t -> t.getId() == id).count() == 1);
        }

        assertTrue("When a todo is marked as done in the user interface, it should also become done in the database.", database.list().stream().filter(t -> t.getId() == ids.get(0)).map(t -> t.getDone()).findFirst().get());
        assertTrue("When a todo is marked as done in the user interface, it should also become done in the database.", database.list().stream().filter(t -> t.getId() == ids.get(1)).map(t -> t.getDone()).findFirst().get());
        assertFalse("Marking a certain todo as done should not modify other todos in the database.", database.list().stream().filter(t -> t.getId() == ids.get(2)).map(t -> t.getDone()).findFirst().get());
        assertFalse("Marking a certain todo as done should not modify other todos in the database.", database.list().stream().filter(t -> t.getId() == ids.get(3)).map(t -> t.getDone()).findFirst().get());

    }

    @Test(timeout = 2500)
    public void removeOne() throws SQLException {
        database.add(new Todo("exam", "rehearse for the exam", false));

        Todo todo = database.list().get(0);
        int id = todo.getId();

        String input = "4\n" + id + "\nx\n";
        Scanner s = new Scanner(input);
        UserInterface ui = new UserInterface(s, database);

        try {
            ui.start();
        } catch (SQLException ex) {
            fail("An error occurred when executing the program: " + ex.getMessage());
        }

        String error = "Removing a todo in the user interface should also remove said todo from the database.\n"
                + "When the database contains the todo: " + todo + "\n"
                + "And the input is:\n" + input + "\n";

        assertTrue(error + "There should be zero rows in the database after executing the program.", database.list().isEmpty());

    }

    @Test(timeout = 2500)
    public void removeMultiple() throws SQLException {
        database.add(new Todo("exam 1", "rehearse for the exam", false));
        database.add(new Todo("exam 2", "rehearse for the exam", false));
        database.add(new Todo("exam 3", "rehearse for the exam", false));
        database.add(new Todo("exam 4", "rehearse for the exam", false));

        List<Integer> ids = database.list().stream().map(t -> t.getId()).distinct().unordered().collect(Collectors.toList());
        assertEquals("After four todos have been added in the database, there should be four different ids in there.\nThis error should not occur unless you've modified the files that you should not have touched.", 4, ids.size());

        String input = "4\n" + ids.get(0) + "\n4\n" + ids.get(1) + "\nx\n";
        Scanner s = new Scanner(input);
        UserInterface ui = new UserInterface(s, database);

        try {
            ui.start();
        } catch (SQLException ex) {
            fail("An error occurred when executing the program: " + ex.getMessage());
        }

        assertTrue("When a todo is removed in the user interface, it should not be found in the database anymore.", database.list().stream().filter(t -> t.getId() == ids.get(0)).count() == 0);
        assertTrue("When a todo is removed in the user interface, it should not be found in the database anymore.", database.list().stream().filter(t -> t.getId() == ids.get(1)).count() == 0);
        assertTrue("Removing one todo should not result in removing other todos.", database.list().stream().filter(t -> t.getId() == ids.get(2)).count() == 1);
        assertTrue("Removing one todo should not result in removing other todos.", database.list().stream().filter(t -> t.getId() == ids.get(3)).count() == 1);

    }

    private List<String> lines() {
        return Arrays.asList(io.getSysOut().split("\n"));
    }
}
