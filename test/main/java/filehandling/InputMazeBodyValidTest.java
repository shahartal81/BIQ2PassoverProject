package filehandling;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RunWith(Parameterized.class)
public class InputMazeBodyValidTest {
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

       testSubject.setMazeDefinition(result);
       ErrorsSingleton.instance().clean();
    }

    @Parameters(name = "{index}: given rows({0}, cloumns {1}), expected - valid maze")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                //valid cases
                {4, 12},
                {3, 10},
        });
    }

    private int rows;
    private int cols;



    public InputMazeBodyValidTest(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
    }


    @Test
    public void readFromFileMaxStepsRowsColsValidTest() {
        Assert.assertTrue(testSubject.isMazeValid(rows,cols));
    }
}
