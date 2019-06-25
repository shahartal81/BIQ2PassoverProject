import filehandling.CommandLineParser;
import filehandling.ErrorsSingleton;
import filehandling.MazeParser;
import gamemanager.MatchManager;
import gamemanager.GameManagerFactory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class MazeGameMultiThread {
    public static void main(String[] args) throws IllegalAccessException, ClassNotFoundException, InstantiationException, NoSuchMethodException, InvocationTargetException, InterruptedException, IOException {
        CommandLineParser commandLineParser = new CommandLineParser();
        commandLineParser.validateAndParseArguments(args);
        if (ErrorsSingleton.instance().getErrorsList().isEmpty()) {

            MazeParser inputFileParser = new MazeParser();
            MatchManager matchManager = new MatchManager();
            GameManagerFactory gameManagerFactory = new GameManagerFactory();
            matchManager.clearOutPutFiles();
            matchManager.parseMazes(commandLineParser.parseMazesFolder(), inputFileParser);
            matchManager.startGames(gameManagerFactory, commandLineParser.parsePlayersPackage(), commandLineParser.getNumberOfThreads());
            matchManager.printResults();
        } else {
            ErrorsSingleton.instance().printErrors();
        }
    }
}

