package player;

import additionalclasses.Position;
import enums.Move;

import java.util.HashMap;
import java.util.Map;

import static player.PlayerRuleSet.CellInfo.Value.UNKNOWN;
import static player.PlayerRuleSet.CellInfo.Value.WALL;

public class PlayerRuleSet implements Player {

    private Position previousPosition = new Position(0,0);;
    private Position currentPosition = new Position(0,0);
    private Map<Integer, Position> bookmarkToPositionMap = new HashMap<>();
    private Map<Position, CellInfo> positionCellInfoMap = new HashMap<Position, CellInfo>() {{put(currentPosition, new CellInfo());}};
    private int bookmarkSequence = 0;
    private RuleSet[] ruleSets = {
            new RuleSet(Move.RIGHT, Move.DOWN),
            new RuleSet(Move.LEFT, Move.UP),
            new RuleSet(Move.DOWN, Move.LEFT),
            new RuleSet(Move.UP, Move.RIGHT)};
    private int ruleSetIndex = 0;
    private boolean shouldBookmark = true;
    private State state = State.MOVED;

    enum State {
        MOVED,
        HIT_BOOKMARK,
        HIT_WALL_AFTER_BOOKMARK,
        CHANGED_DIRECTION_AFTER_BOOKMARK
    }

    @Override
    public Move move() {
        if(shouldBookmark){
            shouldBookmark = false;
            CellInfo cellInfo = positionCellInfoMap.get(currentPosition);
            if (cellInfo.getBookmarkSequence() == 0) {
                bookmarkSequence++;
                bookmarkToPositionMap.put(bookmarkSequence, currentPosition);
                cellInfo.setBookmarkSequence(bookmarkSequence);
                return Move.BOOKMARK;
            }
        }
        Move nextMove = getNextMove();
        int counter = 0;
        //will end at max_steps if we can't move or at max rule set length
        while(nextMove == null && counter < ruleSets.length){
            counter++;
            changeRuleSet();
            setState(State.MOVED);
            nextMove = getNextMove();
        }
        if(counter == ruleSets.length){
            return Move.UP;
        }
        return nextMove;
    }

    private void changeRuleSet() {
        ruleSetIndex++;
        if(ruleSetIndex == ruleSets.length){
            ruleSetIndex = 0;
        }
    }

    private Move getNextMove() {
        if(state == State.MOVED || state == State.CHANGED_DIRECTION_AFTER_BOOKMARK) {
            Move direction = ruleSets[ruleSetIndex].getFirstDirection();
            if (canMove(direction)) {
                changeCurrentPosition(direction);
                setState(State.MOVED);
                return direction;
            }
            direction = ruleSets[ruleSetIndex].getSecondDirection();
            if (canMove(direction)) {
                changeCurrentPosition(direction);
                setState(State.MOVED);
                shouldBookmark = true;
                return direction;
            }
            return null;
        }
        if(state == State.HIT_BOOKMARK) {
            Move direction = ruleSets[ruleSetIndex].getSecondDirection();
            if (canMove(direction)) {
                changeCurrentPosition(direction);
                setState(State.CHANGED_DIRECTION_AFTER_BOOKMARK);
                shouldBookmark = true;
                return direction;
            }
        }
        return null;
    }

    private boolean canMove(Move direction){
        CellInfo.Value nextValue = getNextValue(direction);
        if (nextValue == WALL) {
            return false;
        }
        if(nextValue == UNKNOWN){
            CellInfo cellInfo = new CellInfo();
            positionCellInfoMap.put(getPosition(direction), cellInfo);
        }
        return true;
    }

    @Override
    public void hitWall() {
        shouldBookmark = false;
        positionCellInfoMap.get(currentPosition).setValue(WALL);
        currentPosition = previousPosition;
        if(state == State.CHANGED_DIRECTION_AFTER_BOOKMARK){
            setState(State.HIT_WALL_AFTER_BOOKMARK);
        }
    }

    @Override
    public void hitBookmark(int seq) {
        currentPosition = bookmarkToPositionMap.get(seq);
        setState(State.HIT_BOOKMARK);
    }

    private void changeCurrentPosition(Move direction) {
        previousPosition = currentPosition;
        currentPosition = getPosition(direction);
    }

    private Position getRightPosition() {
        return new Position(currentPosition.getRow(), currentPosition.getColumn() + 1);
    }
    private Position getLeftPosition() {
        return new Position(currentPosition.getRow(), currentPosition.getColumn() - 1);
    }
    private Position getUpPosition() {
        return new Position(currentPosition.getRow() - 1, currentPosition.getColumn());
    }
    private Position getDownPosition() {
        return new Position(currentPosition.getRow() + 1, currentPosition.getColumn());
    }

    private CellInfo.Value getNextValue(Move move){
        CellInfo cellInfoOfMove = positionCellInfoMap.get(getPosition(move));
        if (cellInfoOfMove == null) {
            return UNKNOWN;
        }
        return positionCellInfoMap.get(getPosition(move)).getValue();
    }

    private Position getPosition(Move move) {
        switch (move) {
            case UP:
                return getUpPosition();
            case DOWN:
                return getDownPosition();
            case LEFT:
                return  getLeftPosition();
            case RIGHT:
                return getRightPosition();
            case BOOKMARK:
                default:
                throw new IllegalArgumentException("Unknown Move");
        }
    }

    void setState(State newState){
        state = newState;
    }

    private class RuleSet {

        private final Move firstDirection;
        private final Move secondDirection;

        public RuleSet(Move firstDirection, Move secondDirection) {
            this.firstDirection = firstDirection;
            this.secondDirection = secondDirection;
        }

        public Move getFirstDirection() {
            return firstDirection;
        }

        public Move getSecondDirection() {
            return secondDirection;
        }
    }

    protected static class CellInfo {

        private int bookmarkSequence;
        private CellInfo.Value value = CellInfo.Value.SPACE;

        enum Value{
            SPACE,
            WALL,
            UNKNOWN
        }
        public void setValue(CellInfo.Value value) {
            this.value = value;
        }

        public void setBookmarkSequence(int bookmarkSequence) {
            this.bookmarkSequence = bookmarkSequence;
        }

        public int getBookmarkSequence() {
            return bookmarkSequence;
        }

        public CellInfo.Value getValue() {
            return value;
        }
    }
}
