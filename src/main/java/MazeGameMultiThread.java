import filehandling.CommandLineParser;
import filehandling.ErrorsSingleton;
import filehandling.MazeParser;
import gamemanager.GameLoader;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class MazeGameMultiThread {
    public static void main(String[] args) throws IllegalAccessException, ClassNotFoundException, InstantiationException, NoSuchMethodException, InvocationTargetException, InterruptedException, IOException {
        CommandLineParser commandLineParser = new CommandLineParser();
        commandLineParser.validateAndParseArguments(args);
        if (ErrorsSingleton.instance().getErrorsList().isEmpty()) {
            List<String> mazeList = commandLineParser.parseMazesFolder();
            List<String> playerList = commandLineParser.parsePlayersPackage();

            GameLoader gameLoader = new GameLoader();
            MazeParser inputFileParser = new MazeParser();
            gameLoader.parseMazes(mazeList, inputFileParser);
            gameLoader.startGames(playerList, commandLineParser.getNumberOfThreads());
            gameLoader.printResults();
        } else {
            ErrorsSingleton.instance().printErrors();
        }
    }
}

