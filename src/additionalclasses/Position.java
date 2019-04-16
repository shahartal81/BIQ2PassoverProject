package additionalclasses;

import enums.Move;

import java.util.Objects;

public class Position {

    private int row;
    private int column;

    public Position (int row, int column){
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public Position byMove(Move move) {
        switch (move){
            case UP:
                return new Position(row - 1, column);
            case DOWN:
                return new Position(row + 1, column);
            case LEFT:
                return new Position(row, column - 1);
            case RIGHT:
                return new Position(row, column + 1);
        }

        throw new IllegalArgumentException("");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return row == position.row &&
                column == position.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }
}
