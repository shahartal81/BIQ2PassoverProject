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
        PLAYER_BOOKMARK_EACH_STEP,
        PLAYER_RULE_SET
    }

    private int maxSteps;
    private int rows;
    private int cols;
    private PlayerTypes playerInd;

    @Parameterized.Parameters(name = "Test #{index} - Check if Max Steps = {0}, Rows = {1} Columns = {2} then Player = {3}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {3000, 15, 15, PlayerTypes.PLAYER_BOOKMARK_EACH_STEP},
                {50, 5, 5, PlayerTypes.PLAYER_RULE_SET}
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
            case PLAYER_BOOKMARK_EACH_STEP:
                Assert.assertTrue(player instanceof PlayerBookmarkEachStep);
                return;
            case PLAYER_RULE_SET:
                Assert.assertTrue(player instanceof PlayerRuleSet);
                return;
            default:
                Assert.fail("Player not supported!");
        }
    }
}