package main;

import additionalclasses.InputFileParser;
import additionalclasses.Maze;
import gamemanager.GameManager;
import player.PlayerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Run {
    public static void main(String[] args) {
//        String filePath = "test.txt";
        if (args.length == 0){
            System.out.println("Missing maze file argument in command line");
        }
        if (args.length < 2) {
            System.out.println("Missing output file argument in command line");
        } else {
            new Run().validateAndStartGame(args);
        }
    }

    private void validateAndStartGame(String[] args) {
        Maze maze = null;
        File fileIn = new File(args[0]);
        if (!fileIn.isFile() || !fileIn.exists()){
            System.out.println("Command line argument for maze: " +  fileIn + " doesn't lead to a maze file or leads to a file that cannot be opened");
        }
        else {
            maze = InputFileParser.getMaze(fileIn);
        }

        File fileOut = new File(args[1]);
        if (fileOut.exists()) {
            System.out.println("Command line argument for output file: " + fileOut + " points to a bad path or to a file that already exists");
        } else if(maze != null){
            start(maze, fileOut);
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
}

