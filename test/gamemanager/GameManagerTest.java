package gamemanager;

import enums.Move;
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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class GameManagerTest {

    private GameManager gameManager;
    private BufferedWriter fileWriter = null;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private Player player;

    @Mock
    private PlayerFactory playerFactory;

    @Before
    public void setUp() throws IOException {
        fileWriter = new BufferedWriter(new FileWriter(new File("test.txt")));

        //TODO think how to send other maxSteps
        when(playerFactory.createPlayer(any(), 300)).thenReturn(player);

        gameManager = new GameManager(fileWriter, playerFactory);
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
    public void happy(){
        Mockito.when(player.move()).thenReturn(Move.LEFT);

        boolean isSolved = gameManager.playGame();

        Assert.assertTrue(isSolved);
    }

    @Test
    public void fail(){
        Mockito.when(player.move()).thenReturn(Move.LEFT).thenReturn(Move.RIGHT);

        boolean isSolved = gameManager.playGame();

        Assert.assertFalse(isSolved);
    }

}