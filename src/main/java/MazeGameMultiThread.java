import filehandling.CommandLineParser;
import filehandling.ErrorsSingleton;
import filehandling.MazeParser;
import gamemanager.GameLoader;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class MazeGameMultiThread {
    public static void main(String[] args) throws IllegalAccessException, ClassNotFoundException, InstantiationException, NoSuchMethodException, InvocationTargetException, InterruptedException, IOException {
        CommandLineParser commandLineParser = new CommandLineParser();
        commandLineParser.validateAndParseArguments(args);
        if (ErrorsSingleton.instance().getErrorsList().isEmpty()) {

            MazeParser inputFileParser = new MazeParser();
            GameLoader gameLoader = new GameLoader();
            gameLoader.parseMazes(commandLineParser.parseMazesFolder(), inputFileParser);
            gameLoader.startGames(commandLineParser.parsePlayersPackage(), commandLineParser.getNumberOfThreads());
            gameLoader.printResults();
        } else {
            ErrorsSingleton.instance().printErrors();
        }
    }
}

