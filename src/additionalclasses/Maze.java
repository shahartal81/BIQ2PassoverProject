package additionalclasses;
public class Maze {
    private int maxSteps;
    private int rows;
    private int columns;

    private char[][] mazeMap = new char[rows][columns];

    public void setMaxSteps(int maxSteps) {
        this.maxSteps = maxSteps;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public void setMazeMap(char[][] mazeMap) {
        this.mazeMap = mazeMap;
    }

    public char[][] getMazeMap() {
        return mazeMap;
    }

    public int getMaxSteps() {

        return maxSteps;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

}