package filehandling;

import org.junit.Assert;
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
public class InputDataTest {

    private List<String> result = new ArrayList<>();

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
    }


    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { "MaxSteps = 10", 10, "MaxSteps", 2}, { "MaxSteps=10", 10, "MaxSteps", 2 }, { "     MaxSteps    =   10   ", 10, "MaxSteps", 2 },
                { "MaxSteps = 10", 10, "MaxSteps", 2}
        });
    }



    private String line;
    private int expected;
    private String key;
    private int lineNumber;

    public InputDataTest(String maxSteps, int expected, String key, int lineNumber) {
        this.line = maxSteps;
        this.expected = expected;
        this.key = key;
        this.lineNumber = lineNumber;
    }


    @Test
    public void readFromFileMaxStepsRowsColsValidTest() {
        int index = lineNumber-1;
        result.set(index, line);

        MazeParser testSubject = new MazeParser();
        testSubject.setMazeDefinition(result);

        Assert.assertEquals(expected, testSubject.numberOf(key, lineNumber));
    }
}
