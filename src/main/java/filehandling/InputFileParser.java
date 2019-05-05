package main.java.filehandling;


import main.java.additionalclasses.Maze;
import main.java.additionalclasses.MazeElement;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class InputFileParser {
    private ArrayList<String> result = new ArrayList<>();
    private static final char PLAYER = MazeElement.PLAYER.getValue();
    private static final char END = MazeElement.END.getValue();
    private static final char WALL = MazeElement.WALL.getValue();
    private static final char PASS = MazeElement.PASS.getValue();

    public ArrayList<String> getResult() {
        return result;
    }


    public Maze getMaze(File fileIn){
        Maze maze = null;
        readFromFile(fileIn);

        if (result.size() < 5){
            System.out.println("Bad maze file format"); //TODO - change to a more informative error
            return maze;
        }

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
            System.out.println("Data in maze input file is invalid. Maze cannot be created");
        }
        return maze;
    }

    void readFromFile(File fileIn){
        try(BufferedReader readFile = new BufferedReader(new FileReader(fileIn))){
           readFromFile(readFile);

        }
        catch (FileNotFoundException e){
            System.out.println("File not found. Exception: " + e);
        }
        catch (IOException e){
            System.out.println("Reading from file failed: " + e);
        }
    }

    void readFromFile(BufferedReader readFile) throws IOException {
        String line;
        while((line = readFile.readLine()) != null) {
            if (line.trim().length() > 0) {
                result.add(line);
            }
        }
    }

    private int numberOf(String key, int lineNumber){
        int index = lineNumber - 1;
        if (index < result.size()) {
            String line = result.get(index).trim();
            String[] strs = line.split("=");
            String num = strs[1].trim();
            if (strs.length != 2 || !strs[0].trim().equals(key) || !num.matches("[0-9]+")) {
                System.out.println("Bad maze file header: expected in line " + lineNumber + " - " + key + " = <num>" + "\n" + " got: " + line);
                return -1;
            }
            try {
                return Integer.parseInt(num);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number " + num + " in line " + lineNumber);
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
                    System.out.println("Wrong character in maze: " +  mazeChar + " in row " + (i+1) + ", col " + (j+1) ); //row-col in file
                    isCharValid = false;
                }
            }
        }

        if (isCharCountValid(countPlayerChar, PLAYER) && isCharCountValid(countEndChar, END) && isCharValid){
            isMazeValid = true;
        }
        return isMazeValid;
    }

    private static boolean isCharCountValid(int count, char mazeChar){
        boolean isValid = false;
        if (count == 0){
            System.out.println("Missing " +  mazeChar + " in maze");
        }
        else if (count > 1){
            System.out.println("More than one " +  mazeChar + " in maze");

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
            System.out.println("Bad maze file header: expected in line 2 - MaxSteps bigger than 0 "
                    + "\n" + "got: " + result.get(1));
            return false;
        }
        return true;
    }

    private boolean isRowsColsValid(int row, int col){
        if ((row < 1 && col < 2) || (row < 2 && col < 1)) {
            System.out.println("Bad maze file header: expected in lines 3,4 - minimum 1 row and 2 columns or 2 rows and 1 column in a maze "
                    + "\n" + "got: " + result.get(2) + " " + result.get(3));
            return false;
        }
        return true;
    }
}
