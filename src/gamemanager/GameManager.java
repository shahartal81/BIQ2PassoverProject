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
    private static final int MAX_STEPS = 20;
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
        mazeDimensions = new Position(4,6);
        maze = new char[4][6];
        for (int i = 0; i < 4; i++){
            maze[0][i] = WALL;
        }
        maze[0][4] = PASS;
        maze[0][5] = PASS;

        maze[1][0] = WALL;
        maze[1][1] = PASS;
        maze[1][2] = PLAYER;
        playerPosition = new Position(1,2);
        maze[1][3] = WALL;
        maze[1][4] = PASS;
        maze[1][5] = PASS;

        maze[2][0] = WALL;
        maze[2][1] = PASS;
        maze[2][2] = PASS;
        maze[2][3] = WALL;
        maze[2][4] = PASS;
        maze[2][5] = PASS;

        maze[3][0] = PASS;
        maze[3][1] = PASS;
        maze[3][2] = END;
        endPosition = new Position(3,2);
        maze[3][3] = WALL;
        maze[3][4] = WALL;

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
        Position next = playerPosition.byMove(move);
        try {
            if (maze[next.getRow()][next.getColumn()] == WALL){
                System.out.println("Wanted to move " + move + " but..."); //for console only
                player.hitWall();
            } else if (next.equals(endPosition)) {
                System.out.println("You won!");
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
    }
}
