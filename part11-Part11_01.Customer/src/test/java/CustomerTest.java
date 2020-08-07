
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;

@Points("11-01")
public class CustomerTest {

    @Test
    public void isClass() {
        isPrivateAtribute("Customer", "name", String.class);
        isPrivateAtribute("Customer", "address", String.class);
        isPrivateAtribute("Customer", "email", String.class);
    }

    public void isPrivateAtribute(String Class, String name, Class type) {
        assertTrue("Class " + Class + " could not be found. Please ensure that it has been defined int the format: " + Class + " { ...?", Reflex.reflect(Class).isPublic());
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
}
