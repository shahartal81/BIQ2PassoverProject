package enums;

import additionalclasses.Position;

public enum Move {
    UP ('U', +1, 0),
    DOWN ('D', -1, 0),
    LEFT ('L', 0, -1),
    RIGHT ('R', 0, +1),
    BOOKMARK ('B', 0, 0);

    private char value;

    public int getRow() {
        return row;
    }

    public int getColomn() {
        return colomn;
    }

    private int row;
    private int colomn;

    Move(char value, int row, int colomn) {
        this.value = value;
        this.row = row;
        this.colomn = colomn;
    }

    public char getValue(){
        return this.value;
    }

    public Move getOpposite(){
        switch (this) {
            case UP:
                return DOWN;
            case DOWN:
                return UP;
            case LEFT:
                return RIGHT;
            case RIGHT:
                return LEFT;
        }
        throw new IllegalArgumentException("No opposite move for this move : " + getValue());
    }


    Move (char value){
        this.value = value;
    }
}
