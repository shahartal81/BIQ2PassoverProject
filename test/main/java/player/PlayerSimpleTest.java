package player;

import main.java.enums.Move;
import org.junit.Assert;
import org.junit.Test;
import main.java.player.PlayerSimple;

public class PlayerSimpleTest {

    @Test
    public void testPlayerSimpleMoveMethod(){
        PlayerSimple player = new PlayerSimple();
        Move move = player.move();
        Assert.assertTrue(move.equals(Move.DOWN) || move.equals(Move.UP) || move.equals(Move.LEFT) || move.equals(Move.RIGHT));
    }
}
