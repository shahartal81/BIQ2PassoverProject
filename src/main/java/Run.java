package main.java;

import main.java.filehandling.InputFileParser;
import main.java.additionalclasses.Maze;
import main.java.gamemanager.GameLoader;
import main.java.gamemanager.GameManager;
import main.java.player.PlayerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Run {
    public static void main(String[] args) {
        if (args.length == 0){
            System.out.println("Missing maze file argument in command line");
        }
        if (args.length < 2) {
            System.out.println("Missing output file argument in command line");
        } else {
            GameLoader gl = new GameLoader();
            gl.validateAndStartGame(args);
        }
    }
}

