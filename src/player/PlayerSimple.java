package player;

import enums.Move;

import java.util.Random;

public class PlayerSimple implements Player {

    @Override
    public Move move() {
        return randomMove();
    }

    @Override
    public void hitWall() {
        System.out.println("Hit Wall");
    }

    @Override
    public void hitBookmark(int seq) {
        System.out.println("Hit Bookmark");
    }

    private Move randomMove(){
        int pick = new Random().nextInt(Move.values().length-1);
        return Move.values()[pick];
    }
}
