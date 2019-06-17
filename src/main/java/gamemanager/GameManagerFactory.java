package gamemanager;

import java.util.List;

public class GameManagerFactory {

    public GameManagerStrategy chooseGameManagerStrategy(List<GameManager> gameManagerList, int numOfThreads){
        if (numOfThreads == 1){
            return new GameManagerThreadPool(gameManagerList, numOfThreads);
        } else {
            return new GameManagerSingleThread(gameManagerList);
        }
    }
}
