package main.java;

import main.java.filehandling.InputFileParser;
import main.java.filehandling.FileParser;
import main.java.gamemanager.GameLoader;

public class Run {
    public static void main(String[] args) {
        if (args.length == 0){
            System.out.println("Missing maze file argument in command line");
        }
        if (args.length < 2) {
            System.out.println("Missing output file argument in command line");
        } else {
            GameLoader gameLoader = new GameLoader();
            FileParser inputFileParser = new InputFileParser();
            gameLoader.validateAndStartGame(args,inputFileParser);
        }
    }
}

