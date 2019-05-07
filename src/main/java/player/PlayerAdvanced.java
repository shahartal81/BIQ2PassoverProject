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
    private boolean hitWall = false;
    private boolean considerBookmark = false;
    private int bookmarkNumberToConsider = 0;

    public Map<Integer, ArrayList<Move>> getBookmarksMap() {
        return bookmarksMap;
    }

    public int getSeqNumber() {
        return seqNumber;
    }

    public Move getLastMove() {
        return lastMove;
    }

    public boolean isHitWall() {
        return hitWall;
    }

    @Override
    public Move move() {
        if (hitWall && !considerBookmark) {
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
        System.out.println("Player hit Wall");
        hitWall = true;
    }

    @Override
    public void hitBookmark(int seq) {
        System.out.println("Player hit Bookmark");
        handleBookmark(seq);
    }

    private void handleBookmark(int sequence) {
        ArrayList<Move> moves;
        if (bookmarksMap.isEmpty() || !bookmarksMap.containsKey(sequence)) {
            moves = new ArrayList<>();
            System.out.println("Player created new bookmark in sequence " + sequence);
        } else {
            moves = bookmarksMap.get(sequence);
            considerBookmark = true;
            bookmarkNumberToConsider = sequence;
        }
        if (hitWall && !moves.contains(lastMove)) {
            moves.add(lastMove);
            System.out.println("Player added " + lastMove + " at bookmark sequence " + sequence);
        }
        hitWall = false;
        System.out.println("Player forbidden moves in sequence " + sequence + " are " + moves);
        bookmarksMap.put(sequence, moves);
    }

    private Move chooseMove(){
        if (hitWall || considerBookmark || (lastMove != null && lastMove.equals(Move.BOOKMARK))) {
            ArrayList<Move> moves = new ArrayList<>();
            int sequenceNumber;
            if (considerBookmark) {
                sequenceNumber = bookmarkNumberToConsider;
                considerBookmark = false;
            } else {
                sequenceNumber = seqNumber;
            }
            for (Move move : Move.values()) {
                if (!bookmarksMap.get(sequenceNumber).contains(move) && !move.equals(Move.BOOKMARK)) {
                    moves.add(move);
                }
            }
            Move[] movesArray = moves.toArray(new Move[0]);
            System.out.println("Player has following options to choose from: " + moves);
            return movesArray[new Random().nextInt(movesArray.length - 1)];
        }
        return new Move[]{Move.LEFT, Move.RIGHT, Move.UP, Move.DOWN}[new Random().nextInt(Move.values().length-1)];
    }
}
