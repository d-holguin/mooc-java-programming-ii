
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.Before;
import org.junit.Rule;

@Points("10-09")
public class ReadingFilesPerLineTest {

    @Rule
    public MockStdio io = new MockStdio();

    private Reflex.MethodRef1 method;

    @Before
    public void onSetup() {
        method = Reflex.reflect(ReadingFilesPerLine.class).staticMethod("read").returning(List.class).taking(String.class);
    }

    @Test
    public void onMetodiLue() {
        method.requirePublic();
    }

    @Test
    public void methodReturnsLines() throws IOException, Throwable {
        String data = "this\nis\na test\n";
        File newFile = create(data);
        List<String> lines = (List<String>) method.invoke(newFile.getAbsolutePath());
        newFile.delete();

        for (String string : data.split("\n")) {
            assertTrue("when the file contains the lines: " + data + ", the returned string list should contain the string " + string, lines.contains(string));
        }
    }

    @Test
    public void methodReturnsLines2() throws IOException, Throwable {
        String data = "kekkonen\nwith two rows\n";
        File newFile = create(data);
        List<String> lines = (List<String>) method.invoke(newFile.getAbsolutePath());
        newFile.delete();

        for (String string : data.split("\n")) {
            assertTrue("when the file contains the lines: " + data + ", the returned string list should contain the string " + string, lines.contains(string));
        }
    }

    public File create(String lines) throws IOException {
        File tmp = File.createTempFile("tmp-", "exercise-09-09");
        FileWriter fw = new FileWriter(tmp);
        fw.write(lines);
        fw.flush();
        fw.close();
        return tmp;
    }
}
