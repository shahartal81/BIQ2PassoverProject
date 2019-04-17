package main;

import gamemanager.GameManager;
import player.PlayerFactory;

public class Run {
    public static void main(String[] args) {
        PlayerFactory playerFactory = new PlayerFactory();

        GameManager gameManager = new GameManager(playerFactory);
        gameManager.printMaze();
        gameManager.playGame();
    }
}
