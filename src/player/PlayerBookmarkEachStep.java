package player;

import enums.Move;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class PlayerBookmarkEachStep implements Player {

    private Map<Integer, ArrayList<Move>> bookmarksMap = new HashMap<>();
    private Integer seqNumber = 0;
    private Move lastMove;
    private boolean nextTurnBookmark = false;
    private int hitBookmarkSeqNumber = 0;

    @Override
    public Move move() {
        if (nextTurnBookmark) {
            nextTurnBookmark = false;
            return Move.BOOKMARK;
        } else {
            lastMove = chooseMove();
            nextTurnBookmark = true;
            return lastMove;
        }
    }

    @Override
    public void hitWall() {
        seqNumber++;
        addBookmark(seqNumber);
        nextTurnBookmark = false;
        System.out.println("Hit Wall");
    }

    @Override
    public void hitBookmark(int seq) {
        hitBookmarkSeqNumber = seq;
        lastMove = Move.BOOKMARK;
        System.out.println("Hit Bookmark");
    }

    private void addBookmark(Integer sequence) {
        ArrayList<Move> moves;
        if (bookmarksMap.isEmpty() || !bookmarksMap.containsKey(sequence)) {
            moves = new ArrayList<>();
        } else {
            moves = bookmarksMap.get(sequence);
        }
        moves.add(lastMove);
        bookmarksMap.put(sequence, moves);
        System.out.println("Added a bookmark");
    }

    private Move chooseMove(){
        if (lastMove.equals(Move.BOOKMARK)) {
            ArrayList<Move> moves = new ArrayList<>();
            for (Move move: Move.values()) {
                if (!bookmarksMap.get(hitBookmarkSeqNumber).contains(move)) {
                    moves.add(move);
                }
            }
            Move[] movesArray = moves.toArray(new Move[0]);
            return movesArray[new Random().nextInt(movesArray.length-1)];
        } else {
            return new Move[]{Move.LEFT, Move.RIGHT, Move.UP, Move.DOWN}[new Random().nextInt(Move.values().length-1)];
        }
    }
}
