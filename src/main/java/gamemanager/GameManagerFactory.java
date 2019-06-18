package gamemanager;

import java.util.List;

public class GameManagerFactory {

    public GameManagerStrategy chooseGameManagerStrategy(List<GameManager> gameManagerList, int numOfThreads){
        if (numOfThreads == 1){
            return new GameManagerSingleThread(gameManagerList);
        } else {
            return new GameManagerThreadPool(gameManagerList, numOfThreads);
        }
    }
}
