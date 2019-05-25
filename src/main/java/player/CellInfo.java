package player;

import enums.Move;

import java.util.EnumMap;

class CellInfo {

    private EnumMap<Move, Character> directions = new EnumMap<>(Move.class);
    private int bookmarkSequence;
    private Value value = Value.SPACE;

    enum Value{
        SPACE,
        WALL,
        UNKNOWN
    }
    public void setValue(Value value) {
        this.value = value;
    }

    public void setDirectionValue(Move move, Character value) {
        directions.put(move,value);
    }

    public Character getDirectionValue(Move move){
        return directions.get(move);
    }

    public void setBookmarkSequence(int bookmarkSequence) {
        this.bookmarkSequence = bookmarkSequence;
    }

    public int getBookmarkSequence() {
        return bookmarkSequence;
    }

    public Value getValue() {
        return value;
    }
}
