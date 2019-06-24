package cucumber;

import additionalclasses.Maze;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import filehandling.CommandLineParser;
import filehandling.MazeParser;
import gamemanager.GameLoader;
import gamemanager.GameManagerFactory;
import gamemanager.GameResult;
import org.junit.Assert;
import player.PlayerBookmarkEachStep;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class E2ETestStepdefs {

    private List<Maze> mazes = new ArrayList<>();
    private List<String> mazesFiles = new ArrayList<>();
    private List<Class<?>> players = new ArrayList<>();
    private GameLoader gameLoader = new GameLoader();
    private MazeParser mazeParser = new MazeParser();
    private Map<String, List<List<String>>> resultsByMazeName = new HashMap<>();


    @Given ("we have mazes from following files")
    public void weParseMases(List<String> files){
        for (String file : files){
            mazesFiles.add(file);
        }
        gameLoader.parseMazes(mazesFiles, mazeParser);

        for (Maze maze : gameLoader.getMazes()){
            mazes.add(maze);
        }
    }

    @And("we have players")
    public void weHavePlayers() {
        CommandLineParser parser = new CommandLineParser();
        players = parser.parsePlayersPackage();
    }


    @And("we start a game for multiple players in {int} threads")
    public void weStartAGameForMultiplePlayers(int threads) throws IllegalAccessException, InvocationTargetException, InstantiationException, InterruptedException, NoSuchMethodException, ClassNotFoundException {
        GameManagerFactory gameManagerFactory = new GameManagerFactory();
        gameLoader.startGames(gameManagerFactory, players, threads);
    }

    @Then("we get game results")
    public void weGetGameResults() {
        Map<Maze, List<GameResult>> allGamesResults = gameLoader.printResults();
        for (Map.Entry<Maze, List<GameResult>> gamesPerMaze: allGamesResults.entrySet()){
            List<GameResult> gamesResults = gamesPerMaze.getValue();
            List<List<String>> resultsWithPlayerName = new ArrayList<>();
            for (GameResult result : gamesResults){
                List<String> resultsAsString = new ArrayList<>();
                resultsAsString.add(result.getPlayer().getPlayerName());
                resultsAsString.add(String.valueOf(result.isSolved()));
                resultsAsString.add(String.valueOf(result.getUsedSteps()));
                resultsWithPlayerName.add(resultsAsString);
            }
            resultsByMazeName.put(gamesPerMaze.getKey().getMazeName(), resultsWithPlayerName );
        }
    }

    @Then("results for {string} are")
    public void resultsForAre(String mazeName, List<List<String>> expected) {
        //removing data of the player that has random results each time
        List<List<String>> result = resultsByMazeName.get(mazeName)
                .stream()
                .filter(r->! r.contains("PlayerBookmarkEachStep"))
                .collect(Collectors.toList());
        Assert.assertEquals(expected, result);
    }
}