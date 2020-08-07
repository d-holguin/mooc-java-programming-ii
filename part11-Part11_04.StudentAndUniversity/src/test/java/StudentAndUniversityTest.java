
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;

@Points("11-04")
public class StudentAndUniversityTest {

    @Test
    public void onLuokat() {
        PrivateAttributeExists("Student", "studentID", int.class);
        PrivateAttributeExists("Student", "name", String.class);
        limitAttributeCount("Student", 3);
        referenceExists("Student", "University");

        PrivateAttributeExists("University", "name", String.class);
        limitAttributeCount("University", 2);

        collectionReferenceExists("University", "Student");
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

    public void collectionReferenceExists(String from, String to) {
        assertTrue("Class " + from + " could not be found. Please ensure that it has been defined as: public class " + from + " { ...?", Reflex.reflect(from).isPublic());
        assertTrue("Class " + to + " could not be found. Please ensure that it has been defined as: public class " + to + " { ...?", Reflex.reflect(to).isPublic());

        Class fromClass = Reflex.reflect(from).getReferencedClass();

        List<Class> collectionTypes = Arrays.asList(List.class, ArrayList.class, Set.class, HashSet.class, Vector.class, Collection.class);

        Field reference = null;
        for (Field declaredField : fromClass.getDeclaredFields()) {
            if (collectionTypes.contains(declaredField.getType())) {
                reference = declaredField;
                break;
            }
        }

        assertNotNull("No collection reference from " + from + " was found to " + to + ".\nA reference can be added by adding a collection to the class " + from + ", with the type parameter " + to + ".\nUse one of the following as the type:\n" + collectionTypes.toString(), reference);
        assertNotNull("The collectionreference in the class " + from + " did not have a type parameter (for example: List<" + to + ">)", reference.getGenericType());

        assertTrue("The collectionreference in the class " + from + " should have the type parameter " + to + ", for example: List<" + to + ">", reference.getGenericType().getTypeName().contains(to));

    }

}
