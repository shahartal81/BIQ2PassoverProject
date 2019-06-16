package gamemanager;

import java.util.List;

public class GameManagerSingleThread implements GameManagerStrategy {

    private List<GameManager> gameManagers;

    public GameManagerSingleThread(List<GameManager> gameManagers) {
        this.gameManagers = gameManagers;
    }

    @Override
    public void start() {
        for (GameManager gameManager : gameManagers) {
            gameManager.playGame();
        }
    }
}
