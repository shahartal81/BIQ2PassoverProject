package player;

class CellInfo {

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
