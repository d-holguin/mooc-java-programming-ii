

import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Modifier;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.Rule;

public class AnimalsTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    @Points("09-12.1")
    public void abstractClassAnimal() {
        assertTrue("The class Animal should be abstract. Now it wasn't", Modifier.isAbstract(Reflex.reflect("Animal").cls().getModifiers()));
        assertTrue("The class Animal should have a constructor that takes a string parameter. Now it didn't.", Reflex.reflect("Animal").constructor().taking(String.class).exists());
        assertTrue("The class Animal should have the method getName. This was not the case.", Reflex.reflect("Animal").method("getName").returning(String.class).takingNoParams().exists());
        assertTrue("The class Animal should have the method eat. This was not the case.", Reflex.reflect("Animal").method("eat").returningVoid().takingNoParams().exists());
        assertTrue("The class Animal should have the method sleep. This was not the case.", Reflex.reflect("Animal").method("sleep").returningVoid().takingNoParams().exists());
    }

    @Test
    @Points("09-12.2")
    public void dogClass() throws Throwable {
        assertFalse("The class Dog shouldn't be abstract, but it was.", Modifier.isAbstract(Reflex.reflect("Dog").cls().getModifiers()));
        assertTrue("The class Dog should have a constructor that takes a string parameter. This was not the case.", Reflex.reflect("Dog").constructor().taking(String.class).exists());
        assertTrue("The class Dog should have non-parameterized constructor. This was not the case.", Reflex.reflect("Dog").constructor().takingNoParams().exists());
        assertTrue("The class Dog should have the method getName. This was not the case.", Reflex.reflect("Dog").method("getName").returning(String.class).takingNoParams().exists());
        assertTrue("The class Dog should have the method eat. This was not the case.", Reflex.reflect("Dog").method("eat").returningVoid().takingNoParams().exists());
        assertTrue("The class Dog should have the method sleep. This was not the case.", Reflex.reflect("Dog").method("sleep").returningVoid().takingNoParams().exists());
        assertTrue("The class Dog should have the method bark. This was not the case.", Reflex.reflect("Dog").method("bark").returningVoid().takingNoParams().exists());

        Object dog = Reflex.reflect("Dog").constructor().takingNoParams().invoke();
        Reflex.reflect("Dog").method("bark").returningVoid().takingNoParams().invokeOn(dog);
        assertTrue("Dog's bark wasn't printed. The output was:\n" + io.getSysOut(), io.getSysOut().toLowerCase().contains("dog") && io.getSysOut().toLowerCase().contains("barks"));

        Object winnie = Reflex.reflect("Dog").constructor().taking(String.class).invoke("Winnie");
        Reflex.reflect("Dog").method("eat").returningVoid().takingNoParams().invokeOn(winnie);
        assertTrue("Winnie's eating wasn't printed. The output was:\n" + io.getSysOut(), io.getSysOut().toLowerCase().contains("winnie") && io.getSysOut().toLowerCase().contains("eat"));
    }

    @Test
    @Points("09-12.2")
    public void dogClassMethodsInherited() {
        assertTrue("The getName method of class Dog should be inherited. This was not the case.", Reflex.reflect("Dog").method("getName").returning(String.class).takingNoParams().getMethod().getDeclaringClass().toString().contains("Animal"));
        assertTrue("The eat method of class Dog should be inherited. This was not the case.", Reflex.reflect("Dog").method("eat").returningVoid().takingNoParams().getMethod().getDeclaringClass().toString().contains("Animal"));
        assertTrue("The sleep method of class Dog should be inherited. This was not the case.", Reflex.reflect("Dog").method("sleep").returningVoid().takingNoParams().getMethod().getDeclaringClass().toString().contains("Animal"));
    }

    @Test
    @Points("09-12.3")
    public void catClass() {
        assertFalse("The class Cat shouldn't be abstract. Now it was.", Modifier.isAbstract(Reflex.reflect("Cat").cls().getModifiers()));
        assertTrue("The class Cat should have a constructor that takes a string as a parameter. This was not the case.", Reflex.reflect("Cat").constructor().taking(String.class).exists());
        assertTrue("The class Cat should have a non-parameterized constructor. This was not the case.", Reflex.reflect("Cat").constructor().takingNoParams().exists());
        assertTrue("The class Cat should have the method getName. This was not the case.", Reflex.reflect("Cat").method("getName").returning(String.class).takingNoParams().exists());
        assertTrue("The class Cat should have the method eat. This was not the case.", Reflex.reflect("Cat").method("eat").returningVoid().takingNoParams().exists());
        assertTrue("The class Cat should have the method sleep. This was not the case.", Reflex.reflect("Cat").method("sleep").returningVoid().takingNoParams().exists());
        assertTrue("The class Cat should have the method purr. This was not the case.", Reflex.reflect("Cat").method("purr").returningVoid().takingNoParams().exists());
    }

    @Test
    @Points("09-12.3")
    public void catClassMethodsInherited() {
        assertTrue("The getName method of class Cat should be inherited. This was not the case.", Reflex.reflect("Cat").method("getName").returning(String.class).takingNoParams().getMethod().getDeclaringClass().toString().contains("Animal"));
        assertTrue("The eat method of class Cat should be inherited. This was not the case.", Reflex.reflect("Cat").method("eat").returningVoid().takingNoParams().getMethod().getDeclaringClass().toString().contains("Animal"));
        assertTrue("The method sleep of class Cat should be inherited. This was not the case.", Reflex.reflect("Cat").method("sleep").returningVoid().takingNoParams().getMethod().getDeclaringClass().toString().contains("Animal"));
    }

    @Test
    @Points("09-12.4")
    public void interfaceNoiseCapableExists() {
        assertTrue("The interface NoiseCapable should be an interface. This was not the case.", Modifier.isInterface(Reflex.reflect("NoiseCapable").cls().getModifiers()));
        assertTrue("The interface NoiseCapable should define the method makeNoise. This was not the case.", Reflex.reflect("NoiseCapable").method("makeNoise").returningVoid().takingNoParams().exists());
    }

    @Test
    @Points("09-12.4")
    public void dogImplementsInterfaceNoiseCapable() throws Throwable {
        assertTrue("Dog should implement the interface NoiseCapable. This was not the case.", Reflex.reflect("NoiseCapable").cls().isAssignableFrom(Reflex.reflect("Dog").cls()));

        Object torpedo = Reflex.reflect("Dog").constructor().taking(String.class).invoke("Torpedo");
        Reflex.reflect("NoiseCapable").method("makeNoise").returningVoid().takingNoParams().invokeOn(torpedo);
        Reflex.reflect("Dog").method("eat").returningVoid().takingNoParams().invokeOn(torpedo);

        assertTrue("The dog's bark was not printed when it was called via the interface NoiseCapable. The output was:\n" + io.getSysOut(), io.getSysOut().toLowerCase().contains("torpedo") && io.getSysOut().toLowerCase().contains("barks"));
    }
    
    @Test
    @Points("09-12.4")
    public void catImplementsInterfaceNoiseCapable() throws Throwable {
        assertTrue("Cat should implement the interface NoiseCapable. This was not the case.", Reflex.reflect("NoiseCapable").cls().isAssignableFrom(Reflex.reflect("Cat").cls()));

        Object garfield = Reflex.reflect("Cat").constructor().taking(String.class).invoke("Garfield");
        Reflex.reflect("NoiseCapable").method("makeNoise").returningVoid().takingNoParams().invokeOn(garfield);
        Reflex.reflect("Cat").method("eat").returningVoid().takingNoParams().invokeOn(garfield);

        assertTrue("The cat's purr was not printed when it was called via the interface NoiseCapable. The output was:\n" + io.getSysOut(), io.getSysOut().toLowerCase().contains("garfield") && io.getSysOut().toLowerCase().contains("purrs"));
    }

}
