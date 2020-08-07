
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.Before;
import org.junit.Rule;

@Points("10-10")
public class BooksFromFileTest {

    @Rule
    public MockStdio io = new MockStdio();

    private Reflex.MethodRef1 metodi;

    @Before
    public void setupExists() {
        metodi = Reflex.reflect(BooksFromFile.class).staticMethod("readBooks").returning(List.class).taking(String.class);
    }

    @Test
    public void methodReadBooksExists() {
        metodi.requirePublic();
    }

    @Test
    public void methodReturnsBooks() throws IOException, Throwable {
        String data = "A,1,2,B";
        File newFile = create(data);
        List<Book> lines = (List<Book>) metodi.invoke(newFile.getAbsolutePath());
        newFile.delete();

        String error = "When a file is in the format:\n" + data + "\n, the reading should return one book"
                + "\nname: A"
                + "\npublishing year: 1"
                + "\npages: 2"
                + "\nauthor: B";

        assertTrue(error, lines.size() == 1);

        Book k = lines.get(0);
        assertEquals(error, "A", k.getName());
        assertEquals(error, "B", k.getAuthor());
        assertEquals(error, 1, k.getPublishingYear());
        assertEquals(error, 2, k.getPagecount());

    }

    @Test
    public void methodReturnsBooks2() throws IOException, Throwable {
        String data = "A,1,2,B\nC,3,4,D";
        File uusi = create(data);
        List<Book> lines = (List<Book>) metodi.invoke(uusi.getAbsolutePath());
        uusi.delete();

        String error = "When a file is in the format:\n" + data + "\n, the reading should return two books; First:\n"
                + "\nname: A"
                + "\npublishing year: 1"
                + "\npages: 2"
                + "\nauthor: B"
                + "\n\nSecond:"
                + "\nname: C"
                + "\npublishing year: 3"
                + "\npages: 4"
                + "\nauthor: D";

        assertTrue(error, lines.size() == 2);

        Book k = null;

        try {
            k = lines.get(0);
            assertEquals(error, "A", k.getName());
            assertEquals(error, "B", k.getAuthor());
            assertEquals(error, 1, k.getPublishingYear());
            assertEquals(error, 2, k.getPagecount());
        } catch (Throwable t) {
            k = lines.get(1);
            assertEquals(error, "A", k.getName());
            assertEquals(error, "B", k.getAuthor());
            assertEquals(error, 1, k.getPublishingYear());
            assertEquals(error, 2, k.getPagecount());
        }

        try {
            k = lines.get(1);
            assertEquals(error, "C", k.getName());
            assertEquals(error, "D", k.getAuthor());
            assertEquals(error, 3, k.getPublishingYear());
            assertEquals(error, 4, k.getPagecount());
        } catch (Throwable t) {
            k = lines.get(0);
            assertEquals(error, "C", k.getName());
            assertEquals(error, "D", k.getAuthor());
            assertEquals(error, 3, k.getPublishingYear());
            assertEquals(error, 4, k.getPagecount());
        }

    }

    public File create(String lines) throws IOException {
        File tmp = File.createTempFile("tmp-", "teht-09-09");
        FileWriter fw = new FileWriter(tmp);
        fw.write(lines);
        fw.flush();
        fw.close();
        return tmp;
    }
}
