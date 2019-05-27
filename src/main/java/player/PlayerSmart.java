package player;

import additionalclasses.Position;
import enums.Move;

import java.util.HashMap;
import java.util.Map;

import static player.CellInfo.Value.UNKNOWN;
import static player.CellInfo.Value.WALL;

public class PlayerSmart implements Player {

    private Position previousPosition;
    private Position currentPosition = new Position(0,0);
    private Map<Integer, Position> bookmarkToPositionMap = new HashMap<>();
    private Map<Position, CellInfo> positionCellInfoMap= new HashMap<>();
    private int bookmarkSequence = 0;
    private RuleSet[] ruleSets = {
            new RuleSet(Move.RIGHT, Move.DOWN),
            new RuleSet(Move.LEFT, Move.UP),
            new RuleSet(Move.DOWN, Move.LEFT),
            new RuleSet(Move.UP, Move.RIGHT)};
    private int ruleSetIndex = 0;
    private boolean shouldBookmark = true;
    private boolean shouldSkipFirstDirection;


    PlayerSmart() {
        positionCellInfoMap.put(currentPosition, new CellInfo());
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
        //will end at max_steps if i can't move
        while(nextMove == null){
            changeRuleSet();
            nextMove = getNextMove();
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
        if(!shouldSkipFirstDirection) {
            Move direction = ruleSets[ruleSetIndex].getFirstDirection();
            if (canMove(direction)) {
                previousPosition = currentPosition;
                currentPosition = getPosition(direction);
                return direction;
            }
        }
        Move direction = ruleSets[ruleSetIndex].getSecondDirection();
        if (canMove(direction)) {
            previousPosition = currentPosition;
            currentPosition = getPosition(direction);
            shouldBookmark = true;
            shouldSkipFirstDirection = false;
            return direction;
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
    }

    @Override
    public void hitBookmark(int seq) {
        System.out.println("hit bookmark: our seq: " + bookmarkSequence + " gm seq: " + seq);
        System.out.println(bookmarkToPositionMap);
        currentPosition = bookmarkToPositionMap.get(seq);
        if(seq == 1){
//            changeRuleSet();
        }
        shouldSkipFirstDirection = true;
    }

//    private Move getNextMove(){
//        CellInfo rightCellInfo = positionCellInfoMap.get(getRightPosition());
//        if(rightCellInfo == null){
//            CellInfo cellInfo = new CellInfo();
//            positionCellInfoMap.put(getRightPosition(), cellInfo);
//            lastMove = Move.RIGHT;
//            return lastMove;
//        }
//        if(rightCellInfo.getValue() == WALL){
//            CellInfo downCellInfo = positionCellInfoMap.get(getDownPosition());
//            if(downCellInfo == null){
//                CellInfo cellInfo = new CellInfo();
//                positionCellInfoMap.put(getDownPosition(), cellInfo);
//                lastMove = Move.DOWN;
//                return lastMove;
//            }
//        }
//    }




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
                throw new IllegalArgumentException("");
        }
    }

}
