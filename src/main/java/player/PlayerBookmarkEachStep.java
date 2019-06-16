package player;

import enums.Move;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class PlayerBookmarkEachStep implements Player {

    private Map<Integer, Bookmark> bookmarks = new HashMap<>();
    private int seqNumber = 0;
    private Move lastMove;
    private boolean useBookmark = false;
    private boolean hitBookmark = false;

    public Map<Integer, Bookmark> getBookmarks() {
        return bookmarks;
    }

    public int getSeqNumber() {
        return seqNumber;
    }

    public Move getLastMove() {
        return lastMove;
    }

    public boolean isUseBookmark() {
        return useBookmark;
    }

    @Override
    public Move move() {
        if (useBookmark) {
            useBookmark = false;
            seqNumber++;
            handleBookmark(seqNumber);
            lastMove = Move.BOOKMARK;
        } else {
            lastMove = chooseMove();
            useBookmark = true;
        }
        return lastMove;
    }

    @Override
    public void hitWall() {
        useBookmark = true;
    }

    @Override
    public void hitBookmark(int seq) {
        hitBookmark = true;
        handleBookmark(seq);
    }

    private void handleBookmark(int sequence) {
        List<Move> moves;
        Bookmark bookmark = new Bookmark(seqNumber);
        if (bookmarks.isEmpty() || !bookmarks.containsKey(sequence)) {
            moves = new ArrayList<>();
            moves.add(lastMove);
        } else {
            moves = bookmarks.get(sequence).getMovesPerformed();
            if (!hitBookmark) {
                moves.add(lastMove);
            } else {
                switch (lastMove) {
                    case UP:
                        moves.add(Move.DOWN);
                        break;
                    case DOWN:
                        moves.add(Move.UP);
                        break;
                    case RIGHT:
                        moves.add(Move.LEFT);
                        break;
                    case LEFT:
                        moves.add(Move.RIGHT);
                        break;
                    default:
                        moves.add(lastMove);
                }
            }
        }
        bookmark.setMovesPerformed(moves);
        bookmarks.put(sequence, bookmark);
    }

    private Move chooseMove(){
        if (lastMove != null && lastMove.equals(Move.BOOKMARK)) {
                List<Move> moves = new ArrayList<>();
                for (Move move : Move.values()) {
                    if (!bookmarks.get(seqNumber).getMovesPerformed().contains(move)) {
                        moves.add(move);
                    }
                }
                Move[] movesArray = moves.toArray(new Move[0]);
                return movesArray[new Random().nextInt(movesArray.length - 1)];
        }
        return new Move[]{Move.LEFT, Move.RIGHT, Move.UP, Move.DOWN}[new Random().nextInt(Move.values().length-1)];
    }

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
}
