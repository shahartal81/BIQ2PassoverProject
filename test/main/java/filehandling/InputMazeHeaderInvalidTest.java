package filehandling;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RunWith(Parameterized.class)
public class InputMazeHeaderInvalidTest {
    private static List<String> result = new ArrayList<>();
    private MazeParser testSubject = new MazeParser();

    @BeforeClass
    public static void setUp(){
        result.add("My maze");
        result.add("MaxSteps = 10");
        result.add("Rows = 4");
        result.add("Cols = 12");
        result.add("###  ##   ##");
        result.add("# @  # #   #");
        result.add("##  $  ##  #");
        result.add("### #  #####");
    }

    @Before
    public void init(){
        int index = lineNumber-1;
        result.set(index, input);
        testSubject.setMazeDefinition(result);
        ErrorsSingleton.instance().clean();
    }

    @Parameters(name = "{index}: given in file({0}), expected {1}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                //invalid cases
                { "MaxSteps == 10", 0, "MaxSteps", 2,
                        "Bad maze file header: expected in line 2 - MaxSteps = <num>"  + "\n" + " got: " + "MaxSteps == 10"},
                { "MaxSteps = = 10", 0, "MaxSteps", 2,
                        "Bad maze file header: expected in line 2 - MaxSteps = <num>"  + "\n" + " got: " + "MaxSteps = = 10"},
                { "Max Steps = 10", 0, "MaxSteps", 2,
                        "Bad maze file header: expected in line 2 - MaxSteps = <num>"  + "\n" + " got: " + "Max Steps = 10"},
                { " = 10", 0, "MaxSteps", 2,
                        "Bad maze file header: expected in line 2 - MaxSteps = <num>"  + "\n" + " got: " + "= 10"},
                { "MaxSteps = 1 0", 0, "MaxSteps", 2,
                        "Bad maze file header: expected in line 2 - MaxSteps = <num>"  + "\n" + " got: " + "MaxSteps = 1 0"},
                { "MaxSteps = -10", 0, "MaxSteps", 2,
                        "Bad maze file header: expected in line 2 - MaxSteps = <num>"  + "\n" + " got: " + "MaxSteps = -10"},
                { "MaxSteps = ten", 0, "MaxSteps", 2,
                        "Bad maze file header: expected in line 2 - MaxSteps = <num>"  + "\n" + " got: " + "MaxSteps = ten"},
                { "MaxSteps 10", 0, "MaxSteps", 2,
                        "Bad maze file header: expected in line 2 - MaxSteps = <num>"  + "\n" + " got: " + "MaxSteps 10"},
                { "MaxSteps", 0, "MaxSteps", 2,
                        "Bad maze file header: expected in line 2 - MaxSteps = <num>"  + "\n" + " got: " + "MaxSteps"},
                { "", 0, "MaxSteps", 2,
                        "Bad maze file header: expected in line 2 - MaxSteps = <num>"  + "\n" + " got: " + ""},

                //TODO - add similar tests for rows and columns
        });
    }

    private String input;
    private int expectedNum;
    private String key;
    private int lineNumber;
    private String error;


    public InputMazeHeaderInvalidTest(String input, int expectedNum, String key, int lineNumber, String error) {
        this.input = input;
        this.expectedNum = expectedNum;
        this.key = key;
        this.lineNumber = lineNumber;
        this.error = error;
    }

    @Test
    public void readFromFileMaxStepsRowsColsInvalidTest() {
        Assert.assertEquals(expectedNum, testSubject.numberOf(key, lineNumber));
        List<String> errors = ErrorsSingleton.instance().getErrorsList();
        Assert.assertTrue(errors.contains(error));
    }
}
