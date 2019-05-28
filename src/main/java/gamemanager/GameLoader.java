package gamemanager;

import additionalclasses.Maze;
import filehandling.ErrorsSingleton;
import filehandling.MazeDefinitionParser;
import filehandling.MazeFileReader;
import player.PlayerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GameLoader {

    public void validateAndStartGame(String inputFile, String outputFile, MazeDefinitionParser inputFileParser) {
        Maze maze = null;

        File fileIn = new File(inputFile);
        if (!fileIn.isFile() || !fileIn.exists()){
            ErrorsSingleton.instance().addToErrorList("Command line argument for maze: " +  fileIn + " doesn't lead to a maze file or leads to a file that cannot be opened");
        }
        else {
            maze = inputFileParser.getMaze(new MazeFileReader().readFromFile(fileIn));
        }

        File fileOut = new File(outputFile);
        if (fileOut.exists()) {
            ErrorsSingleton.instance().addToErrorList("Command line argument for output file: " + fileOut + " points to a bad path or to a file that already exists");
        } else if(maze != null){
            start(maze, fileOut);
        }

        ErrorsSingleton.instance().printErrors();
    }


    public void start(Maze maze, File fileOut) {
        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(fileOut))) {
            PlayerFactory playerFactory = new PlayerFactory();
            GameManager gameManager = new GameManager(playerFactory, maze);
            gameManager.createOutPutFile(fileWriter);
            gameManager.playGame();
        } catch (IOException e) {
            ErrorsSingleton.instance().addToErrorList("Command line argument for output file: " + fileOut + " points to a bad path or to a file that already exists");
        }
    }
}
