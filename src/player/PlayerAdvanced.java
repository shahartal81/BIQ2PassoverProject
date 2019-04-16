package player;

import enums.Move;
import java.util.Random;

public class PlayerAdvanced implements Player {

    private Move lastMove;

    public Move move() {
        lastMove = randomMove();
        return lastMove;
    }

    public void hitWall() {
        System.out.println("Hit Wall");
    }

    @Override
    public void hitBookmark(int seq) {
        System.out.println("Hit Bookmark");
    }

    private Move randomMove(){
        if (lastMove == null) {
            return new Move[]{Move.LEFT, Move.RIGHT, Move.UP, Move.DOWN}[new Random().nextInt(Move.values().length-1)];
        } else {
            switch (lastMove) {
                case UP:
                    return new Move[]{Move.LEFT, Move.RIGHT, Move.UP}[new Random().nextInt(Move.values().length - 2)];
                case DOWN:
                    return new Move[]{Move.LEFT, Move.RIGHT, Move.DOWN}[new Random().nextInt(Move.values().length - 2)];
                case LEFT:
                    return new Move[]{Move.LEFT, Move.DOWN, Move.UP}[new Random().nextInt(Move.values().length - 2)];
                case RIGHT:
                    return new Move[]{Move.RIGHT, Move.DOWN, Move.UP}[new Random().nextInt(Move.values().length - 2)];
            }

            throw new IllegalArgumentException("");
        }


    }
}
