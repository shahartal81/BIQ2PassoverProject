package main.java.player;

import main.java.enums.Move;
import org.junit.Assert;
import org.junit.Test;

public class PlayerAdvancedTest {

    @Test
    public void testPlayerSimple(){
        Player player = new PlayerAdvanced();
        Move move = player.move();
        Assert.assertTrue(move.equals(Move.DOWN) || move.equals(Move.UP) || move.equals(Move.LEFT) || move.equals(Move.RIGHT));
    }
}
