package additionalclasses;

import enums.Move;

import java.util.ArrayList;
import java.util.List;

public class Bookmark {

    private int sequenceNumber;
    private List<Move> movesPerformed = new ArrayList<>();

    public Bookmark(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public List<Move> getMovesPerformed() {
        return movesPerformed;
    }

    public void setMovesPerformed(List<Move> movesPerformed) {
        this.movesPerformed = movesPerformed;
    }
}
