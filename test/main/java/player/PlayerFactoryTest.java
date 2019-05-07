package player;

import additionalclasses.Position;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Arrays;
import java.util.Collection;

import static org.mockito.Mockito.when;

@RunWith(Parameterized.class)
public class PlayerFactoryTest {

    private enum PlayerTypes {
        PLAYER_SIMPLE,
        PLAYER_VERY_ADVANCED,
        PLAYER_BOOKMARK_EACH_STEP
    }

    private int maxSteps;
    private int rows;
    private int cols;
    private PlayerTypes playerInd;

    @Parameterized.Parameters(name = "Test #{index} - Check if Max Steps = {0}, Rows = {1} Columns = {2} then Player = {3}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {30, 3, 3, PlayerTypes.PLAYER_SIMPLE},
                {30, 10, 10, PlayerTypes.PLAYER_VERY_ADVANCED},
                {3000, 15, 15, PlayerTypes.PLAYER_BOOKMARK_EACH_STEP}
        });
    }

    public PlayerFactoryTest(int m, int r, int c, PlayerTypes ind) {
        this.maxSteps = m;
        this.rows = r;
        this.cols = c;
        this.playerInd = ind;
    }

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private Position position;

    @Test
    public void testPlayerFactory(){
        when(position.getColumn()).thenReturn(cols);
        when(position.getRow()).thenReturn(rows);
        PlayerFactory playerFactory = new PlayerFactory();
        Player player = playerFactory.createPlayer(new Position(position.getRow(), position.getColumn()), maxSteps);
        switch (playerInd){
            case PLAYER_SIMPLE:
                Assert.assertTrue(player instanceof PlayerSimple);
                return;
            case PLAYER_VERY_ADVANCED:
                Assert.assertTrue(player instanceof PlayerAdvanced);
                return;
            case PLAYER_BOOKMARK_EACH_STEP:
                Assert.assertTrue(player instanceof PlayerBookmarkEachStep);
                return;
            default:
                Assert.fail("Player not supported!");
        }
    }
}