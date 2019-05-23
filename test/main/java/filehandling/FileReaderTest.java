package filehandling;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

public class FileReaderTest {

    private FileReader testSubject;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp(){
        testSubject = new FileReader(new ArrayList<>());
    }

    @Test
    public void readFromFileOneLineTest() throws IOException {

        BufferedReader bufferedReader = Mockito.mock(BufferedReader.class);
        when(bufferedReader.readLine()).thenReturn
                ("Simple maze", (String) null);
        List<String> result = testSubject.readFromFile(bufferedReader);
        Assert.assertEquals(1, result.size());
        Assert.assertEquals("Simple maze", result.get(0));
    }

    @Test
    public void readFromFileMultipleLinesTest() throws IOException {
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
        BufferedReader bufferedReader = Mockito.mock(BufferedReader.class);
        when(bufferedReader.readLine()).thenThrow(new FileNotFoundException());
        testSubject.readFromFile(bufferedReader);
    }
}
