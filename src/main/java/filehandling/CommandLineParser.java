package filehandling;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.reflections.Reflections;
import player.Player;

public class CommandLineParser {

    Map<String, String> commands = new HashMap<>();

    public CommandLineParser(String[] args) {
        validateAndParseArguments(args);
    }

    private void validateAndParseArguments(String[] args) {
        if (args[0].equals("-mazes_folder") && args[1].charAt(0) != '-') {
            commands.put(args[0].substring(1), args[1]);
        } else {
            throw new IllegalArgumentException("Not a valid argument: " + args[0]);
        }

        if (args[2].equals("-players") && args[3].charAt(0) != '-') {
            commands.put(args[2].substring(1), args[3]);
        } else {
            throw new IllegalArgumentException("Not a valid argument: " + args[2]);
        }

        if(args.length == 5) {
            throw new IllegalArgumentException("Not a valid argument: " + args[4]);
        }
        //Optional threads argument
        if(args.length == 6) {
            if (args[4].equals("-threads") && args[5].charAt(0) != '-') {
                commands.put(args[4].substring(1), args[5]);
            } else {
                throw new IllegalArgumentException("Not a valid argument: " + args[4]);
            }
        }
    }

    public String getMazesFolder() {
        return commands.get("mazes_folder");
    }

    public String getPlayersPackage() {
        return commands.get("players");
    }

    public Integer getNumberOfThreads() {
        return Integer.parseInt(commands.get("threads"));
    }

    public List<String> parseMazesFolder() {
        File folder = new File(commands.get("mazes_folder"));
        if(folder.isDirectory()) {
            List<String> list = new ArrayList<>();
            for (File fileEntry : Objects.requireNonNull(folder.listFiles())) {
                list.add(fileEntry.toString());
            }
            return list;
        } else {
            throw new IllegalArgumentException(commands.get("mazes_folder") + " is not a folder");
        }
    }

    public List<String> parsePlayersPackage() {
        Reflections reflections = new Reflections(commands.get("players"));
        List<String> list = new ArrayList<>();

        Set<Class<? extends Player>> allClasses =
                reflections.getSubTypesOf(Player.class);

        for(Class<? extends Player> aClass: allClasses) {
            list.add(aClass.getSimpleName());
        }
        return list;
    }
}
