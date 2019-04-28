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

    public Position getPlayerPosition() {
        for (int row = 0; row < mazeMap.length; row++) {
            for (int col = 0; col < mazeMap[row].length; col++) {
                if (mazeMap[row][col] == '@') {
                    return new Position(row, col);
                }
            }
        }
        return null;
    }

    public Position getEndPosition() {
        for (int row = 0; row < mazeMap.length; row++) {
            for (int col = 0; col < mazeMap[row].length; col++) {
                if (mazeMap[row][col] == '$') {
                    return new Position(row, col);
                }
            }
        }
        return null;
    }

    public void printMaze(){
        for (int row = 0; row < mazeMap.length; row++){
            for (int col = 0; col < mazeMap[row].length; col++) {
                System.out.print(mazeMap[row][col] + " ");
            }

            System.out.println();
        }
        System.out.println("----------------------------------");
    }

}