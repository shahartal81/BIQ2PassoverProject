package filehandling;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import player.PlayerFactoryTest;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class CommandLineParserTest {

    @Test
    public void validateArgumentsHappyPathTest() {
        String[] arguments = {"-mazes_folder", "c:\\temp", "-players", "player"};
        CommandLineParser commandLineParser = new CommandLineParser();
        commandLineParser.validateAndParseArguments(arguments);
        List<String> errors = ErrorsSingleton.instance().getErrorsList();
        Assert.assertTrue("Error list is not empty", errors.isEmpty());
    }

    @Test
    public void validateArgumentsHappyPathWithThreadsTest() {
        String[] arguments = {"-mazes_folder", "c:\\temp", "-players", "player", "-threads", "2"};
        CommandLineParser commandLineParser = new CommandLineParser();
        commandLineParser.validateAndParseArguments(arguments);
        List<String> errors = ErrorsSingleton.instance().getErrorsList();
        Assert.assertTrue("Error list is not empty", errors.isEmpty());
    }

    @Test
    public void validateArgumentsInvalidFirstArgument() {
        String[] arguments = {"-mases_folder", "c:\\temp", "-players", "player", "-threads", "2"};
        CommandLineParser commandLineParser = new CommandLineParser();
        commandLineParser.validateAndParseArguments(arguments);
        List<String> errors = ErrorsSingleton.instance().getErrorsList();
        Assert.assertTrue(errors.contains("Not a valid argument: " + arguments[0]));
    }

    @Test
    public void validateArgumentsInvalidSecondArgument() {
        String[] arguments = {"-mazes_folder", "c:\\temp", "-playerz", "player", "-threads", "2"};
        CommandLineParser commandLineParser = new CommandLineParser();
        commandLineParser.validateAndParseArguments(arguments);
        List<String> errors = ErrorsSingleton.instance().getErrorsList();
        Assert.assertTrue(errors.contains("Not a valid argument: " + arguments[2]));
    }

    @Test
    public void validateArgumentsInvalidThirdArgument() {
        String[] arguments = {"-mazes_folder", "c:\\temp", "-players", "player", "-threadz", "2"};
        CommandLineParser commandLineParser = new CommandLineParser();
        commandLineParser.validateAndParseArguments(arguments);
        List<String> errors = ErrorsSingleton.instance().getErrorsList();
        Assert.assertTrue(errors.contains("Not a valid argument: " + arguments[4]));
    }

    @Test
    public void validateArgumentsInvalidThreadsNumber() {
        String[] arguments = {"-mazes_folder", "c:\\temp", "-players", "player", "-threads", "c"};
        CommandLineParser commandLineParser = new CommandLineParser();
        commandLineParser.validateAndParseArguments(arguments);
        List<String> errors = ErrorsSingleton.instance().getErrorsList();
        Assert.assertTrue(errors.contains("Not a valid number: " + arguments[5]));
    }

    @Test
    public void getNumberOfThreads() {
        String[] arguments = {"-mazes_folder", "c:\\temp", "-players", "player", "-threads", "5"};
        CommandLineParser commandLineParser = new CommandLineParser();
        commandLineParser.validateAndParseArguments(arguments);
        Assert.assertEquals(arguments[5], String.valueOf(commandLineParser.getNumberOfThreads()));
    }

    @Test
    public void parseMazesFolder() {
        String mazesFolder = System.getProperty("user.dir") + "\\test\\resources";
        String[] arguments = {"-mazes_folder", mazesFolder, "-players", "player", "-threads", "5"};
        CommandLineParser commandLineParser = new CommandLineParser();
        commandLineParser.validateAndParseArguments(arguments);
        List<String> mazesList = commandLineParser.parseMazesFolder();
        Assert.assertEquals("Mazes list size is not correct", 1, mazesList.size());
        Assert.assertTrue("Mazes list doesn't contains correct value", mazesList.contains(mazesFolder + "\\" + "maze.txt"));
    }

    @Test
    public void parsePlayersPackage() {
        String[] arguments = {"-mazes_folder", "c:\\temp", "-players", "player", "-threads", "5"};
        CommandLineParser commandLineParser = new CommandLineParser();
        commandLineParser.validateAndParseArguments(arguments);
        List<String> playersList = commandLineParser.parsePlayersPackage();
        Assert.assertEquals("Players list size is not correct", 2, playersList.size());
    }
}