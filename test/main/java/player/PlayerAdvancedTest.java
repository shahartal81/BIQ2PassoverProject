package main.java.player;

import main.java.enums.Move;
import org.junit.Assert;
import org.junit.Test;

public class PlayerAdvancedTest {

    @Test
    public void testPlayerAdvancedMoveMethod(){
        PlayerAdvanced player = new PlayerAdvanced();
        Move move = player.move();
        Assert.assertTrue(move.equals(Move.DOWN) || move.equals(Move.UP) || move.equals(Move.LEFT) || move.equals(Move.RIGHT));
    }

    @Test
    public void testPlayerAdvancedHitWallMethod(){
        PlayerAdvanced player = new PlayerAdvanced();
        Move move = player.move();
        Assert.assertEquals("move is not saved as last move", move, player.getLastMove());
        Assert.assertEquals("bookmark sequence should be 0", 0, player.getSeqNumber());
        player.hitWall();
        Assert.assertTrue("use bookmark flag should be true", player.isUseBookmark());
        player.move();
        Assert.assertEquals("bookmark sequence should be 1", 1, player.getSeqNumber());
        Assert.assertTrue("bookmark 1 should be in map", player.getBookmarksMap().containsKey(1));
        Assert.assertEquals("bookmark 1 should contain last move as value", move, player.getBookmarksMap().get(1).get(0));
        Assert.assertEquals("last move should be bookmark", Move.BOOKMARK, player.getLastMove());
    }

    @Test
    public void testPlayerAdvancedHitBookmarkMethod(){
        PlayerAdvanced player = new PlayerAdvanced();
        player.move();
        player.hitWall();
        player.move();
        player.hitBookmark(1);
        Assert.assertTrue("hit bookmark should be true", player.getHitBookmark());
        Move move = player.move();
        player.hitBookmark(2);
        Assert.assertTrue("hit bookmark should be true", player.getHitBookmark());
        Assert.assertTrue("bookmark 2 should be in map", player.getBookmarksMap().containsKey(2));
        Assert.assertEquals("bookmark 2 should contain last move as value", move, player.getBookmarksMap().get(2).get(0));
    }
}
