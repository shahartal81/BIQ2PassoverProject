package player;

import additionalclasses.Position;
import enums.Move;

import java.util.*;

import static enums.Move.*;

public class PlayerDistantGoing implements Player {
    private static final List<Move> MOVES = Arrays.asList(UP, DOWN, RIGHT, LEFT);

    private Set<Position> visited = new HashSet<>();
    private Set<Position> walls = new HashSet<>();
    private Stack<Move> route = new Stack<>();
    private Set<Integer> columnBookmarked = new HashSet<>();
    private Set<Integer> rowBookmarked = new HashSet<>();
    private Map<Integer,Position> bookmarks = new HashMap<>();
    private int bookmarkSeqNumber = 0;

    private Position curPosition;
    private Move nextMove;
    private boolean isMovingBack = false;


    @Override
    public Move move() {
        advancePosition();

        nextMove = chooseMove();
        if (nextMove == null){
            // pop will throw exception if route is empty, in case there are no more options to proceed
            System.out.println("Route size: " + route.size());
            nextMove = route.pop().getOpposite();
            isMovingBack = true;
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
                System.out.println("Move: " + nextMove.getValue());
                route.add(nextMove);
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

        System.out.println(nextPosition);
    }

    @Override
    public void hitBookmark(int seq) {
        Position nextPosition = byMove(curPosition, nextMove);
        Position bookmarkedPosition = bookmarks.get(seq);

        if (bookmarkedPosition.equals(nextPosition)) {
            return;
        }

        //reset current position by bookmark
        curPosition = byMove(bookmarkedPosition, nextMove.getOpposite());

        //loop over route, calculate position by each move and add to visited
        Position position = curPosition;
        while (!route.isEmpty()){
            Move move = route.pop();
            position = byMove(position, move.getOpposite());
            visited.add(position);
        }
    }

    private Position byMove(Position position, Move move) {
        switch (move){
            case UP:
                return new Position(position.getRow() + 1, position.getColumn());
            case DOWN:
                return new Position(position.getRow() - 1, position.getColumn());
            case LEFT:
                return new Position(position.getRow(), position.getColumn() - 1);
            case RIGHT:
                return new Position(position.getRow(), position.getColumn() + 1);
        }
        throw new IllegalArgumentException("");
    }
}
