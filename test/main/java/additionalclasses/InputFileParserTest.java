package main.java.additionalclasses;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mockito;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ConnectException;

public class InputFileParserTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void readFromFileOneLineTest() throws IOException {
        InputFileParser testSubject = new InputFileParser();
        BufferedReader bufferedReader = Mockito.mock(BufferedReader.class);
        Mockito.when(bufferedReader.readLine()).thenReturn
                ("Simple maze", null);
        testSubject.readFromFile(bufferedReader);
        Assert.assertEquals(1, testSubject.getResult().size());
        Assert.assertEquals("Simple maze", testSubject.getResult().get(0));
    }

    @Test
    public void readFromFileMultipleLinesTest() throws IOException {
        InputFileParser testSubject = new InputFileParser();
        BufferedReader bufferedReader = Mockito.mock(BufferedReader.class);
        Mockito.when(bufferedReader.readLine()).thenReturn("Simple maze", "MaxSteps = 10", "Rows = 4", "Cols = 10", "##### \t", null);
        testSubject.readFromFile(bufferedReader);
        Assert.assertEquals(5, testSubject.getResult().size());
        Assert.assertEquals("Simple maze", testSubject.getResult().get(0));
        Assert.assertEquals("MaxSteps = 10", testSubject.getResult().get(1));
        Assert.assertEquals("Rows = 4", testSubject.getResult().get(2));
        Assert.assertEquals("Cols = 10", testSubject.getResult().get(3));
        Assert.assertEquals("##### \t", testSubject.getResult().get(4));
    }

    @Test
    public void readFromFileContainEmptyLinesTest() throws IOException {
        InputFileParser testSubject = new InputFileParser();
        BufferedReader bufferedReader = Mockito.mock(BufferedReader.class);
        Mockito.when(bufferedReader.readLine()).thenReturn
                ("\n","Simple maze", "\n", "MaxSteps = 10", "\n", null);
        testSubject.readFromFile(bufferedReader);
        Assert.assertEquals(2, testSubject.getResult().size());
        Assert.assertEquals("Simple maze", testSubject.getResult().get(0));
        Assert.assertEquals("MaxSteps = 10", testSubject.getResult().get(1));
    }

    @Test (expected = FileNotFoundException.class)
    public void readFromFileNonExistingFileTest() throws IOException {
        InputFileParser testSubject = new InputFileParser();
        BufferedReader bufferedReader = Mockito.mock(BufferedReader.class);
        Mockito.when(bufferedReader.readLine()).thenThrow(new FileNotFoundException());
        testSubject.readFromFile(bufferedReader);
    }



}
