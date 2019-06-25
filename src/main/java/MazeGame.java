import filehandling.MazeParser;
import gamemanager.MatchManager;

public class MazeGame {
    public static void main(String[] args) {
        MazeParser inputFileParser = new MazeParser();
        MatchManager matchManager = new MatchManager();
        if (matchManager.validateArguments(args)) {
            matchManager.parseMaze(args[0], inputFileParser);
            if (matchManager.getMazesNumber() > 0) {
                matchManager.start(args[1]);
            }
        }
    }
}

