package additionalclasses;

public enum MazeElement {
    PLAYER('@'),
    END('$'),
    WALL('#'),
    PASS(' ');

    private char value;

    public char getValue(){
        return this.value;
    }

    MazeElement (char value){
        this.value = value;
    }
}
