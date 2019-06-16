package filehandling;

import additionalclasses.Maze;
import additionalclasses.MazeElement;
import additionalclasses.Position;

import java.util.ArrayList;
import java.util.List;

public class MazeParser {
    private static final char PLAYER = MazeElement.PLAYER.getValue();
    private static final char END = MazeElement.END.getValue();
    private static final char WALL = MazeElement.WALL.getValue();
    private static final char PASS = MazeElement.PASS.getValue();
    private static final int MAZE_START_LINE = 4;
    private static Position playerPosition;
    private static Position endPosition;

    public static Position getPlayerPosition() {
        return playerPosition;
    }

    public static Position getEndPosition() {
        return endPosition;
    }

    private List<String> mazeDefinition = new ArrayList<>();

    List<String> getMazeDefinition() {
        return mazeDefinition;
    }

    void setMazeDefinition(List<String> mazeDefinition) {
        this.mazeDefinition = mazeDefinition;
    }

    public Maze getMaze(List<String> mazeDefinition){
        Maze maze = null;

        this.mazeDefinition = mazeDefinition;
        if (isMazeDefinitionInsufficient()){
            ErrorsSingleton.instance().addToErrorList("Data in maze input file is insufficient. Maze cannot be created");
            return null;
        }
        String mazeName = mazeDefinition.get(0);
        int maxSteps = numberOf("MaxSteps", 2);
        int rows = numberOf("Rows", 3);
        int cols = numberOf("Cols", 4);

        boolean isMaxValid = isMaxStepsValid(maxSteps);
        boolean isMazeValid = isMazeValid(rows, cols);

        if (isMaxValid && isMazeValid) {
            maze = new Maze();
            maze.setMaxSteps(maxSteps);
            maze.setRows(rows);
            maze.setColumns(cols);
            maze.setMazeMap(fillMazeMap(rows, cols));
            maze.setMazeName(mazeName);
        }
        else {
            ErrorsSingleton.instance().addToErrorList(("Data in maze input file is invalid. Maze cannot be created"));
        }
        return maze;
    }

    int numberOf(String key, int lineNumber){
        int index = lineNumber - 1;
        if (index < mazeDefinition.size()) {
            String num = "";
            String line = mazeDefinition.get(index).trim();
            String[] strs = line.split("=");
            if (strs.length == 2){
               num  = strs[1].trim();
            }
            if (strs.length != 2 || !strs[0].trim().equals(key) || !num.matches("[0-9]+")) {
                ErrorsSingleton.instance.addToErrorList(("Bad maze file header: expected in line " + lineNumber + " - " + key + " = <num>" + "\n" + " got: " + line));
                return 0;
            }
            try {
                return Integer.parseInt(num);
            } catch (NumberFormatException e) {
                ErrorsSingleton.instance.addToErrorList(("Invalid number " + num + " in line " + lineNumber));
            }
        }
        return 0;
    }

    boolean isMazeValid(int rows, int cols){
        if (!isRowsColsValid(rows,cols)) {
            return false;
        }

        boolean isCharValid = true;
        int countPlayerChar = 0;
        int countEndChar = 0;

        int mazeBodyRows = Integer.min(MAZE_START_LINE + rows, mazeDefinition.size());
        for (int i = MAZE_START_LINE; i < mazeBodyRows; i++){
            String line = mazeDefinition.get(i);

            int mazeBodyCols = Integer.min(cols, line.length());
            for (int j = 0; j < mazeBodyCols; j++){
                char mazeChar = line.charAt(j);

                if (mazeChar == PLAYER){
                    playerPosition = new Position(i - MAZE_START_LINE, j);
                    countPlayerChar++;
                }
                else if (mazeChar == END){
                    endPosition = new Position(i - MAZE_START_LINE, j);
                    countEndChar++;
                }
                else if (mazeChar != WALL && mazeChar != PASS){
                    ErrorsSingleton.instance.addToErrorList(("Wrong character in maze: " +  mazeChar + " in row " + (i+1) + ", col " + (j+1) ));
                    isCharValid = false;
                }
            }
        }

        return isCharCountValid(countPlayerChar, PLAYER) && isCharCountValid(countEndChar, END) && isCharValid;
    }

    private boolean isCharCountValid(int count, char mazeChar){
        boolean isValid = false;
        if (count == 0){
            ErrorsSingleton.instance.addToErrorList(("Missing " +  mazeChar + " in maze"));
        }
        else if (count > 1){
            ErrorsSingleton.instance.addToErrorList(("More than one " +  mazeChar + " in maze"));
        } else {
            isValid = true;
        }
        return isValid;
    }

    private char[][] fillMazeMap(int rows, int cols){
        char[][]mazeMap = new char[rows][cols];
        List<String> mazeArray = mazeDefinition.subList(4, mazeDefinition.size());

        //add spaces for missing columns
        for (int i = 0; i < mazeArray.size(); i++){
            String str = mazeArray.get(i);
            if (str.length() < cols){
                int diff = cols - str.length();
                mazeArray.set(i, addEmptyColumns(str, diff));
            }
        }

        //add spaces for missing rows
        if (rows > mazeArray.size()){
            int rowsToAdd = rows - mazeArray.size();
            for (int i = 0; i < rowsToAdd; i++ ){
                mazeArray.add(addEmptyRow(cols));
            }
        }

        //fill maze map
        for (int row = 0; row < mazeMap.length; row++){
            for (int col = 0; col < mazeMap[row].length; col++){
                mazeMap[row][col] = mazeArray.get(row).charAt(col);
            }
        }
        return mazeMap;
    }

    private String addEmptyColumns(String str, int diff) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        for (int i = 0; i < diff; i++){
            sb.append(PASS);
        }
        return sb.toString();
    }

    private String addEmptyRow(int cols) {
        StringBuilder row = new StringBuilder();
        for (int i = 0; i < cols; i++){
            row.append(PASS);
        }
        return row.toString();
    }

    boolean isMaxStepsValid(int steps){
        if (steps == 0) {
            ErrorsSingleton.instance.addToErrorList(("Bad maze file header: expected in line 2 - MaxSteps bigger than 0 "
                    + "\n" + "got: " + mazeDefinition.get(1)));
            return false;
        }
        return true;
    }

    boolean isRowsColsValid(int row, int col){
        if (row > 0 && col > 0 && (row > 1 || col > 1)) {
            return true;
        }
        ErrorsSingleton.instance.addToErrorList(("Bad maze file header: expected in lines 3,4 - minimum 1 row and 2 columns or 2 rows and 1 column in a maze "
                + "\n" + "got: " + mazeDefinition.get(2) + " " + mazeDefinition.get(3)));
        return false;
    }

    private boolean isMazeDefinitionInsufficient(){
        return mazeDefinition == null || mazeDefinition.isEmpty() || mazeDefinition.size() < 5;
    }
}
