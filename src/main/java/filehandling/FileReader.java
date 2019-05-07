package main.java.filehandling;

import main.java.gamemanager.GameLoader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileReader {
    private GameLoader gameLoader = new GameLoader();
    List<String> result = new ArrayList<>();

    public List<String> readFromFile(File fileIn){
        try(BufferedReader readFile = new BufferedReader(new java.io.FileReader(fileIn))){
            return readFromFile(readFile);
        }
        catch (FileNotFoundException e){
            gameLoader.addToErrorList("File not found. Exception: " + e);
        }
        catch (IOException e){
            gameLoader.addToErrorList("Reading from file failed: " + e);
        }
        return null;
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
