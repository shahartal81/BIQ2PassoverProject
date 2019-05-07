package main.java.gamemanager;

import main.java.additionalclasses.Maze;
import main.java.filehandling.FileParser;
import main.java.player.PlayerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameLoader {

    private List<String> errorsList;

    


    public void validateAndStartGame(String[] args, FileParser inputFileParser) {
        errorsList = new ArrayList<>();
        Maze maze = null;

        File fileIn = new File(args[0]);
        if (!fileIn.isFile() || !fileIn.exists()){
            addToErrorList("Command line argument for maze: " +  fileIn + " doesn't lead to a maze file or leads to a file that cannot be opened");
        }
        else {
            inputFileParser.setErrorList(errorsList);
            maze = inputFileParser.getMaze(fileIn);
        }

        File fileOut = new File(args[1]);
        if (fileOut.exists()) {
            addToErrorList("Command line argument for output file: " + fileOut + " points to a bad path or to a file that already exists");
        } if(maze != null){
            start(maze, fileOut);
        } else {
            for (String error : errorsList){
                System.out.println(error);
            }
        }
    }


    public void start(Maze maze, File fileOut) {
        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(fileOut))) {
            PlayerFactory playerFactory = new PlayerFactory();
            GameManager gameManager = new GameManager(playerFactory, maze);
            gameManager.createOutPutFile(fileWriter);
            gameManager.playGame();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addToErrorList(String error){
        errorsList.add(error);
    }

    public List<String> getErrorsList() {
        return errorsList;
    }


}
