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
    private MazeParser testSubject = new MazeParser();

    @Before
    public void init(){
       MazeTestData.initEmptyBody(result,testSubject);
    }

    @Parameters(name = "{index}: {3}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                //invalid cases
                {3, 4, Arrays.asList("####",
                                     "# ##",
                                     " # #"), "Missing player and finish"},
                {3, 4, Arrays.asList("####",
                                     "#@##",
                                     " # #"), "Missing finish"},
                {3, 4, Arrays.asList("####",
                                     "# ##",
                                     " #$#"), "Missing player"},
                {3, 4, Arrays.asList("#$##",
                                     "# ##",
                                     " #$#"), "More than one finish"},
                {3, 4, Arrays.asList("#$#@",
                                     "# ##",
                                     " #@#"), "More than one player"},
                {3, 4, Arrays.asList("# ## #",
                                     "# ## @",
                                     "# ### ",
                                     "# ##  ",
                                     "#$##  "), "Player and finish in extra column and extra row"},
                {3, 4, Arrays.asList("####",
                                     "#@##",
                                     "$# ?"), "Wrong character"},


        });
    }

    private int rows;
    private int cols;
    private List<String>mazeBody;
    private String description;

    public InputMazeBodyInvalidTest(int rows, int cols, List<String>mazeBody, String description) {
        this.rows = rows;
        this.cols = cols;
        this.mazeBody = mazeBody;
        this.description = description;
    }


    @Test
    public void mazeBodyInvalidTest() {
        testSubject.getMazeDefinition().addAll(mazeBody);
        Assert.assertFalse(testSubject.isMazeValid(rows,cols));
    }
}
