package main.java.gamemanager;

import main.java.filehandling.InputFileParser;
import main.java.additionalclasses.Position;
import main.java.enums.Move;
import org.junit.*;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import main.java.player.Player;
import main.java.player.PlayerFactory;
import java.io.File;

import java.io.BufferedWriter;
import java.io.IOException;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GameManagerTest {

    private GameManager gameManager;
    private BufferedWriter fileWriter = null;
    private String mazeTestFilePath = "test/resources/maze.txt";
    private File mazeFile = new File(mazeTestFilePath);
    private Position playerPosition;
    private Position expectedPosition;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    private TemporaryFolder folder = new TemporaryFolder();

    @Mock
    private Player player;

    @Mock
    private PlayerFactory playerFactory;

    @Before
    public void setUp() throws IOException {
//        folder.create();
//        File createdFile = folder.newFile("test.txt");
//        fileWriter = new BufferedWriter(new FileWriter(createdFile));

        when(playerFactory.createPlayer(any(),anyInt())).thenReturn(player);
        InputFileParser ifp = new InputFileParser();

        gameManager = new GameManager(playerFactory, ifp.getMaze(mazeFile));
    }

    @After
    public void teardown() throws IOException {
        if (fileWriter != null)
        {
            fileWriter.close();
        }
        fileWriter = null;
    }


    @Test
    public void moveLeftTest(){
        gameManager.movePlayer(Move.LEFT);
        playerPosition = gameManager.getPlayerPosition();
        expectedPosition = new Position(2,1);
        Assert.assertEquals(playerPosition,expectedPosition);
    }

    @Test
    public void moveRightTest(){
        gameManager.movePlayer(Move.RIGHT);
        playerPosition = gameManager.getPlayerPosition();
        expectedPosition = new Position(2,3);
        Assert.assertEquals(playerPosition,expectedPosition);
    }

    @Test
    public void moveUpTest(){
        gameManager.movePlayer(Move.UP);
        playerPosition = gameManager.getPlayerPosition();
        expectedPosition = new Position(1,2);
        Assert.assertEquals(playerPosition,expectedPosition);
    }

    @Test
    public void moveDownTest(){
        gameManager.movePlayer(Move.DOWN);
        playerPosition = gameManager.getPlayerPosition();
        expectedPosition = new Position(3,2);
        Assert.assertEquals(playerPosition,expectedPosition);
    }

    @Test
    public void moveLeftAndDownTest(){
        gameManager.movePlayer(Move.DOWN);
        gameManager.movePlayer(Move.LEFT);
        playerPosition = gameManager.getPlayerPosition();
        expectedPosition = new Position(3,1);
        Assert.assertEquals(playerPosition,expectedPosition);
    }

    @Test
    public void hitWallTest(){
        gameManager.movePlayer(Move.DOWN);
        gameManager.movePlayer(Move.LEFT);
        gameManager.movePlayer(Move.LEFT);
        Mockito.doAnswer((answer) -> {return null;}).when(player).hitWall();
        verify(player, Mockito.times(1)).hitWall();
    }

    @Test
    public void moveRightToTheOtherSide(){
        gameManager.movePlayer(Move.RIGHT);
        gameManager.movePlayer(Move.RIGHT);
        gameManager.movePlayer(Move.RIGHT);
        playerPosition = gameManager.getPlayerPosition();
        expectedPosition = new Position(2,0);
        Assert.assertEquals(playerPosition,expectedPosition);
    }

    @Test
    public void moveDownToTheOtherSide(){
        gameManager.movePlayer(Move.DOWN);
        gameManager.movePlayer(Move.DOWN);
        gameManager.movePlayer(Move.DOWN);
        playerPosition = gameManager.getPlayerPosition();
        expectedPosition = new Position(0,2);
        Assert.assertEquals(playerPosition,expectedPosition);
    }


}