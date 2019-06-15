package player;

import additionalclasses.Position;
import enums.Move;

import java.util.*;

import static enums.Move.*;

public class PlayerDistantGoing implements Player {
    private static final List<Move> MOVES = Arrays.asList(UP, DOWN, LEFT, RIGHT);

    private Set<Position> visited = new HashSet<>();
    private Set<Position> walls = new HashSet<>();
    private Stack<Move> route = new Stack<>();

    private Position curPosition;
    private Move nextMove;
    private boolean isMovingBack = false;


    @Override
    public Move move() {
        advancePosition();

        nextMove = chooseMove();
        if (nextMove == null){
            // pop will throw exception if route is empty, in case there are no more options to proceed
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

        Position nextPosition = byMove(nextMove);
        if (!walls.contains(nextPosition)) {
            setCurrentPosition(nextPosition);
            if (!isMovingBack) {
                route.add(nextMove);
            }
        }

        isMovingBack = false;
    }

    private Move chooseMove(){
       for (Move move : MOVES){
           Position position = byMove(move);
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
        Position nextPosition = byMove(nextMove);
        walls.add(nextPosition);

        System.out.println(nextPosition);
    }

    @Override
    public void hitBookmark(int seq) {

    }

    private Position byMove(Move move) {
        switch (move){
            case UP:
                return new Position(curPosition.getRow() + 1, curPosition.getColumn());
            case DOWN:
                return new Position(curPosition.getRow() - 1, curPosition.getColumn());
            case LEFT:
                return new Position(curPosition.getRow(), curPosition.getColumn() - 1);
            case RIGHT:
                return new Position(curPosition.getRow(), curPosition.getColumn() + 1);
        }
        throw new IllegalArgumentException("");
    }
}
