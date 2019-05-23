import filehandling.MazeDefinitionParser;
import filehandling.MazeParser;
import gamemanager.GameLoader;

public class MazeGame {
    public static void main(String[] args) {
        if (args.length == 0){
            System.out.println("Missing maze file argument in command line");
        }
        if (args.length < 2) {
            System.out.println("Missing output file argument in command line");
        } else {
            GameLoader gameLoader = new GameLoader();
            MazeDefinitionParser inputFileParser = new MazeParser();
            gameLoader.validateAndStartGame(args[0], args[1],inputFileParser);
        }
    }
}

