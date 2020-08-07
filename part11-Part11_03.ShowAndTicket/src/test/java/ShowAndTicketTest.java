
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;

@Points("11-03")
public class ShowAndTicketTest {

    @Test
    public void ClassesExist() {
        PrivateAttributesExist("Show", "movie", String.class);
        PrivateAttributesExist("Show", "time", String.class);
        limitAttributeCount("Show", 2);

        PrivateAttributesExist("Ticket", "seat", int.class);
        PrivateAttributesExist("Ticket", "code", int.class);
        limitAttributeCount("Show", 3);

        referenceExists("Ticket", "Show");
    }

    public void PrivateAttributesExist(String Class, String name, Class type) {
        assertTrue("Class " + Class + " could not be found. Please ensure that it has been defined as: public class " + Class + " { ...?", Reflex.reflect(Class).isPublic());
        Class classClass = Reflex.reflect(Class).getReferencedClass();
        String error = Class + " should have a private variable '" + name + "', that is a '" + type + "'.";

        Field field = null;

        try {
            field = classClass.getDeclaredField(name);
        } catch (Exception ex) {
            ex.printStackTrace();
            fail(error);
        }

        assertTrue(error, field.getType().equals(type));

        assertTrue(error, Modifier.isPrivate(field.getModifiers()));
    }

    public void limitAttributeCount(String Class, int maxAttributes) {
        assertTrue("Class " + Class + " could not be found. Please ensure that it has been defined as: public class " + Class + " { ...?", Reflex.reflect(Class).isPublic());

        int numAttributes = Reflex.reflect(Class).getReferencedClass().getDeclaredFields().length;
        assertTrue("Class " + Class + " should have max. " + maxAttributes + " attributes. There were " + numAttributes + ".", numAttributes <= maxAttributes);
    }

    public void referenceExists(String from, String to) {
        assertTrue("Class " + from + " could not be found. Please ensure that it has been defined as: public class " + from + " { ...?", Reflex.reflect(from).isPublic());
        assertTrue("Class " + to + " could not be found. Please ensure that it has been defined as: public class " + to + " { ...?", Reflex.reflect(to).isPublic());

        Class fromClass = Reflex.reflect(from).getReferencedClass();
        Class toClass = Reflex.reflect(to).getReferencedClass();

        Field reference = null;
        for (Field declaredField : fromClass.getDeclaredFields()) {
            if (declaredField.getType().equals(toClass)) {
                reference = declaredField;
                break;
            }
        }

        assertNotNull("No reference from " + from + " was found to " + to + ".\nA reference is added by adding the target classes object variable", reference);
    }

}
