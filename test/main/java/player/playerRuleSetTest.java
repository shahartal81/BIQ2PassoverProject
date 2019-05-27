package player;

import enums.Move;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class playerRuleSetTest {

    private PlayerRuleSet player;
    private List<Move> actualMoves;
    private List<Move> expectedMoves;

    @Before
    public void setUp() {
        player = new PlayerRuleSet();
        actualMoves = new ArrayList<>();
        expectedMoves= new ArrayList<>();
    }

    @Test
    public void firstMove_shouldBeBookmark(){
        actualMoves.add(player.move());
        expectedMoves.add(Move.BOOKMARK);
        Assert.assertEquals(expectedMoves,actualMoves);
    }

    @Test
    public void secondMove_shouldNotBeBookmark(){
        player.move();
        actualMoves.add(player.move());
        expectedMoves.add(Move.RIGHT);
        Assert.assertEquals(expectedMoves,actualMoves);
    }

    @Test
    public void hitWall_shouldMoveDownAndBookmark(){
        player.move();
        player.move();
        player.hitWall();
        actualMoves.add(player.move());
        actualMoves.add(player.move());
        expectedMoves = Arrays.asList(Move.DOWN, Move.BOOKMARK);
        Assert.assertEquals(expectedMoves,actualMoves);
    }

    @Test
    public void hitBookmark(){
        player.move();
        player.move();
    }

    @Test
    public void moveState_shouldMoveRight(){
        player.move();
        player.setState(PlayerRuleSet.State.MOVED);
        Assert.assertEquals(Move.RIGHT, player.move());
    }

    @Test
    public void changedDirectionBookmarkState_shouldMoveRight(){
        player.move();
        player.setState(PlayerRuleSet.State.CHANGED_DIRECTION_AFTER_BOOKMARK);
        Assert.assertEquals(Move.RIGHT, player.move());
    }

    @Test
    public void hitBookmarkState_shouldMoveDown(){
        player.move();
        player.setState(PlayerRuleSet.State.HIT_BOOKMARK);
        Assert.assertEquals(Move.DOWN, player.move());
    }

    @Test
    public void hitWallAfterBookmark_shouldMoveLeft(){
        player.move();
        player.setState(PlayerRuleSet.State.HIT_WALL_AFTER_BOOKMARK);
        Assert.assertEquals(Move.LEFT, player.move());
    }

    @Test
    public void changeRuleSetTest(){
        actualMoves.add(player.move());
        actualMoves.add(player.move());
        actualMoves.add(player.move());
        player.hitWall();
        actualMoves.add(player.move());
        player.hitWall();
        actualMoves.add(player.move());
        actualMoves.add(player.move());
        expectedMoves = Arrays.asList(Move.BOOKMARK, Move.RIGHT,Move.RIGHT, Move.DOWN, Move.LEFT, Move.LEFT);
        Assert.assertEquals(expectedMoves,actualMoves);
    }

}
