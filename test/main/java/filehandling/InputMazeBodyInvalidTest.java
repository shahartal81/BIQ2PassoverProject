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
public class InputMazeBodyInvalidTest {
    private List<String> result = new ArrayList<>();
    MazeParser testSubject = new MazeParser();

    @Before
    public void init(){
       MazeTestData.initEmptyBody(result,testSubject);
    }

    @Parameters(name = "{index}: given rows({0}, cloumns {1}), expected - invalid maze")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                //invalid cases
                {3, 4, Arrays.asList("####",
                                     "# ##",
                                     " # #")},
                {3, 4, Arrays.asList("####",
                                     "#@##",
                                     " # #")},
                {3, 4, Arrays.asList("####",
                                     "# ##",
                                     " #$#")},
                {3, 4, Arrays.asList("#$##",
                                     "# ##",
                                     " #$#")},


        });
    }

    private int rows;
    private int cols;
    private List<String>mazeBody;

    public InputMazeBodyInvalidTest(int rows, int cols, List<String>mazeBody) {
        this.rows = rows;
        this.cols = cols;
        this.mazeBody = mazeBody;
    }


    @Test
    public void readFromFileMaxStepsRowsColsValidTest() {
        testSubject.getMazeDefinition().addAll(mazeBody);
        Assert.assertFalse(testSubject.isMazeValid(rows,cols));
    }
}
