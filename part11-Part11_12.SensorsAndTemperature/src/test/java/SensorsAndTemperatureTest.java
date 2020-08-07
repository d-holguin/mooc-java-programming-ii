
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.junit.Test;
import static org.junit.Assert.*;
import application.Sensor;

public class SensorsAndTemperatureTest<_Sensor> {

    @Test
    @Points("11-12.1")
    public void classStandardSensorExists() {
        String klassName = "application.StandardSensor";
        Reflex.ClassRef<Object> classRef;
        classRef = Reflex.reflect(klassName);

        assertTrue("The class " + s(klassName) + " should defined as public\n"
                + "public class " + klassName + " {...\n}", classRef.isPublic());
    }

    @Test
    @Points("11-12.1")
    public void noExtraVariablesWithStandardSensor() {
        String klassName = "application.StandardSensor";
        sanitezationCheck(klassName, 1, "the variable that tells if the sensor is on");
    }

    @Test
    @Points("11-12.1")
    public void testStandardSensorConstructor() throws Throwable {
        String klassName = "application.StandardSensor";
        Reflex.ClassRef<Object> classRef = Reflex.reflect(klassName);

        Reflex.MethodRef1<Object, Object, Integer> ctor = classRef.constructor().taking(int.class).withNiceError();
        assertTrue("Please define the class " + s(klassName) + " a public constructor: \n"
                + "public " + s(klassName) + "(int arvo)", ctor.isPublic());
        String v = "the error was caused by the code: new StandardSensor(10);\n";
        ctor.withNiceError(v).invoke(10);
    }

    public Sensor newStandardSensor(int ti) throws Throwable {
        String klassName = "application.StandardSensor";
        Reflex.ClassRef<Object> classRef;
        classRef = Reflex.reflect(klassName);
        Reflex.MethodRef1<Object, Object, Integer> ctor = classRef.constructor().taking(int.class).withNiceError();
        return (Sensor) ctor.invoke(ti);
    }

    @Test
    @Points("11-12.1")
    public void StandardSensorIsASensor() {
        String klassName = "application.StandardSensor";
        Class clazz = ReflectionUtils.findClass(klassName);

        boolean implementsInteface = false;
        Class kali = Sensor.class;
        for (Class iface : clazz.getInterfaces()) {
            if (iface.equals(kali)) {
                implementsInteface = true;
            }
        }

        if (!implementsInteface) {
            fail("Please ensure that the class StandardSensor implements the interface Sensor.");
        }
    }

    @Test
    @Points("11-12.1")
    public void testStandardSensor() throws Throwable {
        String klassName = "application.StandardSensor";
        Reflex.ClassRef<Object> classRef = Reflex.reflect(klassName);

        Sensor vs10 = newStandardSensor(10);
        Sensor vs55 = newStandardSensor(55);

        String k1 = ""
                + "StandardSensor s = new StandardSensor(10);\n"
                + "s.read();\n";

        String k2 = ""
                + "StandardSensor s = new StandardSensor(55);\n"
                + "s.read();\n";

        assertEquals(k1, 10, (int) classRef.method(vs10, "read").returning(int.class).takingNoParams().withNiceError(k1).invoke());
        assertEquals(k2, 55, (int) classRef.method(vs55, "read").returning(int.class).takingNoParams().withNiceError(k2).invoke());

        k1 = ""
                + "StandardSensor s = new StandardSensor(10);\n"
                + "s.isOn();\n";

        k2 = ""
                + "StandardSensor s = new StandardSensor(55);\n"
                + "s.isOn();\n";

        assertEquals(k1, true, (boolean) classRef.method(vs10, "isOn").returning(boolean.class).takingNoParams().withNiceError(k1).invoke());
        assertEquals(k2, true, (boolean) classRef.method(vs55, "isOn").returning(boolean.class).takingNoParams().withNiceError(k1).invoke());

        k1 = ""
                + "StandardSensor s = new StandardSensor(10);\n"
                + "s.setOff();\n";

        classRef.method(vs10, "setOff").returningVoid().takingNoParams().withNiceError(k1).invoke();

        k1 = ""
                + "StandardSensor s = new StandardSensor(10);\n"
                + "s.setOff();\n"
                + "s.isOn();\n";

        assertEquals(k1, true, (boolean) classRef.method(vs10, "isOn").returning(boolean.class).takingNoParams().withNiceError(k1).invoke());

        k1 = ""
                + "StandardSensor s = new StandardSensor(10);\n"
                + "s.setOn();\n";

        classRef.method(vs10, "setOn").returningVoid().takingNoParams().withNiceError(k1).invoke();

        k1 = ""
                + "StandardSensor s = new StandardSensor(10);\n"
                + "s.setOn();\n"
                + "s.isOn();\n";

        assertEquals(k1, true, (boolean) classRef.method(vs10, "isOn").returning(boolean.class).takingNoParams().withNiceError(k1).invoke());

    }

    /*
     *
     */
    @Test
    @Points("11-12.2")
    public void classTemperatureSensorExists() {
        String klassName = "application.TemperatureSensor";
        Reflex.ClassRef<Object> classRef;
        classRef = Reflex.reflect(klassName);

        assertTrue("The class " + s(klassName) + " should defined as public\n"
                + "public class " + klassName + " {...\n}", classRef.isPublic());
    }

    @Test
    @Points("11-12.2")
    public void noExtraVariablesWithTempratureSensor() {
        String klassName = "application.TemperatureSensor";
        sanitezationCheck(klassName, 2, "a Random-object (not neccessary) and a variable rememberig if it's on");
    }

    @Test
    @Points("11-12.2")
    public void testTemperatureSensorConstructor() throws Throwable {
        String klassName = "application.TemperatureSensor";
        Reflex.ClassRef<Object> classRef = Reflex.reflect(klassName);

        Reflex.MethodRef0<Object, Object> ctor = classRef.constructor().takingNoParams().withNiceError();
        assertTrue("Please define the class " + s(klassName) + " a public constructor: \n"
                + "public " + s(klassName) + "()", ctor.isPublic());
        String v = "The error was caused by the code: new TemperatureSensor();\n";
        ctor.withNiceError(v).invoke();
    }

    @Test
    @Points("11-12.2")
    public void TemperatureSensorIsASensor() {
        String klassName = "application.TemperatureSensor";
        Class clazz = ReflectionUtils.findClass(klassName);

        boolean implementsInteface = false;
        Class kali = Sensor.class;
        for (Class iface : clazz.getInterfaces()) {
            if (iface.equals(kali)) {
                implementsInteface = true;
            }
        }

        if (!implementsInteface) {
            fail("Toteuttaahan luokka TemperatureSensor rajapinnan Sensori?");
        }
    }

    public Sensor newTemperatureSensor() throws Throwable {
        String klassName = "application.TemperatureSensor";
        Reflex.ClassRef<Object> classRef;
        classRef = Reflex.reflect(klassName);
        Reflex.MethodRef0<Object, Object> ctor = classRef.constructor().takingNoParams().withNiceError();
        return (Sensor) ctor.invoke();
    }

    @Test
    @Points("11-12.2")
    public void testTemperatureSensor() throws Throwable {
        String klassName = "application.TemperatureSensor";
        Reflex.ClassRef<Object> classRef = Reflex.reflect(klassName);

        Sensor sensor1 = newTemperatureSensor();

        // alussa ei päällä
        String k1 = ""
                + "TemperatureSensor v = new TemperatureSensor();\n"
                + "s.isOn();\n";

        assertEquals(k1, false, (boolean) classRef.method(sensor1, "isOn").returning(boolean.class).takingNoParams().withNiceError(k1).invoke());

        // päälle
        k1 = ""
                + "TemperatureSensor v = new TemperatureSensor();\n"
                + "s.setOn();\n";

        classRef.method(sensor1, "setOn").returningVoid().takingNoParams().withNiceError(k1).invoke();

        k1 = ""
                + "TemperatureSensor v = new TemperatureSensor();\n"
                + "s.setOn();\n"
                + "s.isOn();\n";

        assertEquals(k1, true, (boolean) classRef.method(sensor1, "isOn").returning(boolean.class).takingNoParams().withNiceError(k1).invoke());

        // readings
        k1 = ""
                + "TemperatureSensor v = new TemperatureSensor();\n"
                + "s.read();\n";

        Set tulokset = new TreeSet();
        for (int i = 0; i < 1000; i++) {
            int tulos = (int) classRef.method(sensor1, "read").returning(int.class).takingNoParams().withNiceError(k1).invoke();
            assertTrue("Lämpötilan piti olla välillä -30...30, mutta:\n" + k1 + " \n" + tulos, tulos > -31 && tulos < 31);
            tulokset.add(tulos);
        }
        assertTrue("Luotiin TemperatureSensor v = new TemperatureSensor(); ja kutsuttiin s.read() tuhat kertaa.\n"
                + "lämpötilat piti arpoa väliltä -30...30. Ei kuitenkaan saatu muita lämpötiloja kuin\n"
                + tulokset.toString(), tulokset.size() > 50);

        // pois päältä
        k1 = ""
                + "TemperatureSensor v = new TemperatureSensor();\n"
                + "s.setOff();\n";

        classRef.method(sensor1, "setOff").returningVoid().takingNoParams().withNiceError(k1).invoke();

        k1 = ""
                + "TemperatureSensor v = new TemperatureSensor();\n"
                + "s.setOff();\n"
                + "s.isOn();\n";

        assertEquals(k1, false, (boolean) classRef.method(sensor1, "isOn").returning(boolean.class).takingNoParams().withNiceError(k1).invoke());

        k1 = ""
                + "TemperatureSensor v = new TemperatureSensor();\n"
                + "s.setOff();\n"
                + "s.read();\n";
        try {
            classRef.method(sensor1, "read").returning(int.class).takingNoParams().withNiceError(k1).invoke();
            fail("Olisi pitänyt heittää poikkeus IllegalStateException() kun suoritettiin\n"
                    + k1);
        } catch (Throwable e) {
        }
    }

    /*
     *
     */
    @Test
    @Points("11-12.3")
    public void classAverageSensorExists() {
        String klassName = "application.AverageSensor";
        Reflex.ClassRef<Object> classRef;
        classRef = Reflex.reflect(klassName);

        assertTrue("The class " + s(klassName) + " should defined as public\n"
                + "public class " + klassName + " {...\n}", classRef.isPublic());
    }

    @Test
    @Points("11-12.3")
    public void noExtraVariablesWithAverageSensor() {
        String klassName = "application.AverageSensor";
        /*sanitezationCheck(klassName, 2, "hallittavat sensors sekä readings muistavat oliomuuttujat\n"
        + "Päälläolotietoa ei kannata ylläpitää suoraan AverageSensorssa vaan se kannattaa kysellä hallinnassa olevilta"
        + "sensoreilta.");*/
        sanitezationCheck(klassName, 2, "lists of the controllable sensors and the past readings\n"
                + "The information about which sensors are on is not adviced to keep at the Average sensor, but to "
                + "ask the sensors each time separetly.");
    }

    @Test
    @Points("11-12.3")
    public void testAverageSensor() throws Throwable {
        String klassName = "application.AverageSensor";
        Reflex.ClassRef<Object> classRef = Reflex.reflect(klassName);

        Reflex.MethodRef0<Object, Object> ctor = classRef.constructor().takingNoParams().withNiceError();
        assertTrue("Please define the class " + s(klassName) + " a public constructor: \n"
                + "public " + s(klassName) + "()", ctor.isPublic());
        String v = "The error was caused by the code: new TemperatureSensor();\n";
        ctor.withNiceError(v).invoke();
    }

    @Test
    @Points("11-12.3")
    public void AverageSensorIsASensor() {
        String klassName = "application.AverageSensor";
        Class clazz = ReflectionUtils.findClass(klassName);

        boolean implementsInteface = false;
        Class kali = Sensor.class;
        for (Class iface : clazz.getInterfaces()) {
            if (iface.equals(kali)) {
                implementsInteface = true;
            }
        }

        if (!implementsInteface) {
            fail("Please ensure that the class AverageSensor implements the interface Sensor.");
        }
    }

    public Sensor newAverageSensor() throws Throwable {
        String klassName = "application.AverageSensor";
        Reflex.ClassRef<Object> classRef;
        classRef = Reflex.reflect(klassName);
        Reflex.MethodRef0<Object, Object> ctor = classRef.constructor().takingNoParams().withNiceError();
        return (Sensor) ctor.invoke();
    }

    @Test
    @Points("11-12.3")
    public void methodForAddingSensorsExistsForAverageSensor() throws Throwable {
        String klassName = "application.AverageSensor";
        Reflex.ClassRef<Object> classRef = Reflex.reflect(klassName);

        Sensor sensor1 = newTemperatureSensor();

        String k1 = "The error was caused by the code\n"
                + "AverageSensor ka = new AverageSensor();\n"
                + "ka.addSensor( new TemperatureSensor() );\n";

        Sensor ka = newAverageSensor();

        assertTrue("Please add the method 'addSensor(Sensor toAdd)' in the class AverageSensor.", classRef.method(ka, "addSensor").returningVoid().taking(Sensor.class).isPublic());

        classRef.method(ka, "addSensor").returningVoid().taking(Sensor.class).withNiceError(k1).invoke(sensor1);
    }

    @Test
    @Points("11-12.3")
    public void averageCalculationWorks() throws Throwable {
        String klassName = "application.AverageSensor";
        Reflex.ClassRef<Object> classRef = Reflex.reflect(klassName);

        Sensor sensor1 = newStandardSensor(4);

        String code = "The error was caused by the code\n"
                + "AverageSensor ka = new AverageSensor();\n"
                + "ka.addSensor( new StandardSensor(4) );\n"
                + "ka.read();\n";

        Sensor ka = newAverageSensor();

        classRef.method(ka, "addSensor").returningVoid().taking(Sensor.class).invoke(sensor1);

        classRef.method(ka, "read").returning(int.class).takingNoParams().withNiceError(code).invoke();

        assertEquals(code, 4, ka.read());

        code = "The error was caused by the code\n"
                + "AverageSensor ka = new AverageSensor();\n"
                + "ka.addSensor( new StandardSensor(4) );\n"
                + "ka.addSensor( new StandardSensor(5) );\n"
                + "ka.addSensor( new StandardSensor(9) );\n"
                + "ka.read();\n";

        classRef.method(ka, "addSensor").returningVoid().taking(Sensor.class).withNiceError(code).invoke(newStandardSensor(5));
        classRef.method(ka, "addSensor").returningVoid().taking(Sensor.class).withNiceError(code).invoke(newStandardSensor(9));

        classRef.method(ka, "read").returning(int.class).takingNoParams().withNiceError(code).invoke();
        assertEquals(code, 6, ka.read());

    }

    @Test
    @Points("11-12.3")
    public void AverageSensorOnOff() throws Throwable {
        String klassName = "application.AverageSensor";
        Reflex.ClassRef<Object> classRef = Reflex.reflect(klassName);

        Sensor sensor1 = newTemperatureSensor();

        String code = ""
                + "AverageSensor ka = new AverageSensor();\n"
                + "ka.addSensor( new TemperatureSensor() );\n"
                + "ka.isOn();\n";

        Sensor ka = newAverageSensor();

        classRef.method(ka, "addSensor").returningVoid().taking(Sensor.class).invoke(sensor1);

        assertEquals("Koska lämpösensor on aluksi pois päältä, ei AverageSensornkaan pitäisi olla päällä\n"
                + "" + code, false, classRef.method(ka, "isOn").returning(boolean.class).takingNoParams().withNiceError(code).invoke());

        code = "TemperatureSensor sensor = new TemperatureSensor();\n"
                + "sensor.setOn();\n"
                + "AverageSensor ka = new AverageSensor();\n"
                + "ka.addSensor( sensor);\n"
                + "ka.isOn();\n";

        sensor1 = newTemperatureSensor();
        sensor1.setOn();
        ka = newAverageSensor();
        classRef.method(ka, "addSensor").returningVoid().taking(Sensor.class).invoke(sensor1);

        assertEquals("Jos kesiarvosensorin ainoa hallitsema sensori on päällä,"
                + " pitäisi AverageSensorn olla päällä\n"
                + "" + code, true, classRef.method(ka, "isOn").returning(boolean.class).takingNoParams().withNiceError(code).invoke());

        code = "TemperatureSensor sensor = new TemperatureSensor();\n"
                + "sensor.setOn();\n"
                + "AverageSensor ka = new AverageSensor();\n"
                + "ka.addSensor( sensor);\n"
                + "ka.setOff();\n"
                + "ka.isOn();\n";

        sensor1 = newTemperatureSensor();
        sensor1.setOn();
        ka = newAverageSensor();
        classRef.method(ka, "addSensor").returningVoid().taking(Sensor.class).invoke(sensor1);
        classRef.method(ka, "setOff").returningVoid().takingNoParams().withNiceError(code).invoke();

        assertEquals(code, false, classRef.method(ka, "isOn").returning(boolean.class).takingNoParams().withNiceError(code).invoke());

        code
                = "TemperatureSensor sensor = new TemperatureSensor();\n"
                + "sensor.setOn();\n"
                + "AverageSensor ka = new AverageSensor();\n"
                + "ka.addSensor( sensor );\n"
                + "ka.setOff();\n"
                + "sensor.isOn();\n";

        assertEquals(code, false, sensor1.isOn());
    }

    @Test
    @Points("11-12.3")
    public void AverageSensorOnOff2() throws Throwable {
        String klassName = "application.AverageSensor";
        Reflex.ClassRef<Object> classRef = Reflex.reflect(klassName);

        Sensor sensor1 = newTemperatureSensor();
        Sensor sensor2 = newTemperatureSensor();

        String code = ""
                + "AverageSensor ka = new AverageSensor();\n"
                + "TemperatureSensor sensor1 = new TemperatureSensor();\n"
                + "TemperatureSensor sensor2 = new TemperatureSensor();\n"
                + "ka.addSensor(sensor1);\n"
                + "ka.addSensor(sensor2);\n"
                + "ka.isOn();\n";

        Sensor ka = newAverageSensor();

        classRef.method(ka, "addSensor").returningVoid().taking(Sensor.class).invoke(sensor1);
        classRef.method(ka, "addSensor").returningVoid().taking(Sensor.class).invoke(sensor2);

        assertEquals(code, false, classRef.method(ka, "isOn").returning(boolean.class).takingNoParams().withNiceError(code).invoke());

        ka.setOn();

        code = ""
                + "AverageSensor ka = new AverageSensor();\n"
                + "TemperatureSensor sensor1 = new TemperatureSensor();\n"
                + "TemperatureSensor sensor2 = new TemperatureSensor();\n"
                + "ka.addSensor(sensor1);\n"
                + "ka.addSensor(sensor2);\n"
                + "ka.setOn();\n"
                + "ka.isOn();\n";

        assertEquals(code, true, ka.isOn());

        code = ""
                + "AverageSensor ka = new AverageSensor();\n"
                + "TemperatureSensor sensor1 = new TemperatureSensor();\n"
                + "TemperatureSensor sensor2 = new TemperatureSensor();\n"
                + "ka.addSensor(sensor1);\n"
                + "ka.addSensor(sensor2);\n"
                + "ka.setOn();\n"
                + "sensor1.isOn();\n";

        assertEquals(code, true, sensor1.isOn());

        code = ""
                + "AverageSensor ka = new AverageSensor();\n"
                + "TemperatureSensor sensor1 = new TemperatureSensor();\n"
                + "TemperatureSensor sensor2 = new TemperatureSensor();\n"
                + "ka.addSensor(sensor1);\n"
                + "ka.addSensor(sensor2);\n"
                + "ka.setOn();\n"
                + "sensor2.isOn();\n";

        assertEquals(code, true, sensor2.isOn());
    }

    @Test
    @Points("11-12.3")
    public void AverageSensorExceptionIfTryingToUseWhileOff() throws Throwable {
        String klassName = "application.AverageSensor";
        Reflex.ClassRef<Object> classRef = Reflex.reflect(klassName);

        Sensor sensor1 = newTemperatureSensor();
        Sensor sensor2 = newTemperatureSensor();

        String code = ""
                + "AverageSensor ka = new AverageSensor();\n"
                + "TemperatureSensor sensor1 = new TemperatureSensor();\n"
                + "TemperatureSensor sensor2 = new TemperatureSensor();\n"
                + "ka.addSensor( sensor1);\n"
                + "ka.addSensor( sensor2);\n"
                + "ka.read();\n";

        Sensor ka = newAverageSensor();

        classRef.method(ka, "addSensor").returningVoid().taking(Sensor.class).invoke(sensor1);
        classRef.method(ka, "addSensor").returningVoid().taking(Sensor.class).invoke(sensor2);

        try {
            classRef.method(ka, "read").returning(int.class).takingNoParams().withNiceError(code).invoke();
            fail("Should have thrown a IllegalStateException when run:\n"
                    + code);
        } catch (Throwable e) {
        }
    }

    /*
     *
     */
    @Test
    @Points("11-12.4")
    public void AverageSensorMethodReadings() throws Throwable {
        String klassName = "application.AverageSensor";
        Reflex.ClassRef<Object> classRef = Reflex.reflect(klassName);

        String k1 = "The error was caused by the code\n"
                + "AverageSensor ka = new AverageSensor();\n"
                + "ka.readings();\n";

        Sensor ka = newAverageSensor();

        assertTrue("Please add the method 'public List<Integer> readings()' for the class AverageSensor!\n",
                classRef.method(ka, "readings").returning(List.class).takingNoParams().isPublic());

        classRef.method(ka, "readings").returning(List.class).takingNoParams().withNiceError(k1).invoke();
    }

    @Test
    @Points("11-12.4")
    public void AverageSensorMethodReadingsWork() throws Throwable {
        String klassName = "application.AverageSensor";
        Reflex.ClassRef<Object> classRef = Reflex.reflect(klassName);

        Sensor sensor1 = newTemperatureSensor();

        String code = ""
                + "AverageSensor ka = new AverageSensor();\n"
                + "ka.addSensor( new StandardSensor(3) );\n"
                + "ka.addSensor( new StandardSensor(7) );\n"
                + "ka.readings();\n";

        Sensor ka = newAverageSensor();

        classRef.method(ka, "addSensor").returningVoid().taking(Sensor.class).invoke(newStandardSensor(3));
        classRef.method(ka, "addSensor").returningVoid().taking(Sensor.class).invoke(newStandardSensor(7));

        assertTrue("If no readings have been taken, an empty list should be returned.\n"
                + "Instead a null was returned in response to the code:\n" + code, classRef.method(ka, "readings").returning(List.class).takingNoParams().withNiceError(code).invoke() != null);
        assertTrue("The returned list should have been empty with the code:\n" + code, classRef.method(ka, "readings").returning(List.class).takingNoParams().withNiceError(code).invoke().isEmpty());

        classRef.method(ka, "read").returning(int.class).takingNoParams().withNiceError(code).invoke();

        code = ""
                + "AverageSensor ka = new AverageSensor();\n"
                + "ka.addSensor( new StandardSensor(3) );\n"
                + "ka.addSensor( new StandardSensor(7) );\n"
                + "ka.read();\n"
                + "ka.readings();\n";
        assertTrue("Returned null with the code: \n" + code, classRef.method(ka, "readings").returning(List.class).takingNoParams().withNiceError(code).invoke() != null);
        List l = classRef.method(ka, "readings").returning(List.class).takingNoParams().withNiceError(code).invoke();
        assertTrue("The list should have the lenght of 1 with the code:\n" + code
                + "\nInstead it was: " + l, l.size() == 1);

        assertTrue("The list should have included only the value 5 with the code:" + code + ""
                + "\nInstead it included: " + l, classRef.method(ka, "readings").returning(List.class).takingNoParams().withNiceError(code).invoke().get(0).equals(Integer.valueOf(5)));

        code = ""
                + "AverageSensor ka = new AverageSensor();\n"
                + "ka.addSensor( new StandardSensor(3) );\n"
                + "ka.addSensor( new StandardSensor(7) );\n"
                + "ka.read();\n"
                + "ka.read();\n"
                + "ka.read();\n"
                + "ka.readings();\n";

        classRef.method(ka, "read").returning(int.class).takingNoParams().withNiceError(code).invoke();
        classRef.method(ka, "read").returning(int.class).takingNoParams().withNiceError(code).invoke();

        assertTrue("Null was returned with the code:\n" + code, classRef.method(ka, "readings").returning(List.class).takingNoParams().withNiceError(code).invoke() != null);
        assertTrue("The list should have the lenght of 3 with the code\n" + code + ""
                + "\nInstead it was: " + l, classRef.method(ka, "readings").returning(List.class).takingNoParams().withNiceError(code).invoke().size() == 3);
        l = classRef.method(ka, "readings").returning(List.class).takingNoParams().withNiceError(code).invoke();
        assertTrue("The returned list should have only included 3 times the number 5 with the code: " + code + ""
                + "\nInstead it included: " + l, l.get(0).equals(Integer.valueOf(5)) && l.get(1).equals(Integer.valueOf(5)) && l.get(2).equals(Integer.valueOf(5)));
    }

    /*
     *
     */
    private void sanitezationCheck(String klassName, int n, String m) throws SecurityException {
        Field[] fields = ReflectionUtils.findClass(klassName).getDeclaredFields();

        for (Field field : fields) {
            assertFalse("You shouldn't need \"static variables\", please delete from the class " + s(klassName) + " the variable " + toField(field.toString(), s(klassName)), field.toString().contains("static") && !field.toString().contains("final"));
            assertTrue("all the classes variables should be private, but from the class " + s(klassName) + " a non-private variable was found: " + toField(field.toString(), klassName), field.toString().contains("private"));
        }

        if (fields.length > 1) {
            int var = 0;
            for (Field field : fields) {
                if (!field.toString().contains("final")) {
                    var++;
                }
            }
            assertTrue("The class " + s(klassName) + " shouldn't need other variables than " + m + ", please work around the extra variables", var <= n);
        }
    }

    private String toField(String toString, String klassName) {
        return toString.replace(klassName + ".", "").replace("java.lang.", "").replace("java.util.", "");
    }

    private String s(String klassName) {
        return klassName.substring(klassName.lastIndexOf(".") + 1);
    }
}
