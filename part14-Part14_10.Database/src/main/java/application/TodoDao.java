package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TodoDao {

    private String databasePath;

    public TodoDao(String databasePath) {
        this.databasePath = databasePath;
    }

    public List<Todo> list() throws SQLException {
        List<Todo> todos = new ArrayList<>();
        try (Connection connection = createConnectionAndEnsureDatabase();
                ResultSet results = connection.prepareStatement("SELECT * FROM Todo").executeQuery()) {
            while (results.next()) {
                todos.add(new Todo(results.getInt("id"), results.getString("name"), results.getString("description"), results.getBoolean("done")));
            }
        }
        return todos;
    }

    public void add(Todo todo) throws SQLException {
        try (Connection connection = createConnectionAndEnsureDatabase()) {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO Todo (name, description, done) VALUES (?, ?, ?)");
            stmt.setString(1, todo.getName());
            stmt.setString(2, todo.getDescription());
            stmt.setBoolean(3, todo.getDone());
            stmt.executeUpdate();
        }
    }

    public void markAsDone(int id) throws SQLException {
        try (Connection connection = createConnectionAndEnsureDatabase()) {
            PreparedStatement stmt = connection.prepareStatement("UPDATE Todo SET done = true WHERE id = ?");
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public void remove(int id) throws SQLException {
        try (Connection connection = createConnectionAndEnsureDatabase()) {
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM Todo WHERE id = ?");
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    private Connection createConnectionAndEnsureDatabase() throws SQLException {
        Connection conn = DriverManager.getConnection(this.databasePath, "sa", "");
        try {
            conn.prepareStatement("CREATE TABLE Todo (id int auto_increment primary key, name varchar(255), description varchar(10000), done boolean)").execute();
        } catch (SQLException t) {
        }

        return conn;
    }
}
