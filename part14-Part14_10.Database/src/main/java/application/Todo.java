package application;

public class Todo {

    private int id;
    private String name;
    private String description;
    private Boolean done;

    public Todo(int id, String name, String description, Boolean done) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.done = done;
    }

    public Todo(String name, String description, Boolean done) {
        this(-1, name, description, done);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    @Override
    public String toString() {
        return "Todo{" + "id=" + id + ", name=" + name + ", description=" + description + ", done=" + done + '}';
    }

}
