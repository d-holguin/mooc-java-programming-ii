
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Field;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestUtils {

    public static boolean sisaltaa(String palautus, String... oletetutArvot) {
        for (String arvo : oletetutArvot) {
            if (!palautus.contains(arvo)) {
                return false;
            }
        }

        return true;
    }

    public static void saniteettitarkastus(String klassName, int n, String m) throws SecurityException {
        Field[] kentat = ReflectionUtils.findClass(klassName).getDeclaredFields();

        for (Field field : kentat) {
            assertFalse("et tarvitse \"stattisia muuttujia\", poista luokalta " + klassName + " muuttuja " + kentta(field.toString(), klassName), field.toString().contains("static") && !field.toString().contains("final"));
            assertTrue("luokan kaikkien oliomuuttujien näkyvyyden tulee olla private, muuta luokalta " + klassName + " löytyi: " + kentta(field.toString(), klassName), field.toString().contains("private"));
        }

        if (kentat.length > 1) {
            int var = 0;
            for (Field field : kentat) {
                if (!field.toString().contains("final")) {
                    var++;
                }
            }
            assertTrue("et tarvitse " + klassName + "-luokalle kuin " + m + ", poista ylimääräiset", var <= n);
        }
    }

    public static String kentta(String toString, String klassName) {
        return toString.replace(klassName + ".", "").replace("java.lang.", "").replace("java.util.", "");
    }

    public static String s(String klassName) {
        String[] pts = klassName.split("\\.");
        return pts[pts.length - 1];
    }
}
