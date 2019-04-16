package gamemanager;

import additionalclasses.MazeElement;
import additionalclasses.Position;
import enums.Move;
import player.Player;
import player.PlayerSimple;

public class GameManager {
    private Player player;
    private static final int MAX_STEPS = 20;
    private static final char PLAYER = MazeElement.PLAYER.getValue();
    private static final char END = MazeElement.END.getValue();
    private static final char WALL = MazeElement.WALL.getValue();
    private static final char PASS = MazeElement.PASS.getValue();

    private char[][]maze;
    private Position playerPosition;
    private Position endPosition;
    private int usedSteps = 0;
    private boolean isSolved = false;

    public GameManager(){
        this.player = new PlayerSimple();
        createMaze();
    }

    public char[][] getMaze() {
        return maze;
    }

    private void createMaze(){
        maze = new char[4][6];
        for (int i = 0; i < 4; i++){
            maze[0][i] = '#';
        }
        maze[0][4] = ' ';
        maze[0][5] = ' ';

        maze[1][0] = '#';
        maze[1][1] = ' ';
        maze[1][2] = '@';
        playerPosition = new Position(1,2);
        maze[1][3] = '#';
        maze[1][4] = ' ';
        maze[1][5] = ' ';

        maze[2][0] = '#';
        maze[2][1] = ' ';
        maze[2][2] = ' ';
        maze[2][3] = '#';
        maze[2][4] = ' ';
        maze[2][5] = ' ';

        maze[3][0] = ' ';
        maze[3][1] = ' ';
        maze[3][2] = '$';
        endPosition = new Position(3,2);
        maze[3][3] = '#';
        maze[3][4] = '#';

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
            char c = maze[next.getRow()][next.getColumn()];
            if (c == WALL){
                System.out.println("Wanted to move " + move + " but..."); //for console only
                player.hitWall();
            } else if (c == END) {
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
            movePlayer(move);
        }
    }
}
