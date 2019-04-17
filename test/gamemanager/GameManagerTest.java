package gamemanager;

import enums.Move;
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

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class GameManagerTest {

    private GameManager gameManager;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private Player player;

    @Mock
    private PlayerFactory playerFactory;

    @Before
    public void setUp() {

        when(playerFactory.createPlayer(any())).thenReturn(player);

        gameManager = new GameManager(playerFactory);
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