import filehandling.CommandLineParser;
import filehandling.MazeDefinitionParser;
import filehandling.MazeParser;
import gamemanager.GameLoader;
import player.Player;

import java.util.List;

public class MazeGameMultiThread {
    public static void main(String[] args) {
        if (args.length < 4) {
            System.out.println("Missing arguments in command line");
        } else {
            CommandLineParser commandLineParser = new CommandLineParser(args);
            List<String> mazeList = commandLineParser.parseMazesFolder();
            List<String> playerList = commandLineParser.parsePlayersPackage();

            System.out.println(mazeList);
            System.out.println(playerList);
            System.out.println(commandLineParser.getNumberOfThreads());

            for(String maze: mazeList) {
                GameLoader gameLoader = new GameLoader();
                MazeDefinitionParser inputFileParser = new MazeParser();
                gameLoader.validateAndStartGame(maze, maze + ".out", inputFileParser);
            }
        }
    }
}

