package main;

import gamemanager.GameManager;
import player.PlayerFactory;

import java.io.File;

public class Run {
    public static void main(String[] args) {
        PlayerFactory playerFactory = new PlayerFactory();

        GameManager gameManager = new GameManager(playerFactory);
        gameManager.printMaze();
        gameManager.playGame();
        String filePath = "test.txt";
//        if (args.length < 2) {
//            System.out.println("Missing output file argument in command line");
//        } else {
            File file = new File(filePath);
            if (file.exists()) {
                System.out.println("File points to a bad path or to a file that already exists");
            } else {
                GameManager gm1 = new GameManager(file);
                gm1.printMaze();
                gm1.playGame();
            }
//        }
    }
}
