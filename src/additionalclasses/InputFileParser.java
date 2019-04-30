package additionalclasses;

import com.sun.deploy.util.StringUtils;

import java.io.*;
import java.util.ArrayList;

public class InputFileParser {

    private static ArrayList<String> result = new ArrayList<>();
//    private static String filename = "sample.txt";
    private static int maxSteps;
    private static int rows;
    private static int cols;
    private static final char PLAYER = MazeElement.PLAYER.getValue();
    private static final char END = MazeElement.END.getValue();
    private static final char WALL = MazeElement.WALL.getValue();
    private static final char PASS = MazeElement.PASS.getValue();


    public static  Maze getMaze(File fileIn){
        Maze maze = null;
        readFromFile(fileIn);

        if (result.size() < 5){
            System.out.println("Bad maze file format"); //TODO - change to a more informative error
            return maze;
        }

        maxSteps = numberOf("MaxSteps", 2);
        rows = numberOf("Rows", 3);
        cols = numberOf("Cols", 4);

        if(isValid(maxSteps) && isValid(rows, cols)) {
            maze = new Maze();
            maze.setMaxSteps(maxSteps);
            maze.setRows(rows);
            maze.setColumns(cols);
            mazeSymbolsValidation();
            maze.setMazeMap(fillMazeMap(rows, cols));
        }
       else {
            System.out.println("Maze cannot be created");
        }
        return maze;
    }

    public static void readFromFile(File fileIn){

        try(BufferedReader readFile = new BufferedReader(new FileReader(fileIn))){
            String line;
            while((line = readFile.readLine()) != null) {
                if (line.trim().length() > 0) {
                    result.add(line);
                }
            }
        }
        catch (FileNotFoundException e){
            System.out.println("File not found. Exception: " + e);
        }
        catch (IOException e){
            System.out.println("Reading from file failed: " + e);
        }
    }

    public static int numberOf(String key, int lineNumber){
        if (lineNumber < result.size()) {
            String line = result.get(lineNumber - 1).trim();
            String[] strs = line.split("=");
            if (strs.length != 2 || !strs[0].trim().equals(key) || !strs[1].trim().matches("[0-9]+")) {
                System.out.println("Bad maze file header: expected in line " + lineNumber + " - " + key + " = <num>" + "\n" + " got: " + line);
                return -1;
            }
            try {
                return Integer.parseInt(strs[1].trim());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return -1;
    }

    public static void mazeSymbolsValidation(){
        int countPlayerChar = 0;
        int countEndChar = 0;
        for (int i = 4; i < result.size(); i++){
            for (int j = 0; j < result.get(i).length(); j++){
                char mazeChar = result.get(i).charAt(j);
                if (mazeChar == PLAYER){
                    countPlayerChar++;
                }
                else if (mazeChar == END){
                    countEndChar++;
                }
                else if (mazeChar != WALL && mazeChar != PASS){
                    System.out.println("Wrong character in maze: " + "<" + mazeChar + "> in row " + (i+1) + ", col " + (j+1) ); //row-col in file
                }
            }
        }

        validateMazeChar(countPlayerChar, PLAYER);
        validateMazeChar(countEndChar, END);
    }

    private static void validateMazeChar (int count, char mazeChar){
        if (count == 0){
            System.out.println("Missing " +  mazeChar + " in maze");
        }

        if (count > 1){
            System.out.println("More than one " +  mazeChar + " in maze");
        }
    }

    public static char[][] fillMazeMap(int rows, int cols){
        char[][]mazeMap = new char[rows][cols];
        ArrayList<String> mazeArray = new ArrayList<>();
        for (int i = 4; i < result.size(); i++){
            mazeArray.add(result.get(i));
        }

        //add spaces for missing columns
        for (String str : mazeArray){
            if (str.length() < cols){
                int diff = cols - str.length();
                int index = mazeArray.indexOf(str);
                mazeArray.set(index, addColumns(str, diff));
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

    private static String addColumns(String str, int diff) {
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

    private static boolean isValid(int steps){
        if (steps == 0) {
            System.out.println("Bad maze file header: expected in line 2 - MaxSteps bigger than 0 "
                    + "\n" + "got: " + result.get(1));
            return false;
        }
        return true;
    }

    private static boolean isValid(int row, int col){
        if ((row <= 1 && col <= 2) || (row <= 2 && col <= 1)) {
            System.out.println("Bad maze file header: expected in lines 3,4 - minimum 1 row and 2 columns or 2 rows and 1 column in a maze "
                    + "\n" + "got: " + result.get(2) + " " + result.get(3));
            return false;
        }

        return true;
    }
}
