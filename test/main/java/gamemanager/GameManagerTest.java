package gamemanager;

import additionalclasses.Position;
import enums.Move;
import filehandling.MazeFileReader;
import filehandling.MazeParser;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import player.Player;
import player.PlayerFactory;

import java.io.File;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GameManagerTest {

    private gamemanager.GameManager gameManager;
    private String mazeTestFilePath = "test/resources/maze.txt";
    private File mazeFile = new File(mazeTestFilePath);
    private Position playerPosition;
    private Position expectedPosition;
    private int bookmarkSequence;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private Player player;

    @Mock
    private PlayerFactory playerFactory;

    @Before
    public void setUp() {
        MazeFileReader reader = new MazeFileReader();
        List<String> mazeDefinition = reader.readFromFile(mazeFile);
        when(playerFactory.createPlayer(any(),anyInt())).thenReturn(player);
        MazeParser mazeParser = new MazeParser();
        gameManager = new GameManager(playerFactory, mazeParser.getMaze(mazeDefinition));
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

    @Test
    public void mazeIsSolvedSuccessTest(){
        gameManager.movePlayer(Move.UP);
        gameManager.movePlayer(Move.LEFT);
        Assert.assertTrue(gameManager.getIsSolved());
    }

    @Test
    public void mazeIsntSolvedAfterMaxSteps(){
        Mockito.when(player.move()).thenReturn(Move.LEFT);
        Assert.assertFalse(gameManager.getIsSolved());
    }

    @Test
    public void increaseUsedStepsOnEachMoveTest(){
        int usedSteps = gameManager.getUsedSteps();
        gameManager.movePlayer(Move.UP);
        usedSteps++;
        Assert.assertEquals(usedSteps, gameManager.getUsedSteps());
        gameManager.movePlayer(Move.RIGHT);
        usedSteps++;
        gameManager.movePlayer(Move.RIGHT);
        usedSteps++;
        Assert.assertEquals(usedSteps, gameManager.getUsedSteps());
        gameManager.movePlayer(Move.BOOKMARK);
        usedSteps++;
        Assert.assertEquals(usedSteps, gameManager.getUsedSteps());
    }

    @Test
    public void bookmarkSequenceIsIncreasedTest(){
        bookmarkSequence = gameManager.getBookmarkSeqNumber();
        gameManager.movePlayer(Move.BOOKMARK);
        bookmarkSequence++;
        Assert.assertEquals(bookmarkSequence, gameManager.getBookmarkSeqNumber());
    }

    @Test
    public void bookmarkMapIsUpdatedTest(){
        playerPosition = gameManager.getPlayerPosition();
        gameManager.movePlayer(Move.BOOKMARK);
        Assert.assertTrue(gameManager.getBookmarksMap().containsKey(playerPosition));
    }

    @Test
    public void bookmarkPositionThatWasAlreadyBookmarkedTest(){
        bookmarkSequence = gameManager.getBookmarkSeqNumber();
        playerPosition = gameManager.getPlayerPosition();
        gameManager.movePlayer(Move.BOOKMARK);
        bookmarkSequence++;
        gameManager.movePlayer(Move.LEFT);
        gameManager.movePlayer(Move.BOOKMARK);
        bookmarkSequence++;
        gameManager.movePlayer(Move.RIGHT);
        gameManager.movePlayer(Move.BOOKMARK);
        bookmarkSequence++;
        Assert.assertEquals(bookmarkSequence, (int) gameManager.getBookmarksMap().get(playerPosition));
    }
}