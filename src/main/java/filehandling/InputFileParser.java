package main.java.filehandling;


import main.java.additionalclasses.Maze;
import main.java.additionalclasses.MazeElement;
import java.util.ArrayList;
import java.util.List;

public class InputFileParser implements FileParser {
    private List<String> result = new ArrayList<>();
    private List<String> errorList;
    private static final char PLAYER = MazeElement.PLAYER.getValue();
    private static final char END = MazeElement.END.getValue();
    private static final char WALL = MazeElement.WALL.getValue();
    private static final char PASS = MazeElement.PASS.getValue();

    public InputFileParser(List<String> errorList){
        this.errorList = errorList;
    }

    public InputFileParser(){
    }

    List<String> getResult() {
        return result;
    }



    public List<String> getErrorsList(){
        return errorList;
    }

    @Override
    public Maze getMaze(List<String> result){
        Maze maze = null;
         if (result == null || result.size() < 5 ){
            errorList.add("Data in maze input file is insufficient. Maze cannot be created");
            return maze;
        }

         this.result = result;

        int maxSteps = numberOf("MaxSteps", 2);
        int rows = numberOf("Rows", 3);
        int cols = numberOf("Cols", 4);

        boolean isMaxValid = isMaxStepsValid(maxSteps);
        boolean isRowsColsValid = isRowsColsValid(rows, cols);
        boolean isMazeValid = isMazeValid();

        if (isMaxValid && isRowsColsValid && isMazeValid) {
            maze = new Maze();
            maze.setMaxSteps(maxSteps);
            maze.setRows(rows);
            maze.setColumns(cols);
            maze.setMazeMap(fillMazeMap(rows, cols));
        }
       else {
            errorList.add(("Data in maze input file is invalid. Maze cannot be created"));
        }

        return maze;
    }

    @Override
    public void setErrorList(List<String> errorsList) {
        this.errorList = errorsList;
    }

    private int numberOf(String key, int lineNumber){
        int index = lineNumber - 1;
        if (index < result.size()) {
            String line = result.get(index).trim();
            String[] strs = line.split("=");
            String num = strs[1].trim();
            if (strs.length != 2 || !strs[0].trim().equals(key) || !num.matches("[0-9]+")) {
                errorList.add(("Bad maze file header: expected in line " + lineNumber + " - " + key + " = <num>" + "\n" + " got: " + line));
                return -1;
            }
            try {
                return Integer.parseInt(num);
            } catch (NumberFormatException e) {
                errorList.add(("Invalid number " + num + " in line " + lineNumber));
            }
        }
        return -1;
    }

    private boolean isMazeValid(){
        boolean isMazeValid = false;
        boolean isCharValid = true;
        int countPlayerChar = 0;
        int countEndChar = 0;

        for (int i = 4; i < result.size(); i++){
            String line = result.get(i);

            for (int j = 0; j < line.length(); j++){
                char mazeChar = line.charAt(j);

                if (mazeChar == PLAYER){
                    countPlayerChar++;
                }
                else if (mazeChar == END){
                    countEndChar++;
                }
                else if (mazeChar != WALL && mazeChar != PASS){
                    errorList.add(("Wrong character in maze: " +  mazeChar + " in row " + (i+1) + ", col " + (j+1) ));
                    isCharValid = false;
                }
            }
        }

        if (isCharCountValid(countPlayerChar, PLAYER) && isCharCountValid(countEndChar, END) && isCharValid){
            isMazeValid = true;
        }
        return isMazeValid;
    }

    private boolean isCharCountValid(int count, char mazeChar){
        boolean isValid = false;
        if (count == 0){
            errorList.add(("Missing " +  mazeChar + " in maze"));
        }
        else if (count > 1){
            errorList.add(("More than one " +  mazeChar + " in maze"));

        } else {
            isValid = true;
        }
        return isValid;
    }

    private char[][] fillMazeMap(int rows, int cols){
        char[][]mazeMap = new char[rows][cols];
        ArrayList<String> mazeArray = new ArrayList<>();
        for (int i = 4; i < result.size(); i++){
            mazeArray.add(result.get(i));
        }

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

    private static String addEmptyColumns(String str, int diff) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        for (int i = 0; i < diff; i++){
            sb.append(PASS);
        }
        return sb.toString();
    }

    private static String addEmptyRow(int cols) {
        StringBuilder row = new StringBuilder();
        for (int i = 0; i < cols; i++){
            row.append(PASS);
        }
        return row.toString();
    }

    private boolean isMaxStepsValid(int steps){
        if (steps == 0) {
            errorList.add(("Bad maze file header: expected in line 2 - MaxSteps bigger than 0 "
                    + "\n" + "got: " + result.get(1)));
            return false;
        }
        return true;
    }

    private boolean isRowsColsValid(int row, int col){
        if ((row < 1 && col < 2) || (row < 2 && col < 1)) {
            errorList.add(("Bad maze file header: expected in lines 3,4 - minimum 1 row and 2 columns or 2 rows and 1 column in a maze "
                    + "\n" + "got: " + result.get(2) + " " + result.get(3)));
            return false;
        }
        return true;
    }
}
