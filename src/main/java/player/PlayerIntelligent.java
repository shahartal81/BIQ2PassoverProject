package player;

import additionalclasses.Bookmark;
import additionalclasses.Position;
import enums.Move;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class PlayerIntelligent implements Player {

    private Map<Position, Bookmark> positionToBookmarksMap = new HashMap<>();
    private Map<Integer, Position> positionTrack = new HashMap<>();
    private int seqNumber = 0;
    private Move lastMove;
    private List<Move> eligibleMoves = new ArrayList<>();
    private int bookmarkEveryXSteps;
    private int totalStepsDone = 0;
    private Position currentPosition;
    private boolean hitWall;

    public PlayerIntelligent(int bookmarkEveryXSteps) {
        this.bookmarkEveryXSteps = bookmarkEveryXSteps;
        this.currentPosition = new Position(0,0);
    }

    public Position getCurrentPosition() {
        return currentPosition;
    }

    public Map<Position, Bookmark> getPositionToBookmarksMap() {
        return positionToBookmarksMap;
    }

    public int getSeqNumber() {
        return seqNumber;
    }

    public Move getLastMove() {
        return lastMove;
    }

    public List<Move> getEligibleMoves() {
        return eligibleMoves;
    }

    public int getBookmarkEveryXSteps() {
        return bookmarkEveryXSteps;
    }

    public int getTotalStepsDone() {
        return totalStepsDone;
    }

    @Override
    public Move move() {
        if (totalStepsDone == 1) {
            handleBookmark();
            lastMove = Move.BOOKMARK;
        }
        if (totalStepsDone % bookmarkEveryXSteps == 0) {
            handleBookmark();
            totalStepsDone++;
            return Move.BOOKMARK;
        } else {
            if (!hitWall) {
                resetEligibleMoves();
                updateCurrentPosition();
                updatePositionTrack();
            }
            lastMove = chooseMove();
            totalStepsDone++;
        }
        hitWall = false;
        return lastMove;
    }

    private void updateCurrentPosition() {
        switch (lastMove) {
            case UP:
                currentPosition = new Position(currentPosition.getRow()-1, currentPosition.getColumn());
                break;
            case DOWN:
                currentPosition = new Position(currentPosition.getRow()+1, currentPosition.getColumn());
                break;
            case RIGHT:
                currentPosition = new Position(currentPosition.getRow(), currentPosition.getColumn()-1);
                break;
            case LEFT:
                currentPosition = new Position(currentPosition.getRow(), currentPosition.getColumn()+1);
                break;
        }
    }

    private void resetEligibleMoves() {
        eligibleMoves.clear();
        Collections.addAll(eligibleMoves, Move.values());
    }

    private void updatePositionTrack() {
        positionTrack.put(totalStepsDone, currentPosition);
    }

    @Override
    public void hitWall() {
        System.out.println("Player hit Wall");
        totalStepsDone--;
        updateEligibleMovesForNextTurn(lastMove);
        hitWall = true;
    }

    private void updateEligibleMovesForNextTurn(Move move) {
        for(Iterator<Move> iterator = eligibleMoves.listIterator(); iterator.hasNext();) {
            Move currentMove = iterator.next();
            if (currentMove == move) {
                iterator.remove();
                return;
            }
        }
    }

    @Override
    public void hitBookmark(int seq) {
        System.out.println("Player hit Bookmark " + seq);
        updateEligibleMovesForNextTurnBasedOnBookmark(seq);
    }

    private void updateEligibleMovesForNextTurnBasedOnBookmark(int seq) {
        Bookmark foundBookmark = getRelevantBookmark(seq);
        for(Move move : foundBookmark.getMovesPerformed()) {
            updateEligibleMovesForNextTurn(move);
        }
    }

    private Bookmark getRelevantBookmark(int seq) {
        for(Bookmark bookmark : positionToBookmarksMap.values()) {
            if (bookmark.getSequenceNumber() == seq) {
                return bookmark;
            }
        }
        return null;
    }

    private void handleBookmark() {
        List<Move> moves = new ArrayList<>();
        if (totalStepsDone != 1) {
            seqNumber++;
        }
        moves.add(lastMove);
        Bookmark newBookmark = new Bookmark(seqNumber);
        newBookmark.setMovesPerformed(moves);
        positionToBookmarksMap.put(currentPosition, newBookmark);
        System.out.println("Player added a bookmark with sequence number " + seqNumber);
    }

    private Move chooseMove(){
        //return eligibleMoves.get(new Random().nextInt(eligibleMoves.size() - 1));
        if (eligibleMoves.contains(Move.RIGHT) && !lastMove.equals(Move.LEFT)) {
            return Move.RIGHT;
        } else if (eligibleMoves.contains(Move.DOWN) && !lastMove.equals(Move.UP)) {
            return Move.DOWN;
        } else if (eligibleMoves.contains(Move.LEFT) && !lastMove.equals(Move.RIGHT)) {
            return Move.LEFT;
        } else if (eligibleMoves.contains(Move.UP) && !lastMove.equals(Move.DOWN)){
            return Move.UP;
        } else {
            return eligibleMoves.get(new Random().nextInt(eligibleMoves.size() - 1));
        }
    }
}
