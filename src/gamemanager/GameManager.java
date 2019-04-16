package gamemanager;

import additionalclasses.MazeElement;
import additionalclasses.Position;
import enums.Move;
import player.Player;
import player.PlayerAdvanced;
import player.PlayerSimple;
import player.PlayerVeryAdvanced;

import java.util.HashMap;
import java.util.Map;

public class GameManager {
    private Player player;
    private static final int MAX_STEPS = 30;
    private static final int NUMBER_OF_ROWS = 4;
    private static final int NUMBER_OF_COLUMNS = 6;
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

    public GameManager(){
        createMaze();
        if (mazeDimensions.getColumn() <= 3 && mazeDimensions.getRow() <= 3) {
            this.player = new PlayerSimple();
        } else if (mazeDimensions.getColumn() <= 5 && mazeDimensions.getRow() <= 5) {
            this.player = new PlayerAdvanced();
        } else {
            this.player = new PlayerVeryAdvanced();
        }

    }

    private void createMaze(){
        mazeDimensions = new Position(NUMBER_OF_ROWS,NUMBER_OF_COLUMNS);
        maze = new char[NUMBER_OF_ROWS][NUMBER_OF_COLUMNS];
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
                System.out.println("Moved " + move);
                changePosition(next);
            }
            usedSteps ++;
            System.out.println("Used steps: " + usedSteps); //for console only
            printMaze(); //for console only
        } catch (Exception e) {
            System.out.println("enums.Move is out of bounds");
        }
    }

    private Position byMove(Move move) {
        switch (move){
            case UP:
                return new Position(Math.floorMod(playerPosition.getRow() - 1, NUMBER_OF_ROWS), playerPosition.getColumn());
            case DOWN:
                return new Position(Math.floorMod(playerPosition.getRow() + 1,NUMBER_OF_ROWS ), playerPosition.getColumn());
            case LEFT:
                return new Position(playerPosition.getRow(), Math.floorMod(playerPosition.getColumn() - 1, NUMBER_OF_COLUMNS));
            case RIGHT:
                return new Position(playerPosition.getRow(), Math.floorMod(playerPosition.getColumn() + 1, NUMBER_OF_COLUMNS));
        }

        throw new IllegalArgumentException("");

    }

    private void changePosition(Position next) {
        maze[playerPosition.getRow()][playerPosition.getColumn()] = PASS;
        maze[next.getRow()][next.getColumn()] = PLAYER;
        playerPosition = next;
    }

    public void playGame(){
        while (usedSteps < MAX_STEPS && !isSolved) {
            Move move = player.move();
            if (move.equals(Move.BOOKMARK)) {
                bookmarkSeqNumber++;
                bookmarksMap.put(playerPosition, bookmarkSeqNumber);
                player.hitBookmark(bookmarkSeqNumber);
            } else {
                movePlayer(move);
            }
        }
        if(isSolved){
            System.out.println("You won!");
        } else {
            System.out.println("Loser!");
            System.out.println("Used steps: " + usedSteps + " reached the limit of allowed steps: " + MAX_STEPS);
        }
    }
}
