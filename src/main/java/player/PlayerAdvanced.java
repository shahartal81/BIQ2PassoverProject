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
    private boolean useBookmark = false;
    private boolean hitBookmark = false;

    public Map<Integer, ArrayList<Move>> getBookmarksMap() {
        return bookmarksMap;
    }

    public int getSeqNumber() {
        return seqNumber;
    }

    public Move getLastMove() {
        return lastMove;
    }

    public boolean isUseBookmark() {
        return useBookmark;
    }

    public boolean getHitBookmark() {
        return hitBookmark;
    }

    @Override
    public Move move() {
        if (useBookmark) {
            useBookmark = false;
            seqNumber++;
            handleBookmark(seqNumber);
            lastMove = Move.BOOKMARK;
        } else {
            lastMove = chooseMove();
        }
        return lastMove;
    }

    @Override
    public void hitWall() {
        System.out.println("Hit Wall");
        useBookmark = true;
    }

    @Override
    public void hitBookmark(int seq) {
        hitBookmark = true;
        handleBookmark(seq);
        System.out.println("Hit Bookmark");
    }

    private void handleBookmark(int sequence) {
        ArrayList<Move> moves;
        if (bookmarksMap.isEmpty() || !bookmarksMap.containsKey(sequence)) {
            moves = new ArrayList<>();
            moves.add(lastMove);
        } else {
            moves = bookmarksMap.get(sequence);
            if (!hitBookmark) {
                moves.add(lastMove);
            } else {
                switch (lastMove) {
                    case UP:
                        moves.add(Move.DOWN);
                        break;
                    case DOWN:
                        moves.add(Move.UP);
                        break;
                    case RIGHT:
                        moves.add(Move.LEFT);
                        break;
                    case LEFT:
                        moves.add(Move.RIGHT);
                        break;
                    default:
                        moves.add(lastMove);
                }
            }
        }
        bookmarksMap.put(sequence, moves);
        System.out.println("Added a bookmark");
    }

    private Move chooseMove(){
        if (lastMove == null) {
            return new Move[]{Move.LEFT, Move.RIGHT, Move.UP, Move.DOWN}[new Random().nextInt(Move.values().length-1)];
        } else if (lastMove.equals(Move.BOOKMARK)) {
            ArrayList<Move> moves = new ArrayList<>();
            for (Move move: Move.values()) {
                if (!bookmarksMap.get(seqNumber).contains(move)) {
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
