package enums;

public enum Move {
    UP ('U'),
    DOWN ('D'),
    LEFT ('L'),
    RIGHT ('R'),
    BOOKMARK ('B');

    private char value;

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
