package filehandling;

import org.reflections.Reflections;
import player.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class CommandLineParser {

    private Map<String, String> commands = new HashMap<>();
    private static final String MAZES_FOLDER = "mazes_folder";
    private static final String PLAYERS = "players";
    private static final String THREADS = "threads";

    public CommandLineParser(String[] args) {
        validateAndParseArguments(args);
    }

    private void validateAndParseArguments(String[] args) {
        if (args[0].charAt(0) != '-' && args[0].substring(1).equals(MAZES_FOLDER) && args[1].charAt(0) != '-') {
            commands.put(MAZES_FOLDER, args[1]);
        } else {
            //TODO maybe use the singleton of errors also here
            throw new IllegalArgumentException("Not a valid argument: " + args[0]);
        }

        if (args[2].charAt(0) != '-' && args[2].substring(1).equals(PLAYERS) && args[3].charAt(0) != '-') {
            commands.put(PLAYERS, args[3]);
        } else {
            //TODO maybe use the singleton of errors also here
            throw new IllegalArgumentException("Not a valid argument: " + args[2]);
        }

        if(args.length == 5) {
            //TODO maybe use the singleton of errors also here
            throw new IllegalArgumentException("Not a valid argument: " + args[4]);
        }
        //Optional threads argument
        if(args.length == 6) {
            if (args[4].charAt(0) != '-' && args[4].substring(1).equals(THREADS) && (int) args[5].charAt(0) > 0) {
                commands.put(THREADS, args[5]);
            } else {
                //TODO maybe use the singleton of errors also here
                throw new IllegalArgumentException("Not a valid argument: " + args[4]);
            }
        } else {
            commands.put(THREADS, String.valueOf(1));
        }
    }

    public Integer getNumberOfThreads() {
        return Integer.parseInt(commands.get(THREADS));
    }

    public List<String> parseMazesFolder() {
        File folder = new File(commands.get(MAZES_FOLDER));
        if(folder.isDirectory()) {
            List<String> mazesList = new ArrayList<>();
            for (File fileEntry : Objects.requireNonNull(folder.listFiles())) {
                mazesList.add(fileEntry.toString());
            }
            return mazesList;
        } else {
            throw new IllegalArgumentException(commands.get(MAZES_FOLDER) + " is not a folder");
        }
    }

    public List<String> parsePlayersPackage() {
        Reflections reflections = new Reflections(commands.get(PLAYERS));
        List<String> playerList = new ArrayList<>();

        Set<Class<? extends Player>> allPlayerClasses =
                reflections.getSubTypesOf(Player.class);

        for(Class<? extends Player> playerClass: allPlayerClasses) {
            playerList.add(playerClass.getName());
        }
        return playerList;
    }
}
