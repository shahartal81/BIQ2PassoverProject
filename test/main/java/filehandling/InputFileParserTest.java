package filehandling;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class InputFileParserTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void readFromFileOneLineTest() throws IOException {
        filehandling.FileReader testSubject = new filehandling.FileReader();
        BufferedReader bufferedReader = Mockito.mock(BufferedReader.class);
        when(bufferedReader.readLine()).thenReturn
                ("Simple maze", (String) null);
        List<String> result = testSubject.readFromFile(bufferedReader);
        Assert.assertEquals(1, result.size());
        Assert.assertEquals("Simple maze", result.get(0));
    }

    @Test
    public void readFromFileMultipleLinesTest() throws IOException {
        FileReader testSubject = new FileReader();
        BufferedReader bufferedReader = Mockito.mock(BufferedReader.class);
        when(bufferedReader.readLine()).thenReturn("Simple maze", "MaxSteps = 10", "Rows = 4", "Cols = 10", "##### \t", null);
        List<String> result = testSubject.readFromFile(bufferedReader);
        Assert.assertEquals(5, result.size());
        Assert.assertEquals("Simple maze", result.get(0));
        Assert.assertEquals("MaxSteps = 10", result.get(1));
        Assert.assertEquals("Rows = 4", result.get(2));
        Assert.assertEquals("Cols = 10", result.get(3));
        Assert.assertEquals("##### \t", result.get(4));
    }

    @Test
    public void readFromFileContainEmptyLinesTest() throws IOException {
        FileReader testSubject = new FileReader();
        BufferedReader bufferedReader = Mockito.mock(BufferedReader.class);
        when(bufferedReader.readLine()).thenReturn
                ("\n","Simple maze", "\n", "MaxSteps = 10", "\n", null);
        List<String> result = testSubject.readFromFile(bufferedReader);
        Assert.assertEquals(2, result.size());
        Assert.assertEquals("Simple maze", result.get(0));
        Assert.assertEquals("MaxSteps = 10", result.get(1));
    }

    @Test (expected = FileNotFoundException.class)
    public void readFromFileNonExistingFileTest() throws IOException {
        FileReader testSubject = new FileReader();
        BufferedReader bufferedReader = Mockito.mock(BufferedReader.class);
        when(bufferedReader.readLine()).thenThrow(new FileNotFoundException());
        testSubject.readFromFile(bufferedReader);
    }


    @Test
    public void readFromFileEmptyFileTest() {
        List<String> result = new ArrayList<>();
        MazeParser testSubject = new MazeParser();
        testSubject.getMaze(result);
        Assert.assertEquals(0, testSubject.getMazeDefinition().size());
        List<String> errors = testSubject.getErrorsList();
        Assert.assertTrue(errors.contains("Data in maze input file is insufficient. Maze cannot be created"));
    }

    @Test
    public void readFromFileNotEnoughLinesInFileTest() {
        List<String> result = new ArrayList<>();
        result.add("simple maze");
        MazeParser testSubject = new MazeParser();
        testSubject.getMaze(result);
        List<String> errors = testSubject.getErrorsList();
        Assert.assertTrue(errors.contains("Data in maze input file is insufficient. Maze cannot be created"));
    }

}
