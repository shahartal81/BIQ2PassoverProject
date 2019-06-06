import filehandling.CommandLineParser;
import filehandling.MazeDefinitionParser;
import filehandling.MazeParser;
import gamemanager.GameLoader;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class MazeGameMultiThread {
    public static void main(String[] args) throws IllegalAccessException, ClassNotFoundException, InstantiationException, NoSuchMethodException, InvocationTargetException, InterruptedException, IOException {
        if (args.length < 4) {
            System.out.println("Missing arguments in command line");
        } if (args.length > 6) {
            System.out.println("Too many arguments in command line");
        } else {
            CommandLineParser commandLineParser = new CommandLineParser(args);
            List<String> mazeList = commandLineParser.parseMazesFolder();
            List<String> playerList = commandLineParser.parsePlayersPackage();

            System.out.println(mazeList);
            System.out.println(playerList);
            System.out.println(commandLineParser.getNumberOfThreads());

            GameLoader gameLoader = new GameLoader();
            MazeDefinitionParser inputFileParser = new MazeParser();
            gameLoader.parseMazes(mazeList, inputFileParser);
            gameLoader.startGames(playerList, commandLineParser.getNumberOfThreads());
        }
    }
}

