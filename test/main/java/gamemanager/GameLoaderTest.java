package gamemanager;

import filehandling.ErrorsSingleton;
import filehandling.MazeDefinitionParser;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class GameLoaderTest {

    private File inputFile;
    private File outputFile;
    private GameLoader gameLoader = new GameLoader();
    private String[] paths;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private MazeDefinitionParser fileParser;

    @Before
    public void setUp(){
        inputFile = null;
    }

    @After
    public void deleteFile(){
        if(inputFile != null){
            inputFile.delete();
        }
        outputFile.delete();
        ErrorsSingleton.instance().getErrorsList().clear();
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
        gameLoader.validateArguments(paths[0], paths[1]);
        Assert.assertTrue(ErrorsSingleton.instance().getErrorsList().isEmpty());

    }

    @Test
    public void inputFileIsDirectoryTest() throws Exception {
        paths = new String[]{"test/resources", "test/resources/validOutputFile.txt"};
        inputFile = new File(paths[0]);
        outputFile = new File(paths[1]);
        try {
            inputFile.createNewFile();
        } catch (IOException e) {
            throw new Exception("failed to create inputFile for test " + e);
        }
        gameLoader.validateArguments(paths[0], paths[1]);
        List<String> errorsList = ErrorsSingleton.instance().getErrorsList();
        Assert.assertEquals(errorsList.get(0), "Command line argument for maze: " + inputFile + " doesn't lead to a maze file or leads to a file that cannot be opened");
    }

    @Test
    public void inputFileDoesntExistTest() {
        paths = new String[]{"test/resources/validInputFile.txt", "test/resources/validOutputFile.txt"};
        inputFile = new File(paths[0]);
        outputFile = new File(paths[1]);
        gameLoader.validateArguments(paths[0], paths[1]);
        List<String> errorsList = ErrorsSingleton.instance().getErrorsList();
        Assert.assertEquals(errorsList.get(0), "Command line argument for maze: " + inputFile + " doesn't lead to a maze file or leads to a file that cannot be opened");
    }

    @Test
    public void outputFileAlreadyExistsTest() throws Exception {
        paths = new String[]{"test/resources/validInputFile.txt", "test/resources/validOutputFile.txt"};
        inputFile = new File(paths[0]);
        outputFile = new File(paths[1]);
        try {
            inputFile.createNewFile();
        } catch (IOException e) {
            throw new Exception("failed to create inputFile for test " + e);
        }
        try {
            outputFile.createNewFile();
        } catch (IOException e){
            throw new Exception("failed to create outputFile for test" + e);
        }
        when(fileParser.getMaze(any())).thenReturn(null);
        gameLoader.validateArguments(paths[0], paths[1]);
        List<String> errorsList = ErrorsSingleton.instance().getErrorsList();
        Assert.assertEquals(errorsList.get(0), "Command line argument for output file: " + outputFile + " points to a bad path or to a file that already exists");
    }

    @Test
    public void outputFileBadPathTest() {
        String path = "test/kuku/validOutputFile.txt";
        outputFile = new File(path);
        gameLoader.start(path);
        List<String> errorsList = ErrorsSingleton.instance().getErrorsList();
        Assert.assertEquals(errorsList.get(0), "Command line argument for output file: " + outputFile + " points to a bad path or to a file that already exists");
    }
}
