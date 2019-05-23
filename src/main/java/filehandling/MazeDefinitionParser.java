package filehandling;

import additionalclasses.Maze;

import java.util.List;

public interface MazeDefinitionParser {
    Maze getMaze(List<String> arr);

//    void setErrorList(List<String> errorsList);
}
