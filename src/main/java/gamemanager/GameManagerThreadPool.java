package gamemanager;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class GameManagerThreadPool implements GameManagerStrategy{

    private List<GameManager> gameManagers;
    private int numOfThreads;

    public GameManagerThreadPool(List<GameManager> gameManagers, int numOfThreads) {
        this.gameManagers = gameManagers;
        this.numOfThreads = numOfThreads;
    }

    @Override
    public void start() {
        ExecutorService threadPool = Executors.newFixedThreadPool(numOfThreads);
        for (GameManager gameManager : gameManagers) {
            GameManagerRunner gameManagerRunner = new GameManagerRunner(gameManager);
            threadPool.execute(gameManagerRunner);
        }

        threadPool.shutdown();
        try {
            threadPool.awaitTermination(120, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
