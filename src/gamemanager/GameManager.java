package gamemanager;

import additionalclasses.MazeElement;
import additionalclasses.OutputFile;
import additionalclasses.Position;
import enums.Move;
import player.Player;
import player.PlayerAdvanced;
import player.PlayerBookmarkEachStep;
import player.PlayerSimple;
import player.PlayerVeryAdvanced;
import org.mockito.cglib.proxy.Factory;
import player.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GameManager {
    private Player player;
    private static final int MAX_STEPS = 30;
    private int numberOfRows = 4;
    private int numberOfColumns = 6;
    private static final char PLAYER = MazeElement.PLAYER.getValue();
    private static final char END = MazeElement.END.getValue();
    private static final char WALL = MazeElement.WALL.getValue();
    private static final char PASS = MazeElement.PASS.getValue();

    private Position mazeDimensions;
    private char[][] maze;
    private Position playerPosition;
    private Position endPosition;
    private int usedSteps = 0;
    private boolean isSolved = false;
    private int bookmarkSeqNumber = 0;
    private Map<Position, Integer> bookmarksMap = new HashMap<>();

    private OutputFile outputFile;

    public GameManager(File outPutFile){
    public GameManager(PlayerFactory playerFactory){
        createMaze();
        if (mazeDimensions.getColumn() <= 3 && mazeDimensions.getRow() <= 3) {
            System.out.println("Using PlayerSimple");
            this.player = new PlayerSimple();
        } else if (mazeDimensions.getColumn() <= 5 && mazeDimensions.getRow() <= 5) {
            System.out.println("Using PlayerAdvanced");
            this.player = new PlayerAdvanced();
        } else if ((mazeDimensions.getColumn() * mazeDimensions.getRow()) * 3 >= MAX_STEPS){
            System.out.println("Using PlayerVeryAdvanced");
            this.player = new PlayerVeryAdvanced();
        } else {
            System.out.println("Using PlayerBookmarkEachStep");
            this.player = new PlayerBookmarkEachStep();
        }
        outputFile = new OutputFile(outPutFile);
        player = playerFactory.createPlayer(mazeDimensions);

    }

    private void createMaze(){
        mazeDimensions = new Position(numberOfRows, numberOfColumns);
        maze = new char[numberOfRows][numberOfColumns];
        for (int i = 0; i < 4; i++){
            maze[0][i] = WALL;
        }
        maze[0][4] = PASS;
        maze[0][5] = PASS;

        maze[1][0] = PASS;
        maze[1][1] = PASS;
        maze[1][2] = PLAYER;
        playerPosition = new Position(1,2);
        maze[1][3] = WALL;
        maze[1][4] = END;
        endPosition = new Position(1,4);
        maze[1][5] = PASS;

        maze[2][0] = WALL;
        maze[2][1] = PASS;
        maze[2][2] = PASS;
        maze[2][3] = WALL;
        maze[2][4] = PASS;
        maze[2][5] = PASS;

        maze[3][0] = PASS;
        maze[3][1] = PASS;
        maze[3][2] = PASS;
        maze[3][3] = WALL;
        maze[3][4] = PASS;

    }

    public void printMaze(){
        for (int row = 0; row < maze.length; row++){
            for (int col = 0; col < maze[row].length; col++) {
                System.out.print(maze[row][col] + " ");
            }

            System.out.println();
        }
        System.out.println("----------------------------------");
    }

    private void movePlayer(Move move){
        Position next = byMove(move);
        try {
            if (maze[next.getRow()][next.getColumn()] == WALL){
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
            printMaze(); //for console only
        } catch (Exception e) {
            System.out.println("enums.Move is out of bounds");
        }
    }

    private Position byMove(Move move) {
        switch (move){
            case UP:
                return new Position(Math.floorMod(playerPosition.getRow() - 1, numberOfRows), playerPosition.getColumn());
            case DOWN:
                return new Position(Math.floorMod(playerPosition.getRow() + 1, numberOfRows), playerPosition.getColumn());
            case LEFT:
                return new Position(playerPosition.getRow(), Math.floorMod(playerPosition.getColumn() - 1, numberOfColumns));
            case RIGHT:
                return new Position(playerPosition.getRow(), Math.floorMod(playerPosition.getColumn() + 1, numberOfColumns));
            case BOOKMARK:
                return playerPosition;
        }
        throw new IllegalArgumentException("");
    }

    private void changePosition(Position next) {
        maze[playerPosition.getRow()][playerPosition.getColumn()] = PASS;
        maze[next.getRow()][next.getColumn()] = PLAYER;
        playerPosition = next;
    }

    public boolean playGame(){
        while (usedSteps < MAX_STEPS && !isSolved) {
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
            System.out.println("Used steps: " + usedSteps + " reached the limit of allowed steps: " + MAX_STEPS);
        }
        return isSolved;
    }

    public void setNumberOfRows(int numberOfRows) {
        this.numberOfRows = numberOfRows;
    }

    public void setNumberOfColumns(int numberOfColumns) {
        this.numberOfColumns = numberOfColumns;
        outputFile.printAllMoves();
        try {
            outputFile.exportToFile();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error while exporting to file");
        }
    }
}
