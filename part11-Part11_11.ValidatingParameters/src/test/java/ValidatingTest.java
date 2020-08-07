
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import org.junit.*;
import static org.junit.Assert.*;

public class ValidatingTest {

    Class personClass;
    Constructor personConstructor;
    Class calculatorClass;
    Constructor calculatorConstructor;
    Method factorialMethod;
    Method binomialCoefficentMethod;

    @Before
    public void setup() {
        try {
            personClass = ReflectionUtils.findClass("validating.Person");
            personConstructor = ReflectionUtils.requireConstructor(personClass, String.class, int.class);
            calculatorClass = ReflectionUtils.findClass("validating.Calculator");
            calculatorConstructor = ReflectionUtils.requireConstructor(calculatorClass);
            factorialMethod = ReflectionUtils.requireMethod(calculatorClass, "factorial", int.class);
            binomialCoefficentMethod = ReflectionUtils.requireMethod(calculatorClass, "binomialCoefficent", int.class, int.class);
        } catch (Throwable t) {
        }
    }

    @Test
    @Points("11-11.1")
    public void personClass() {
        if (personClass == null) {
            fail("Please ensure that you have created the Person-class in the validating-package.");
        }

        if (personConstructor == null) {
            fail("Please ensure that the Person-class has the constructor 'public Person(int name, int age)'.");
        }

        for (int i = 0; i <= 120; i++) {
            try {
                createPerson("mikael", i);
            } catch (IllegalArgumentException e) {
                fail("Creating a Person with the age " + i + " and name \"mikael\" failed, but the ages [0-120] should be OK.");
            }
        }

        try {
            createPerson("mikael", -5);
            fail("Creating a Person with a negative age succeeded. The constructor of the class should throw an IllegalArgumentException if the age is not between the ages 0-120.");
        } catch (IllegalArgumentException e) {
        }

        try {
            createPerson("mikael", -1);
            fail("Creating a Person with a negative age succeeded. The constructor of the class should throw an IllegalArgumentException if the age is not between the ages 0-120.");
        } catch (IllegalArgumentException e) {
        }

        try {
            createPerson("mikael", 121);
            fail("Creating a Person with the age 121 succeeded. The constructor of the class should throw an IllegalArgumentException if the age is not between the ages 0-120.");
        } catch (IllegalArgumentException e) {
        }

        try {
            createPerson("mikael", 130);
            fail("Creating a Person with the age 130 succeeded. The constructor of the class should throw na IllegalArgumentException if the age is not between the ages 0-120.");
        } catch (IllegalArgumentException e) {
        }

        try {
            createPerson("", 30);
            fail("Creating a Person with an empty name succeeded. The constructor of the class should throw an IllegalArgumentException if the name is null, empty or over 40 characters in length.");
        } catch (IllegalArgumentException e) {
        }

        try {
            createPerson(null, 30);
            fail("Creating a Person with an null name succeeded. The constructor of the class should throw an IllegalArgumentException if the name is null, empty or over 40 characters in length.");
        } catch (IllegalArgumentException e) {
        } catch (NullPointerException e) {
            fail("Tarkistathan ettei Henkilo-luokan konstruktori heitä NullPointerException-virhettä jos nimi on null?");
        }

        try {
            createPerson("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", 30);
            fail("Creating a Person with a name longer than 40 characters succeeded. The constructor of the class should throw an IllegalArgumentException if the name is null, empty or over 40 characters in length.");
        } catch (IllegalArgumentException e) {
        }

        try {
            createPerson("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", 30);
        } catch (IllegalArgumentException e) {
            fail("Creating a Person with a name shorter than/with 40 characters was unsuccesful. The constructor of the class should throw an IllegalArgumentException only if the name is null, empty or over 40 characters in length.");
        }

        try {
            createPerson("a", 30);
        } catch (IllegalArgumentException e) {
            fail("Creating a Person with a name with 1 character was unsuccesful. The constructor of the class should throw an IllegalArgumentException only if the name is null, empty or over 40 characters in length.");
        }
    }

    @Test
    @Points("11-11.2")
    public void calculatorClass() {
        if (calculatorClass == null) {
            fail("Please ensure that you have created the class 'Calculator' in the package 'validating' and that it is defined as public.");
        }

        if (calculatorConstructor == null) {
            fail("Please ensure that the class 'Calculator' has the constructor 'public Calculator()'.");
        }

        if (factorialMethod == null) {
            fail("Please ensure that the class 'Calculator' has the method 'public int factorial(int num)'.");
        }

        Object calculator = createCalculator();

        try {
            callFactorial(calculator, -1);
            fail("The factorial-method of the Calculator-class succeeded with a negative number. The method should only work with positive numbers.");
        } catch (IllegalArgumentException e) {
        }

        try {
            callFactorial(calculator, -42);
            fail("The factorial-method of the Calculator-class succeeded with a negative number. The method should only work with positive numbers.");
        } catch (IllegalArgumentException e) {
        }

        for (int i = 0; i < 5; i++) {
            try {
                callFactorial(calculator, i);
            } catch (IllegalArgumentException e) {
                fail("The factorial-method of the Calculator-class  " + i + ". Luku " + i + " on ei-negatiivinen, joten laskimen kertoma-metodin pitäisi toimia oikein.");
            }
        }

        if (binomialCoefficentMethod == null) {
            fail("Please ensure that the Calculator-class has the method 'public int binomialCoefficent(int setSize, int subsetSize)'");
        }


        try {
            callBinomial(calculator, -1, 3);
            fail("The calling of the binominalCoefficent-method of the calculator-class succeeded with a negative size of the set. It should only allow positive set sizes.");
        } catch (IllegalArgumentException e) {
        }

        try {
            callBinomial(calculator, 3, -1);
            fail("The calling of the binominalCoefficent-method of the calculator-class succeeded with a negative size of the set. It should only allow positive set sizes.");
        } catch (IllegalArgumentException e) {
        }

        try {
            callBinomial(calculator, 3, 4);
            fail("The calling of the binominalCoefficent-method of the calculator-class succeeded with a bigger subset size than the set size. It should only allow subsetsizes equal or smaller than the set size.");
        } catch (IllegalArgumentException e) {
        }


    }

    private Object createCalculator() {
        try {
            return ReflectionUtils.invokeConstructor(calculatorConstructor);
        } catch (Throwable t) {
            fail("Calling the constructor of the Calculator-class caused an exception: " + t.getMessage() + ".");
        }

        return null;
    }

    private int callFactorial(Object calculator, int num) {
        try {
            return ReflectionUtils.invokeMethod(int.class, factorialMethod, calculator, num);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Throwable t) {
            fail("Calling the factor-method of the Calculator-class caused an exception: " + t.getMessage() + ".");
        }

        return -1;
    }

    private int callBinomial(Object calculator, int setSize, int subsetSize) {
        try {
            return ReflectionUtils.invokeMethod(int.class, binomialCoefficentMethod, calculator, setSize, subsetSize);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Throwable t) {
            fail("Calling the binomialCoefficent-method of the Calculator-class caused an exception: " + t.getMessage() + ".");
        }

        return -1;
    }

    private Object createPerson(String name, int age) {
        try {
            return ReflectionUtils.invokeConstructor(personConstructor, name, age);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (NullPointerException e) {
            throw e;
        } catch (Throwable t) {
            fail("Calling the constructor of the Person-class with the name: " + name + " and the age: " + age + " caused the exception: " + t.getMessage() + ". Validating-exceptions should throw the 'IllegalArgumentException'-exception.");
        }

        return null;
    }
}
