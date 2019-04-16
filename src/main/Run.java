package main;

import gamemanager.GameManager;

public class Run {
    public static void main(String[] args) {
        GameManager gm1 = new GameManager();
        gm1.printMaze();
        gm1.playGame();
    }
}
