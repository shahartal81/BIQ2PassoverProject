import filehandling.MazeParser;
import gamemanager.GameLoader;

public class MazeGame {
    public static void main(String[] args) {
        MazeParser inputFileParser = new MazeParser();
        GameLoader gameLoader = new GameLoader();
        if (gameLoader.validateArguments(args)) {
            gameLoader.parseMaze(args[0], inputFileParser);
            if (gameLoader.getMazesNumber() > 0) {
                gameLoader.start(args[1]);
            }
        }
    }
}

