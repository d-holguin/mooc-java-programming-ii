
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import fi.helsinki.cs.tmc.edutestutils.Reflex.MethodAndReturnType;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;

@Points("11-07")
public class BiggerClassDiagramTest {

    @Test
    public void ClassesExist() {
        ClassExists("A");
        ClassExists("B");
        ClassExists("C");
        ClassExists("D");
        ClassExists("E");

        interfaceExists("IA");
        interfaceExists("IB");
        interfaceExists("IC");

        classExtendsOrImplementsAnother("A", "IA");
        classExtendsOrImplementsAnother("B", "IB");
        classExtendsOrImplementsAnother("C", "IC");

        classExtendsOrImplementsAnother("B", "A");
        classExtendsOrImplementsAnother("C", "B");

        referenceExists("D", "IA");

        collectionReferenceExists("C", "E");
        collectionReferenceExists("E", "C");
    }

    public void methodExists(String Class, String methodName, Class methodReturnType, Class... parameters) {
        MethodAndReturnType mr = Reflex.reflect(Class).method(methodName).returning(methodReturnType);
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

    public void classExtendsOrImplementsAnother(String ClassOne, String ClassTwo) {
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

    public void ClassExists(String Class) {
        assertTrue("Class " + Class + " could not be found. Please ensure that it has been defined as: public class " + Class + " { ...?", Reflex.reflect(Class).isPublic());
    }
}
