package main.java.filehandling;

import main.java.additionalclasses.Maze;

import java.io.File;
import java.util.List;

public interface FileParser {
    Maze getMaze(File fileIn);

    void setErrorList(List<String> errorsList);
}
