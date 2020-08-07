

import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.junit.Rule;

public class LiteratureTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    @Points("10-14.1")
    public void areBooksAndAmountPrinted1() {
        String in = "aapinen\n3\nkukkonen\n4\npaapinen\n5\nkiukkunen\n1\nukkonen\n2\n\n";
        final List<String> input = Arrays.asList(in.toLowerCase().split("\n"));

        io.setSysIn(in);

        MainProgram.main(new String[]{});

        List<String> lines = lines();

        Optional<String> books = lines.stream().filter(r -> r.contains("5 books in total.")).findFirst();
        assertTrue("When the program gets five books as input, it is expected that the output would contain the String \"5 books in total\". This didn't happen. The input was:\n" + input, books.isPresent());

        lines = lines.subList(lines.indexOf(books.get()), lines.size());

        Set<String> namesFound = new HashSet<>();
        namesFound.add("aapinen");
        namesFound.add("kukkonen");
        namesFound.add("paapinen");
        namesFound.add("kiukkunen");
        namesFound.add("ukkonen");

        lines.stream().forEach(line -> {
            for (String nimi : new ArrayList<>(namesFound)) {
                if (line.contains(nimi)) {
                    namesFound.remove(nimi);
                    return;
                }
            }
        });

        assertTrue("All books given should be found. This was not the case with: " + namesFound + "\nThe input was:\n" + input, namesFound.isEmpty());
    }

    @Test
    @Points("10-14.2")
    public void orderByAgeRecommendation() {
        String in = "Aapinen1\n30\nKukkonen1\n40\nAapinen2\n50\nkukkonen2\n10\nKukkonen3\n20\nAapinen3\n60\n\n";
        final List<String> input = Arrays.asList(in.toLowerCase().split("\n"));

        io.setSysIn(in);

        MainProgram.main(new String[]{});

        List<String> lines = lines();

        Optional<String> books = lines.stream().filter(r -> r.contains("6 books in total.")).findFirst();
        assertTrue("When the program gets 6 books as input, it is expected that the output would contain the String \"6 books in total\". This didn't happen. The input was:\n" + input, books.isPresent());

        lines = lines.subList(lines.indexOf(books.get()), lines.size());

        Map<String, Integer> biggerYears = new TreeMap<>();
        biggerYears.put("10", 5);
        biggerYears.put("20", 4);
        biggerYears.put("30", 3);
        biggerYears.put("40", 2);
        biggerYears.put("50", 1);
        biggerYears.put("60", 0);

        for (String line : lines) {

            for (String year : new ArrayList<>(biggerYears.keySet())) {

                if (line.contains(year)) {
                    int expectedBiggerYearsLeft = biggerYears.remove(year);

                    if (biggerYears.size() > expectedBiggerYearsLeft) {
                        fail("It seems like the books are not in order based on the age recommendation correctly. The input was:\n" + input + "\n and the output was:\n" + lines);
                    }
                }
            }

        }

        assertTrue("All the given books to the program should be printed. Any of the books with the following age recommendations were not:\n" + biggerYears.keySet(), biggerYears.isEmpty());
    }

    @Test
    @Points("10-14.3")
    public void orderByAgeRecommendationAndName1() {
        String in = "aapinen\n1990\nsorsanen\n1991\nkukkonen\n1989\n\n";
        final List<String> input = Arrays.asList(in.toLowerCase().split("\n"));

        io.setSysIn(in);

        MainProgram.main(new String[]{});

        List<String> lines = lines();

        Optional<String> books = lines.stream().filter(r -> r.contains("3 books in total.")).findFirst();
        //if(true){throw new Error(lines.toString());}
        assertTrue("When the program gets 3 books as input, it is expected that the output would contain the String \"3 books in total\". This didn't happen. The input was:\n" + input, books.isPresent());

        lines = lines.subList(lines.indexOf(books.get()), lines.size());

        Set<String> namesFound = new HashSet<>();
        namesFound.add("aapinen");
        namesFound.add("sorsanen");
        namesFound.add("kukkonen");

        for (String line : lines) {
            if (line.contains("aapinen")) {
                namesFound.remove("aapinen");
                assertTrue("When 'aapinen' was met, there were still unmet book names: " + namesFound, namesFound.size() == 1 && namesFound.contains("sorsanen"));
            }

            for (String nimi : new HashSet<>(namesFound)) {
                if (line.contains(nimi)) {
                    namesFound.remove(nimi);
                }
            }

        }

        assertTrue("All the book names should be printed, but at least one wasn't: " + namesFound, namesFound.isEmpty());
    }
    
    @Test
    @Points("10-14.3")
    public void orderByAgeRecommendationAndName2() {
        String in = "bbbb\n1\naaaa\n1\ncccc\n1\ndddd\n0\n\n";
        final List<String> input = Arrays.asList(in.toLowerCase().split("\n"));

        io.setSysIn(in);

        MainProgram.main(new String[]{});

        List<String> lines = lines();

        Optional<String> books = lines.stream().filter(r -> r.contains("4 books in total.")).findFirst();
        assertTrue("When the program gets 4 books as input, it is expected that the output would contain the String \"4 books in total\". This didn't happen. The input was:\n" + input, books.isPresent());

        lines = lines.subList(lines.indexOf(books.get()), lines.size());
        
        List<String> expectedOrder = new ArrayList<>();
        expectedOrder.add("dddd");
        expectedOrder.add("aaaa");
        expectedOrder.add("bbbb");
        expectedOrder.add("cccc");

        Set<String> seen = new HashSet<>();

        for (String line : lines) {
            
            String seen_current = null;
            
            for (String expected : new ArrayList<>(expectedOrder)) {
                if(line.contains(expected)) {
                    seen_current = expected;
                    break;
                }
            }
            
            if(seen_current == null) {
                continue;
            }
            
            if(expectedOrder.indexOf(seen_current) != 0) {
                fail("When the input is:\n" + in + "\nThe expected order is\ndddd, aaaa, bbbb, cccc.\nAt runtime " + seen_current + " came too soon in the list.");
            }
            
            expectedOrder.remove(seen_current);
        }

        assertTrue("All the book names should be printed, but at least one wasn't: " + expectedOrder, expectedOrder.isEmpty());
    }

    private List<String> lines() {
        return Arrays.asList(io.getSysOut().toLowerCase().trim().split("\n"));
    }
}
