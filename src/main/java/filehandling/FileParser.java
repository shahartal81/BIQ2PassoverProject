package main.java.filehandling;

import main.java.additionalclasses.Maze;

import java.io.File;
import java.util.List;

public interface FileParser {
    Maze getMaze(List<String> arr);

    void setErrorList(List<String> errorsList);
}
