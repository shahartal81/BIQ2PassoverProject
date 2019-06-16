package enums;

public enum Move {
    UP('U', new int[]{-1, 0}),
    DOWN('D', new int[]{1, 0}),
    LEFT('L', new int[]{0, -1}),
    RIGHT('R', new int[]{0, 1}),
    BOOKMARK('B', new int[]{0, 0});

    private char abr;
    private int[] value;

    public int[] getValue(){
        return this.value;
    }

    public char getAbr() {
        return abr;
    }

    Move(char abr, int[] value){
        this.abr = abr;
        this.value = value;
    }
}
