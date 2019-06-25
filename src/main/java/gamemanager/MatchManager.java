package gamemanager;

import additionalclasses.Maze;
import filehandling.ErrorsSingleton;
import filehandling.MazeFileReader;
import filehandling.MazeParser;
import player.PlayerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

public class MatchManager {

    List<Maze> mazes = new ArrayList<>();
    List<GameManager> gameManagers = new ArrayList<>();

    public List<Maze> getMazes() {
        return mazes;
    }

    public boolean validateArguments(String[] args) {

        if (args.length == 0){
            ErrorsSingleton.instance().addToErrorList("Missing maze file argument in command line");
            return false;
        }
        if (args.length < 2) {
            ErrorsSingleton.instance().addToErrorList("Missing output file argument in command line");
            return false;
        }

        File fileIn = new File(args[0]);
        if (!fileIn.isFile() || !fileIn.exists()){
            ErrorsSingleton.instance().addToErrorList("Command line argument for maze: " +  fileIn + " doesn't lead to a maze file or leads to a file that cannot be opened");
        }

        File fileOut = new File(args[1]);
        if (fileOut.exists()) {
            ErrorsSingleton.instance().addToErrorList("Command line argument for output file: " + fileOut + " points to a bad path or to a file that already exists");
        }

        ErrorsSingleton.instance().printErrors();
        return ErrorsSingleton.instance().getErrorsList().isEmpty();
    }

    public void parseMaze(String inputFile, MazeParser inputFileParser) {
        File fileIn = new File(inputFile);
        Maze maze = inputFileParser.getMaze(new MazeFileReader().readFromFile(fileIn));
        if (maze != null) {
            mazes.add(maze);
        }
    }

    public void parseMazes(List<String> mazeList, MazeParser inputFileParser) {
        for (String maze: mazeList) {
            parseMaze(maze, inputFileParser);
        }
    }

    public int getMazesNumber() {
        return mazes.size();
    }

    public void start(String outputFile) {
        File fileOut = new File(outputFile);
        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(fileOut))) {
            PlayerFactory playerFactory = new PlayerFactory();
            GameManager gameManager = new GameManager(playerFactory, mazes.get(0));
            gameManager.createOutPutFile(fileWriter);
            gameManager.playGame();
        } catch (IOException e) {
            ErrorsSingleton.instance().addToErrorList("Command line argument for output file: " + fileOut + " points to a bad path or to a file that already exists");
        }
    }

    public void startGames(GameManagerFactory gameManagerFactory, List<Class<?>> playerList, int numOfThreads) throws IllegalAccessException, InstantiationException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InterruptedException {
        for (Maze maze : mazes) {
            for (Class<?> player : playerList) {
                GameManager gameManager = new GameManager(player, maze);
                gameManagers.add(gameManager);
            }
        }

        GameManagerStrategy gameManagerStrategy = gameManagerFactory.chooseGameManagerStrategy(gameManagers, numOfThreads);
        gameManagerStrategy.start();
    }


    public Map<Maze, List<GameResult>> printResults() {
      Map<Maze, List<GameResult>> results = gameManagers.stream().collect(groupingBy(GameManager::getMaze,
                mapping(GameManager::getGameResult, toList())));
      boolean didPrintHeaders = false;
      for(Map.Entry<Maze, List<GameResult>> entry : results.entrySet()) {
          //sorting by players so that the same player will be shown on the same column
          entry.getValue().sort(Comparator.comparing(gameResult -> gameResult.getPlayer().getPlayerName()));
          if(!didPrintHeaders){
              String paddedPlayersNames = entry.getValue().stream()
                      .map(gameResult -> getPaddedString(gameResult.getPlayer().getPlayerName()))
                      .collect(Collectors.joining("|"));
              System.out.println(getPaddedString("Maze Name") + "|" + paddedPlayersNames);
              didPrintHeaders = true;
          }
          String paddedResults = entry.getValue().stream()
                  .map(gameResult -> getPaddedString(getResultSummary(gameResult)))
                  .collect(Collectors.joining("|"));
          System.out.println(getPaddedString(entry.getKey().getMazeName()) + "|" + paddedResults);
      }
      return results;
    }

    //TODO: Calculate the Max(playerName, mazeName) and use that as padding instead of hardcoded 50
    private String getPaddedString(String string) {
        return String.format(" %-50s\t", string);
    }

    private String getResultSummary(GameResult gameResult) {
        if(gameResult.isSolved()) {
            return "✔ - " + gameResult.getUsedSteps();
        } else {
            return "X";
        }
    }

    public void clearOutPutFiles() {
        File directory = new File(System.getProperty("user.dir") + "/out/");

        File[] files = directory.listFiles();
        for (File file : files)
        {
            file.delete();
        }
    }
}