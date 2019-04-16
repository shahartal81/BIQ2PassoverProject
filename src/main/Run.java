package main;

import gamemanager.GameManager;
import player.PlayerSimple;

public class Run {
    public static void main(String[] args) {
        GameManager gm1 = new GameManager(new PlayerSimple());
        gm1.printMaze(gm1.getMaze());
        gm1.playGame();
    }
}
