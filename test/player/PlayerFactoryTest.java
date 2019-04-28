package player;

import additionalclasses.Position;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.mockito.Mockito.when;

public class PlayerFactoryTest {

    private PlayerFactory playerFactory;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private Position position;

    @Test
    public void testPlayerSimple(){
        when(position.getColumn()).thenReturn(3);
        when(position.getRow()).thenReturn(3);
        int maxSteps = 30;
        playerFactory = new PlayerFactory();
        Player player = playerFactory.createPlayer(new Position(position.getRow(), position.getColumn()), maxSteps);
        Assert.assertTrue(player instanceof PlayerSimple);
    }

    @Test
    public void testPlayerAdvanced(){
        when(position.getColumn()).thenReturn(5);
        when(position.getRow()).thenReturn(5);
        int maxSteps = 30;
        playerFactory = new PlayerFactory();
        Player player = playerFactory.createPlayer(new Position(position.getRow(), position.getColumn()), maxSteps);
        Assert.assertTrue(player instanceof PlayerAdvanced);
    }

    @Test
    public void testPlayerVeryAdvanced(){
        when(position.getColumn()).thenReturn(10);
        when(position.getRow()).thenReturn(10);
        int maxSteps = 30;
        playerFactory = new PlayerFactory();
        Player player = playerFactory.createPlayer(new Position(position.getRow(), position.getColumn()), maxSteps);
        Assert.assertTrue(player instanceof PlayerVeryAdvanced);
    }

    @Test
    public void testPlayerBookmarkEachStep(){
        when(position.getColumn()).thenReturn(10);
        when(position.getRow()).thenReturn(10);
        int maxSteps = 3000;
        playerFactory = new PlayerFactory();
        Player player = playerFactory.createPlayer(new Position(position.getRow(), position.getColumn()), maxSteps);
        Assert.assertTrue(player instanceof PlayerBookmarkEachStep);
    }

}