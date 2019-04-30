package main.java.enums;

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

    Move (char value){
        this.value = value;
    }
}
