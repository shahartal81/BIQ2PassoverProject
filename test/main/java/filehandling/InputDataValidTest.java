package filehandling;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class InputDataValidTest {
    private List<String> result = new ArrayList<>();
    MazeParser testSubject = new MazeParser();

    @Before
    public void init(){
       result.add("My maze");
       result.add("MaxSteps = 10");
       result.add("Rows = 4");
       result.add("Cols = 12");
       result.add("###  ##   ##");
       result.add("# @  # #   #");
       result.add("##  $  ##  #");
       result.add("### #  #####");

        int index = lineNumber-1;
        result.set(index, input);
        testSubject.setMazeDefinition(result);
        ErrorsSingleton.instance().clean();
    }

    @Parameters(name = "{index}: given in file({0}), expected {1}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                //valid cases
                { "MaxSteps = 10", 10, "MaxSteps", 2},
                { "MaxSteps = 0", 0, "MaxSteps", 2},
                { "MaxSteps=10", 10, "MaxSteps", 2 },
                {"     MaxSteps    =   10   ", 10, "MaxSteps", 2 },
                { "Rows = 4", 4, "Rows", 3},
                { "Rows = 0", 0, "Rows", 3},
                { "Rows=4", 4, "Rows", 3},
                { " Rows    =   4  ", 4, "Rows", 3},
                { "Cols = 12", 12, "Cols", 4},
                { "Cols = 0", 0, "Cols", 4},
                {"Cols=12", 12, "Cols", 4},
                {"   Cols   =   12    ", 12, "Cols", 4}
        });
    }

    private String input;
    private int expectedNum;
    private String key;
    private int lineNumber;
    private String error;


    public InputDataValidTest(String input, int expectedNum, String key, int lineNumber) {
        this.input = input;
        this.expectedNum = expectedNum;
        this.key = key;
        this.lineNumber = lineNumber;
    }


    @Test
    public void readFromFileMaxStepsRowsColsValidTest() {
        Assert.assertEquals(expectedNum, testSubject.numberOf(key, lineNumber));
        List<String> errors = ErrorsSingleton.instance().getErrorsList();
        Assert.assertTrue(errors.isEmpty());
    }
}
