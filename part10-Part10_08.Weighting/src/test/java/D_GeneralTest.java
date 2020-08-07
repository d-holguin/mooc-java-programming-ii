
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.Scanner;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;
import org.junit.Test;

public class D_GeneralTest extends TestUtils {

    @Test
    public void noLoopInItem() {
        noLoopsInFile("Item.java");
    }

    @Test
    public void noLoopInSuitcase() {
        noLoopsInFile("Suitcase.java");
    }

    @Test
    public void noLoopInHold() {
        noLoopsInFile("Hold.java");
    }

    private void noLoopsInFile(String file) {
        File f = Paths.get("src", "main", "java", file).toFile();
        StringBuilder sb = new StringBuilder();
        try (Scanner s = new Scanner(f)) {
            while (s.hasNextLine()) {
                sb.append(s.nextLine());
            }
        } catch (FileNotFoundException ex) {
            fail("The file " + file + " could not be found.");
        }

        String content = sb.toString().replaceAll("\\s+", "");
        
        //Temporary: for use when translating, as the files contain STUD's with loop constructs
        /*try {
        content = content.split("//")[0];
        } catch (Exception e) {
        }*/
        
        assertFalse("The file " + file + " should not have any loop constructs, but a while was found.", content.contains("while("));
        assertFalse("The file " + file + " should not have any loop constructs, but a for was found.", content.contains("for("));

    }

}
