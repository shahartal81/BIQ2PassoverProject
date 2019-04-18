package additionalclasses;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class InputFileParser {

    private static ArrayList<String> result = new ArrayList<>();
    private static String filename = "C:\\BIQ Training\\Maze Project\\sample.txt";
    String fileHeaderErrorLog;
    String mazeFileErrorLog;

    public static  Maze getMaze() throws Exception{
        Maze maze = new Maze();
        readFromFile(filename);

        int maxSteps = numberOf("MaxSteps", 2);
        int rows = numberOf("Rows", 3);
        int cols = numberOf("Cols", 4);
        maze.setMaxSteps(maxSteps);
        maze.setRows(rows);
        maze.setColumns(cols);
        mazeSymbolsValidation();
        maze.setMazeMap(fillMazeMap(rows, cols));
        return maze;
    }

    public static ArrayList<String> readFromFile(String filename){

        try(BufferedReader readFile = new BufferedReader(new FileReader(filename))){
            while(readFile.ready()) {
                result.add(readFile.readLine());
            }
        }
        catch (FileNotFoundException e){
            System.out.println("File not found. Exception: " + e);
        }

        catch (IOException e){
            System.out.println("Reading from file failed: " + e);
        }
        return result;
    }



    public static int numberOf(String key, int lineNumber){
        String line = result.get(lineNumber - 1).trim();
        String[] strs = line.split("\\s+");
        if ( strs.length != 3 || !strs[0].equals(key) || !strs[1].equals("=") || !strs[2].matches("[0-9]+")){
            System.out.println("expected in line " + lineNumber + " - " + key + " = <num> got: " + "<" + line + ">");
        }

        try{
            return Integer.parseInt(strs[2]);
        } catch (NumberFormatException e){
            e.printStackTrace();
        }

        return 0;
    }

    public static void mazeSymbolsValidation(){
        int countPlayerChar = 0;
        int countFinishChar = 0;
        for (int i = 4; i < result.size(); i++){
            for (int j = 0; j < result.get(i).length(); j++){
                char mazeChar = result.get(i).charAt(j);
                if (mazeChar == '@'){
                    countPlayerChar++;
                }
                else if (mazeChar == '$'){
                    countFinishChar++;
                }
                else if (mazeChar != '#' && mazeChar != ' '){
                    System.out.println("Wrong character in maze: " + "<" + mazeChar + "> in row " + (i+1) + ", col " + (j+1) ); //row-col in file
                }
            }
        }

        if (countPlayerChar == 0 ){
            System.out.println("Missing @ in maze");
        }

        if (countFinishChar == 0 ){
            System.out.println("Missing $ in maze");
        }

        if (countPlayerChar > 1 ){
            System.out.println("More than one @ in maze");
        }

        if (countFinishChar > 1 ){
            System.out.println("More than one $ in maze");
        }
    }

    public static char[][] fillMazeMap(int rows, int cols){
        char[][]mazeMap = new char[rows][cols];
        ArrayList<String> mazeArray = new ArrayList<>();
        for (int i = 4; i < result.size(); i++){
            mazeArray.add(result.get(i));
        }

        for (int row = 0; row < mazeMap.length; row++){
            for (int col = 0; col < mazeMap[row].length; col++){
                mazeMap[row][col] = mazeArray.get(row).charAt(col);
            }
        }
        return mazeMap;
    }
}
