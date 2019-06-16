package gamemanager;

import additionalclasses.Maze;
import filehandling.ErrorsSingleton;
import filehandling.MazeDefinitionParser;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.withSettings;

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
        if(outputFile != null){
            outputFile.delete();
        }
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

    @Test
    public void printResultsTest() throws UnsupportedEncodingException {
        PrintStream previousPrintStream = System.out;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        String data;
        try (PrintStream ps = new PrintStream(byteArrayOutputStream, true, "UTF-8")) {
            System.setOut(ps);
            Maze maze1 = mock(Maze.class);
            GameResult gameResult1 = mock(GameResult.class, withSettings().defaultAnswer(Mockito.RETURNS_DEEP_STUBS));
            GameResult gameResult2 = mock(GameResult.class, withSettings().defaultAnswer(Mockito.RETURNS_DEEP_STUBS));
            GameResult gameResult3 = mock(GameResult.class, withSettings().defaultAnswer(Mockito.RETURNS_DEEP_STUBS));
            when(gameResult1.getPlayer().getPlayerName()).thenReturn("Player 1");
            when(gameResult2.getPlayer().getPlayerName()).thenReturn("Player 2");
            when(gameResult3.getPlayer().getPlayerName()).thenReturn("Player 3");
            when(gameResult1.isSolved()).thenReturn(true);
            when(gameResult2.isSolved()).thenReturn(false);
            when(gameResult3.isSolved()).thenReturn(true);
            when(gameResult1.getUsedSteps()).thenReturn(15);
            when(gameResult2.getUsedSteps()).thenReturn(25);
            when(gameResult3.getUsedSteps()).thenReturn(55);
            GameManager gameManager1 = mock(GameManager.class);
            GameManager gameManager2 = mock(GameManager.class);
            GameManager gameManager3 = mock(GameManager.class);
            when(gameManager1.getGameResult()).thenReturn(gameResult1);
            when(gameManager2.getGameResult()).thenReturn(gameResult2);
            when(gameManager3.getGameResult()).thenReturn(gameResult3);
            when(maze1.getMazeName()).thenReturn("maze1");
            when(gameManager1.getMaze()).thenReturn(maze1);
            when(gameManager2.getMaze()).thenReturn(maze1);
            when(gameManager3.getMaze()).thenReturn(maze1);
            gameLoader.gameManagers = Arrays.asList(gameManager1, gameManager2, gameManager3);
            gameLoader.printResults();
            data = new String(byteArrayOutputStream.toByteArray(), StandardCharsets.UTF_8);
        } finally {
            System.setOut(previousPrintStream);
        }
        System.out.println(data);
        String[] lines = data.split(System.lineSeparator());
        for (int i = 0; i < lines.length; i++) {
            lines[i] = lines[i].replace("\t", "");
        }
        Assert.assertEquals(" Maze Name      | Player 1       | Player 2       | Player 3       ", lines[0]);
        Assert.assertEquals(" maze1          | ✔ - 15         | X              | ✔ - 55         ", lines[1]);
    }

    @Test
    public void parseMazeBadPathTest() {
        paths = new String[]{"test/resources/validInputFile.txt", "test/resources/validOutputFile.txt"};
        inputFile = new File(paths[0]);
        outputFile = new File(paths[1]);
        when(fileParser.getMaze(any())).thenReturn(null);
        gameLoader.parseMaze(paths[0], fileParser);
        Assert.assertEquals("Mazes list should be empty", 0, gameLoader.getMazesNumber());
    }

    @Test
    public void parseMazesTest() {
        paths = new String[]{"test/resources/validInputFile.txt", "test/resources/validOutputFile.txt"};
        List<String> mazes = new ArrayList<>();
        mazes.add("test/resources/maze1.txt");
        mazes.add("test/resources/maze2.txt");
        inputFile = new File(paths[0]);
        outputFile = new File(paths[1]);
        when(fileParser.getMaze(any())).thenReturn(null);
        Mockito.doAnswer((answer) -> null).when(fileParser).getMaze(Mockito.any());
        gameLoader.parseMazes(mazes, fileParser);
        verify(fileParser, Mockito.times(2)).getMaze(Mockito.any());
        Assert.assertEquals("Mazes list should be empty", 0, gameLoader.getMazesNumber());
    }
}
