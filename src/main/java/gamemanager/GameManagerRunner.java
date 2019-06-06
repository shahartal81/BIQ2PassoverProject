package gamemanager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GameManagerRunner implements Runnable {

    private GameManager gameManager;

    public GameManagerRunner(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public void run() {
        File fileOut = new File(gameManager.toString() + ".out");
        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(fileOut))) {
            gameManager.createOutPutFile(fileWriter);
            gameManager.playGame();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
