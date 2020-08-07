
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import fi.helsinki.cs.tmc.edutestutils.Reflex.MethodAndReturnType;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;

@Points("11-06")
public class SaveablePersonTest {

    @Test
    public void ClassesExist() {
        PrivateAttributeExists("Person", "name", String.class);
        PrivateAttributeExists("Person", "address", String.class);
        limitAttributeCount("Person", 2);

        interfaceExists("Saveable");
        MethodExists("Saveable", "save", void.class);
        MethodExists("Saveable", "delete", void.class);
        MethodExists("Saveable", "load", void.class, String.class);

        implementsOrExtendsAnother("Person", "Saveable");
    }

    public void MethodExists(String Class, String MethodName, Class MethodReturnType, Class... parameters) {
        MethodAndReturnType mr = Reflex.reflect(Class).method(MethodName).returning(MethodReturnType);
        if (parameters.length == 0) {
            mr.takingNoParams().requirePublic();
        } else if (parameters.length == 1) {
            mr.taking(parameters[0]).requirePublic();
        }
    }

    public void interfaceExists(String Class) {
        assertTrue("Interface " + Class + " could not be found. Please ensure that it has been defined as: public interface " + Class + " ...?", Reflex.reflect(Class).isPublic());
        Class luokkaClass = Reflex.reflect(Class).getReferencedClass();
        assertTrue("Please ensure that " + Class + " has been defined as: public interface " + Class + "..?", Modifier.isInterface(luokkaClass.getModifiers()));
    }

    public void implementsOrExtendsAnother(String ClassOne, String ClassTwo) {
        assertTrue("Class " + ClassOne + " could not be found. Please ensure that it has been defined as: public class " + ClassOne + " ...?", Reflex.reflect(ClassOne).isPublic());
        assertTrue("Class " + ClassTwo + " could not be found. Please ensure that it has been defined as: public class " + ClassTwo + " ...?", Reflex.reflect(ClassTwo).isPublic());

        Class firstClass = Reflex.reflect(ClassOne).getReferencedClass();
        Class secondClass = Reflex.reflect(ClassTwo).getReferencedClass();

        String error = "Please ensure that class " + ClassOne + " extends " + ClassTwo + ".";
        if (Modifier.isInterface(secondClass.getModifiers())) {
            error = "Please ensure that class" + ClassOne + " implements " + ClassTwo + ".";
        }

        assertTrue(error, secondClass.isAssignableFrom(firstClass));

    }

    public void PrivateAttributeExists(String Class, String name, Class type) {
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
