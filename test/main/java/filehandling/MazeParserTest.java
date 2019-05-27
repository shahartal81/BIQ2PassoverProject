package filehandling;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;

public class MazeParserTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void parseMazeEmptyFileTest() {
        List<String> mazeDefinition = new ArrayList<>();
        MazeParser testSubject = new MazeParser();
        testSubject.getMaze(mazeDefinition);
        Assert.assertEquals(0, testSubject.getMazeDefinition().size());
        List<String> errors = ErrorsSingleton.instance().getErrorsList();
        Assert.assertTrue(errors.contains("Data in maze input file is insufficient. Maze cannot be created"));
    }

    @Test
    public void parseMazeNotEnoughLinesInFileTest() {
        List<String> mazeDefinition = new ArrayList<>();
        mazeDefinition.add("simple maze");
        MazeParser testSubject = new MazeParser();
        testSubject.getMaze(mazeDefinition);
        List<String> errors = ErrorsSingleton.instance().getErrorsList();
        Assert.assertTrue(errors.contains("Data in maze input file is insufficient. Maze cannot be created"));
    }


    @Test
    public void parseMazeMaxStepsValidTest() {
        MazeParser testSubject = new MazeParser();
        Assert.assertTrue(testSubject.isMaxStepsValid(1));

    }

    @Test
    public void parseMazeMaxStepsInvalidTest() {
        List<String> mazeDefinition = new ArrayList<>();
        mazeDefinition.add("maze test");
        mazeDefinition.add("MaxSteps = -10");
        MazeParser testSubject = new MazeParser();
        testSubject.setMazeDefinition(mazeDefinition);
        Assert.assertFalse(testSubject.isMaxStepsValid(0));
        List<String> errors = ErrorsSingleton.instance().getErrorsList();
        Assert.assertTrue(errors.contains("Bad maze file header: expected in line 2 - MaxSteps bigger than 0 \n" + "got: MaxSteps = -10"));
    }

    @Test
    public void parseMazeRowsColsValidTest() {
        MazeParser testSubject = new MazeParser();
        Assert.assertTrue(testSubject.isRowsColsValid(1,2));
        Assert.assertTrue(testSubject.isRowsColsValid(2,1));
        Assert.assertTrue(testSubject.isRowsColsValid(2,2));
    }

    @Test
    public void parseMazeRowsColsInvalidTest() {
        List<String> result = new ArrayList<>();
        MazeParser testSubject = new MazeParser();
        MazeTestData.init(result,testSubject);

        Assert.assertFalse(testSubject.isRowsColsValid(1,1));
        Assert.assertFalse(testSubject.isRowsColsValid(0,0));
        Assert.assertFalse(testSubject.isRowsColsValid(0,10));
        Assert.assertFalse(testSubject.isRowsColsValid(10,0));
        Assert.assertFalse(testSubject.isRowsColsValid(-1,5));
    }
}
