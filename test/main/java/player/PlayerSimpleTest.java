package player;

import enums.Move;
import org.junit.Assert;
import org.junit.Test;

public class PlayerSimpleTest {

    @Test
    public void testPlayerSimpleMoveMethod(){
        PlayerSimple player = new PlayerSimple();
        Move move = player.move();
        Assert.assertTrue(move.equals(Move.DOWN) || move.equals(Move.UP) || move.equals(Move.LEFT) || move.equals(Move.RIGHT));
    }
}
