package player;

import enums.Move;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Bookmark {

    private int sequenceNumber;
    private List<Move> movesPerformed = new ArrayList<>();

    public Bookmark(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public List<Move> getMovesPerformed() {
        return movesPerformed;
    }

    public void setMovesPerformed(List<Move> movesPerformed) {
        this.movesPerformed = movesPerformed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bookmark bookmark = (Bookmark) o;
        return sequenceNumber == bookmark.sequenceNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sequenceNumber, movesPerformed);
    }
}
