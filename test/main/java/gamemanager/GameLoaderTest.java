package main.java.gamemanager;

import main.java.filehandling.FileParser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class GameLoaderTest {

    private File inputFile;
    private File outputFile;
    private GameLoader gameLoader = new GameLoader();
    private String[] paths;
    private List<String> errorsList;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private FileParser fileParser;

    @Before
    public void setUp(){

    }

    @Test
    public void validFilePaths() throws Exception {
        paths = new String[]{"test/resources/validInputFile.txt", "test/resources/validOutputFile.txt"};
        inputFile = new File(paths[0]);
        outputFile = new File(paths[1]);
        try {
            inputFile.createNewFile();
        } catch (IOException e) {
            throw new Exception("failed to create inputFile for test " + e);
        }
        gameLoader.validateAndStartGame(paths, fileParser);
        when(fileParser.getMaze(inputFile)).thenReturn(null);
        errorsList = gameLoader.getErrorsList();
        Assert.assertTrue(errorsList.isEmpty());
        inputFile.delete();
        outputFile.delete();

    }

}
