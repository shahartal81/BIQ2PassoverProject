package gamemanager;

import additionalclasses.*;
import enums.Move;
import player.Player;
import player.PlayerFactory;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GameManager {
    private Player player;
    private static final char PLAYER = MazeElement.PLAYER.getValue();
    private static final char END = MazeElement.END.getValue();
    private static final char WALL = MazeElement.WALL.getValue();
    private static final char PASS = MazeElement.PASS.getValue();

    private Maze maze;
    private Position playerPosition;
    private Position endPosition;
    private int usedSteps = 0;
    private boolean isSolved = false;
    private int bookmarkSeqNumber = 0;
    private Map<Position, Integer> bookmarksMap = new HashMap<>();

    private OutputFile outputFile;

    public GameManager(BufferedWriter outPutFile, PlayerFactory playerFactory){
        try {
            maze = InputFileParser.getMaze();
        } catch (Exception e) {
            e.printStackTrace();
        }
        outputFile = new OutputFile(outPutFile);
        player = playerFactory.createPlayer(new Position(maze.getRows(), maze.getColumns()), maze.getMaxSteps());

    }

    public GameManager(PlayerFactory playerFactory){
        try {
            maze = InputFileParser.getMaze();
        } catch (Exception e) {
            e.printStackTrace();
        }
        player = playerFactory.createPlayer(new Position(maze.getRows(), maze.getColumns()), maze.getMaxSteps());
        playerPosition = maze.getPlayerPosition();
        endPosition = maze.getEndPosition();

    }

    private void movePlayer(Move move){
        Position next = byMove(move);
        try {
            if (maze.getMazeMap()[next.getRow()][next.getColumn()] == WALL){
                System.out.println("Wanted to move " + move + " but..."); //for console only
                player.hitWall();
            } else if (next.equals(endPosition)) {
                System.out.println("Moved " + move);
                isSolved = true;
            } else {
                if (move.equals(Move.BOOKMARK)) {
                    bookmarkSeqNumber++;
                    bookmarksMap.put(playerPosition, bookmarkSeqNumber);
                } else {
                    if (bookmarksMap.containsKey(next)) {
                        player.hitBookmark(bookmarksMap.get(next));
                    }
                    System.out.println("Moved " + move);
                    changePosition(next);
                }
            }
            usedSteps++;
            System.out.println("Used steps: " + usedSteps); //for console only
            maze.printMaze();
        } catch (Exception e) {
            System.out.println("enums.Move is out of bounds");
        }
    }

    private Position byMove(Move move) {
        switch (move){
            case UP:
                return new Position(Math.floorMod(playerPosition.getRow() - 1, maze.getRows()), playerPosition.getColumn());
            case DOWN:
                return new Position(Math.floorMod(playerPosition.getRow() + 1, maze.getRows()), playerPosition.getColumn());
            case LEFT:
                return new Position(playerPosition.getRow(), Math.floorMod(playerPosition.getColumn() - 1, maze.getColumns()));
            case RIGHT:
                return new Position(playerPosition.getRow(), Math.floorMod(playerPosition.getColumn() + 1, maze.getColumns()));
            case BOOKMARK:
                return playerPosition;
        }
        throw new IllegalArgumentException("");
    }

    private void changePosition(Position next) {
        maze.getMazeMap()[playerPosition.getRow()][playerPosition.getColumn()] = PASS;
        maze.getMazeMap()[next.getRow()][next.getColumn()] = PLAYER;
        playerPosition = next;
    }

    public boolean playGame(){
        while (usedSteps < maze.getMaxSteps() && !isSolved) {
            Move move = player.move();
            movePlayer(move);
            outputFile.updateMovesMap(move);
        }
        if(isSolved){
            outputFile.setEndGame('!');
            System.out.println("You won!");
        } else {
            outputFile.setEndGame('X');
            System.out.println("Loser!");
            System.out.println("Used steps: " + usedSteps + " reached the limit of allowed steps: " + maze.getMaxSteps());
        }
        outputFile.printAllMoves();
        try {
            outputFile.exportToFile();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error while exporting to file");
        }
        return isSolved;
    }

}
