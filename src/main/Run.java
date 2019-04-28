package main;

import gamemanager.GameManager;
import player.PlayerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Run {
    public static void main(String[] args) {
        String filePath = "test.txt";
        if (args.length < 2) {
            System.out.println("Missing output file argument in command line");
        } else {
            File file = new File(filePath);
            if (file.exists()) {
                System.out.println("File points to a bad path or to a file that already exists");
            } else {
                BufferedWriter fileWriter = null;
                try {
                    fileWriter = new BufferedWriter(new FileWriter(file));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                PlayerFactory playerFactory = new PlayerFactory();

                GameManager gameManager = new GameManager(playerFactory);
//                gameManager.printMaze();
                gameManager.createOutPutFile(fileWriter);
                gameManager.playGame();
            }
        }
    }
}

