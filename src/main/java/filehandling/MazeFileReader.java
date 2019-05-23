package filehandling;


import gamemanager.GameLoader;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MazeFileReader {
    private GameLoader gameLoader = new GameLoader();
    private List<String> result = new ArrayList<>();

    public List<String> readFromFile(File fileIn){
        List<String>fileLines = new ArrayList<>();
        try(BufferedReader readFile = new BufferedReader(new FileReader(fileIn))){
            fileLines =  readFromFile(readFile);
        }
        catch (FileNotFoundException e){
            gameLoader.addToErrorList("File not found. Exception: " + e);
        }
        catch (IOException e){
            gameLoader.addToErrorList("Reading from file failed: " + e);
        }
        return fileLines;
    }

    List<String> readFromFile(BufferedReader readFile) throws IOException {
        String line;
        while((line = readFile.readLine()) != null) {
            if (line.trim().length() > 0) {
                result.add(line);
            }
        }
        return result;
    }
}
