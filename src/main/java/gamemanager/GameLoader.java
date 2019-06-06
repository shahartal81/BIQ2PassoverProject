package gamemanager;

import additionalclasses.Maze;
import filehandling.ErrorsSingleton;
import filehandling.MazeDefinitionParser;
import filehandling.MazeFileReader;
import player.PlayerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class GameLoader {

    List<Maze> mazes = new ArrayList<>();
    List<GameManager> gameManagers = new ArrayList<>();

    public boolean validateArguments(String inputFile, String outputFile) {

        File fileIn = new File(inputFile);
        if (!fileIn.isFile() || !fileIn.exists()){
            ErrorsSingleton.instance().addToErrorList("Command line argument for maze: " +  fileIn + " doesn't lead to a maze file or leads to a file that cannot be opened");
        }

        File fileOut = new File(outputFile);
        if (fileOut.exists()) {
            ErrorsSingleton.instance().addToErrorList("Command line argument for output file: " + fileOut + " points to a bad path or to a file that already exists");
        }

        ErrorsSingleton.instance().printErrors();
        return ErrorsSingleton.instance().getErrorsList().isEmpty();
    }

    public void parseMaze(String inputFile, MazeDefinitionParser inputFileParser) {
        File fileIn = new File(inputFile);
        Maze maze = inputFileParser.getMaze(new MazeFileReader().readFromFile(fileIn));
        if (maze != null) {
            mazes.add(maze);
        }
    }

    public void parseMazes(List<String> mazeList, MazeDefinitionParser inputFileParser) {
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

    public void startGames(List<String> playerList, int numOfThreads) throws IllegalAccessException, InstantiationException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InterruptedException, IOException {
        for (Maze maze : mazes) {
            for (String player : playerList) {
                GameManager gameManager = new GameManager(player, maze);
                gameManagers.add(gameManager);
            }
        }
        ExecutorService threadPool = Executors.newFixedThreadPool(numOfThreads);
        for (GameManager gameManager : gameManagers) {
                GameManagerRunner gameManagerRunner = new GameManagerRunner(gameManager);
                threadPool.execute(gameManagerRunner);
        }

        threadPool.shutdown();
        threadPool.awaitTermination(120, TimeUnit.SECONDS);
    }
}
