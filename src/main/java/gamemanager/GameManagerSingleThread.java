package gamemanager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class GameManagerSingleThread implements GameManagerStrategy {

    private List<GameManager> gameManagers;

    public GameManagerSingleThread(List<GameManager> gameManagers) {
        this.gameManagers = gameManagers;
    }

    @Override
    public void start() {
        for (GameManager gameManager : gameManagers) {
            File fileOut = new File(gameManager.toString() + ".out");
            try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(fileOut))) {
                gameManager.createOutPutFile(fileWriter);
                gameManager.playGame();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
