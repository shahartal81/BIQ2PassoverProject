package main.java.player;

import main.java.enums.Move;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class PlayerAdvanced implements Player {

    private Map<Integer, ArrayList<Move>> bookmarksMap = new HashMap<>();
    private int seqNumber = 0;
    private Move lastMove;
    private boolean usedBookmark = false;
    private int hitBookmarkSeqNumber = 0;

    @Override
    public Move move() {
        if (usedBookmark) {
            usedBookmark = false;
            return Move.BOOKMARK;
        } else {
            lastMove = chooseMove();
            return lastMove;
        }
    }

    @Override
    public void hitWall() {
        seqNumber++;
        addBookmark(seqNumber);
        usedBookmark = true;
        System.out.println("Hit Wall");
    }

    @Override
    public void hitBookmark(int seq) {
        hitBookmarkSeqNumber = seq;
        lastMove = Move.BOOKMARK;
        if (!bookmarksMap.containsKey(seq)) {
            addBookmark(seq);
        }
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
        if (lastMove == null) {
            return new Move[]{Move.LEFT, Move.RIGHT, Move.UP, Move.DOWN}[new Random().nextInt(Move.values().length-1)];
        } else if (lastMove.equals(Move.BOOKMARK)) {
            ArrayList<Move> moves = new ArrayList<>();
            for (Move move: Move.values()) {
                if (!bookmarksMap.get(hitBookmarkSeqNumber).contains(move)) {
                    moves.add(move);
                }
            }
            Move[] movesArray = moves.toArray(new Move[0]);
            return movesArray[new Random().nextInt(movesArray.length-1)];
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
                default:
                    throw new IllegalArgumentException("");
            }
        }
    }
}
