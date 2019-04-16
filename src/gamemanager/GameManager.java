package gamemanager;

import additionalclasses.Position;
import enums.Move;
import player.Player;
import player.PlayerSimple;

public class GameManager {
    private Player p;
    private static final int MAX_STEPS = 20;
    private char[][]maze;
    private Position playerPosition;
    private int usedSteps = 0;
    boolean isSolved = false;

    public GameManager(PlayerSimple p){
        this.p = p;
        maze = createMaze();
        playerPosition = getPlayerPosition();
    }

    public char[][] getMaze() {
        return maze;
    }

    private Position getPlayerPosition(){
        for (int row = 0; row < maze.length; row++){
            for (int col = 0; col < maze[row].length; col++) {
                if (maze[row][col] == '@'){
                    return new Position(row,col);
                }
            }
        }
        return null;
    }

    private char[][] createMaze(){
        char[][]maze = new char[4][6];
        for (int i = 0; i < 4; i++){
            maze[0][i] = '#';
        }
        maze[0][4] = ' ';
        maze[0][5] = ' ';

        maze[1][0] = '#';
        maze[1][1] = ' ';
        maze[1][2] = '@';
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
        maze[3][3] = '#';
        maze[3][4] = '#';

        return maze;
    }

    public void printMaze(char[][] maze){
        for (int row = 0; row < maze.length; row++){
            for (int col = 0; col < maze[row].length; col++) {
                System.out.print(maze[row][col] + " ");
            }

            System.out.println();
        }
        System.out.println("----------------------------------");
    }

    private void movePlayer(Move move, int row, int col){
        try {
            char c = maze[row][col];
            if (c == '#'){
                System.out.println("Wanted to move " + move + " but..."); //for console only
                p.hitWall();
            } else if (c == '$') {
                System.out.println("You won!");
                isSolved = true;
            } else {
                System.out.println("Moved " + move);
                changePositionInMaze(move); //for console only
                playerPosition.setRow(row);
                playerPosition.setColumn(col);
            }
            usedSteps ++;
            System.out.println("Used steps: " + usedSteps); //for console only
            printMaze(maze); //for console only
        } catch (Exception e) {
            System.out.println("enums.Move is out of bounds");
        }
    }

    private void changePositionInMaze(Move move) {
        int row = playerPosition.getRow();
        int col = playerPosition.getColumn();

        maze[row][col] = ' ';

        if (move.equals(Move.UP)){
            maze[row - 1][col] = '@';
        } else if (move.equals(Move.DOWN)){
            maze[row + 1][col] = '@';
        } else if (move.equals(Move.LEFT)){
            maze[row][col - 1] = '@';
        } else {
            maze[row][col + 1] = '@';
        }
    }

    public void playGame(){
        while (usedSteps < MAX_STEPS && !isSolved) {
            Move move = p.move();
            int row = playerPosition.getRow();
            int col = playerPosition.getColumn();

            switch (move){
                case UP:
                    movePlayer(Move.UP, row - 1, col);
                    break;
                case DOWN:
                    movePlayer(Move.DOWN, row + 1, col);
                    break;
                case LEFT:
                    movePlayer(Move.LEFT, row, col - 1);
                    break;
                case RIGHT:
                    movePlayer(Move.RIGHT, row , col + 1);
                    break;
            }
        }
    }


}
