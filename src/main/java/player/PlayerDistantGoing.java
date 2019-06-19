package player;

import additionalclasses.Position;
import enums.Move;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static enums.Move.BOOKMARK;
import static enums.Move.DOWN;
import static enums.Move.LEFT;
import static enums.Move.RIGHT;
import static enums.Move.UP;

public class PlayerDistantGoing implements Player {
    private static final List<Move> MOVES = Arrays.asList(UP, DOWN, RIGHT, LEFT);

    private Set<Position> visited = new HashSet<>();
    private Set<Position> walls = new HashSet<>();
    private List<Move> routeList = new ArrayList<>();

    private Set<Integer> columnBookmarked = new HashSet<>();
    private Set<Integer> rowBookmarked = new HashSet<>();

    private Map<Integer,Position> bookmarks = new HashMap<>();
    private int bookmarkSeqNumber = 0;

    private Position curPosition;
    private Move nextMove;
    private Move firstMove;
    private boolean isMovingBack = false;

    @Override
    public Move move() {
        advancePosition();

        nextMove = chooseMove();
        if (routeList.isEmpty() && visited.size() == 1) {
            firstMove = nextMove;
        }

        if (nextMove == null){
            if (!routeList.isEmpty()) {
                // pop will throw exception if route is empty, in case there are no more options to proceed
                Move lastMove = routeList.remove(routeList.size() - 1);
                nextMove = lastMove.getOpposite();

                isMovingBack = true;
            } else {
                nextMove = firstMove.getOpposite();
            }
        }

        return nextMove;
    }

    private void setCurrentPosition(Position nextPosition){
        curPosition = nextPosition;
        visited.add(curPosition);
    }
    private void advancePosition() {
        //initial step in the beginning
        if (curPosition == null) {
            setCurrentPosition(new Position(0,0));
            return;
        }

        if (nextMove == BOOKMARK){
            return;
        }
        Position nextPosition = byMove(curPosition, nextMove);
        if (!walls.contains(nextPosition)) {
            setCurrentPosition(nextPosition);
            if (!isMovingBack) {
                routeList.add(nextMove);
            }
        }

        isMovingBack = false;
    }

    private boolean setBookmark(){
        if (!columnBookmarked.contains(curPosition.getColumn()) || !rowBookmarked.contains(curPosition.getRow())) {
            bookmarkSeqNumber++;

            bookmarks.put(bookmarkSeqNumber, curPosition);

            columnBookmarked.add(curPosition.getColumn());
            rowBookmarked.add(curPosition.getRow());

            return true;
        }

        return false;
    }

    private Move chooseMove(){
       if (setBookmark()){
           return BOOKMARK;
       }

       for (Move move : MOVES){
           Position position = byMove(curPosition, move);
           if (isAvailable(position)){
               return move;
           }
       }
       return null;
    }

    private boolean isAvailable(Position position){
        return !visited.contains(position) && !walls.contains(position);
    }

    @Override
    public void hitWall() {
        Position nextPosition = byMove(curPosition, nextMove);
        walls.add(nextPosition);
    }

    @Override
    public void hitBookmark(int seq) {
        Position bookmarkedPosition = bookmarks.get(seq);

        // bookmark can be removed by player
        // see below "for loop" that removes bookmarks that are not definitely good
        if (bookmarkedPosition == null) {
            return;
        }
        Position nextPosition = byMove(curPosition, nextMove);

        if (bookmarkedPosition.equals(nextPosition)) {
            return;
        }

        // reset current position by bookmark
        curPosition = byMove(bookmarkedPosition, nextMove.getOpposite());

        for (int i = seq + 1; i <= bookmarkSeqNumber; ++i) {
            bookmarks.remove(i);
        }
    }

    private Position byMove(Position position, Move move) {
        return new Position(
                position.getRow() + (move.getValue()[0] * -1),
                position.getColumn() + move.getValue()[1]);
    }
}
