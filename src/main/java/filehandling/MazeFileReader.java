package filehandling;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MazeFileReader {

    public List<String> readFromFile(File fileIn){
        try(BufferedReader readFile = new BufferedReader(new FileReader(fileIn))){
            return readFromFile(readFile);
        }
        catch (FileNotFoundException e){
            ErrorsSingleton.instance().addToErrorList("File not found. Exception: " + e);
        }
        catch (IOException e){
            ErrorsSingleton.instance().addToErrorList("Reading from file failed: " + e);
        }

        return new ArrayList<>();
    }

    List<String> readFromFile(BufferedReader readFile) throws IOException {
        List<String> result = new ArrayList<>();

        String line;
        while((line = readFile.readLine()) != null) {
            result.add(line);
        }
        return result;
    }
}
