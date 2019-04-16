package player;

import enums.Move;
import player.Player;

import java.util.Random;

public class PlayerSimple extends Player {
    public Move move() {
        return randomMove();
    }

    public void hitWall() {
        System.out.println("Hit Wall");
    }

    private Move randomMove(){
        int pick = new Random().nextInt(Move.values().length);
        return Move.values()[pick];
    }
}
