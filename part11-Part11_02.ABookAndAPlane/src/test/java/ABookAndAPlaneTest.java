
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;

@Points("11-02")
public class ABookAndAPlaneTest {

    @Test
    public void ClassesExist() {
        PrivateAttributesExist("Book", "name", String.class);
        PrivateAttributesExist("Book", "author", String.class);
        PrivateAttributesExist("Book", "pageCount", int.class);

        PrivateAttributesExist("Plane", "ID", String.class);
        PrivateAttributesExist("Plane", "model", String.class);
        PrivateAttributesExist("Plane", "yearOfIntroduction", int.class);
    }

    public void PrivateAttributesExist(String Class, String name, Class type) {
        assertTrue("Class " + Class + " could not be found. Please ensure that it has been defined as: public class " + Class + " { ...?", Reflex.reflect(Class).isPublic());
        Class luokkaClass = Reflex.reflect(Class).getReferencedClass();
        String error = Class + " should have a private variable '" + name + "', that is a '" + type + "'.";

        Field field = null;

        try {
            field = luokkaClass.getDeclaredField(name);
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
}
