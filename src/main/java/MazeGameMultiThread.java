import filehandling.CommandLineParser;
import filehandling.ErrorsSingleton;
import filehandling.MazeParser;
import gamemanager.GameLoader;
import gamemanager.GameManagerFactory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class MazeGameMultiThread {
    public static void main(String[] args) throws IllegalAccessException, ClassNotFoundException, InstantiationException, NoSuchMethodException, InvocationTargetException, InterruptedException, IOException {
        CommandLineParser commandLineParser = new CommandLineParser();
        commandLineParser.validateAndParseArguments(args);
        if (ErrorsSingleton.instance().getErrorsList().isEmpty()) {

            MazeParser inputFileParser = new MazeParser();
            GameLoader gameLoader = new GameLoader();
            GameManagerFactory gameManagerFactory = new GameManagerFactory();
            gameLoader.parseMazes(commandLineParser.parseMazesFolder(), inputFileParser);
            gameLoader.startGames(gameManagerFactory, commandLineParser.parsePlayersPackage(), commandLineParser.getNumberOfThreads());
            gameLoader.printResults();
        } else {
            ErrorsSingleton.instance().printErrors();
        }
    }
}

