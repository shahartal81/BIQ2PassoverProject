package filehandling;

import additionalclasses.Maze;

import java.io.File;
import java.util.List;

public interface FileParser {
    Maze getMaze(List<String> arr);

    void setErrorList(List<String> errorsList);
}
