package gamemanager;

import additionalclasses.Maze;
import additionalclasses.MazeElement;
import additionalclasses.Position;
import enums.Move;
import filehandling.OutputFile;
import player.Player;
import player.PlayerFactory;

import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
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

    public GameManager(PlayerFactory playerFactory, Maze maze){
        this.maze = maze;
        player = playerFactory.createPlayer(new Position(maze.getRows(), maze.getColumns()), maze.getMaxSteps());
        playerPosition = maze.getPlayerPosition();
        endPosition = maze.getEndPosition();

    }

    public GameManager(String playerPackage, Maze maze) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        this.maze = maze;
        Class<?> clazz = Class.forName(playerPackage);
        Constructor<?> ctor = clazz.getConstructor();
        player = (Player) ctor.newInstance();
        playerPosition = maze.getPlayerPosition();
        endPosition = maze.getEndPosition();

    }

    void movePlayer(Move move){
        Position next = byMove(move);
        try {
            if (maze.getMazeMap()[next.getRow()][next.getColumn()] == WALL){
                player.hitWall();
            } else if (next.equals(endPosition)) {
                isSolved = true;
            } else {
                if (move.equals(Move.BOOKMARK)) {
                    bookmarkSeqNumber++;
                    bookmarksMap.put(playerPosition, bookmarkSeqNumber);
                } else {
                    changePosition(next);
                    if (bookmarksMap.containsKey(playerPosition)) {
                        player.hitBookmark(bookmarksMap.get(playerPosition));
                    }
                }
            }
            usedSteps++;
        } catch (Exception e) {
            throw new IllegalArgumentException("Move is out of bounds");
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
            System.out.println("Succeeded in " + usedSteps + " steps");
        } else {
            outputFile.setEndGame('X');
            System.out.println("Failed to solve maze in " + maze.getMaxSteps() + " steps");
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

    public void createOutPutFile(BufferedWriter outPutFile) {
        outputFile = new OutputFile(outPutFile);
    }


    public Position getPlayerPosition() {
        return playerPosition;
    }

    public int getUsedSteps() { return usedSteps; }

    public int getBookmarkSeqNumber() { return bookmarkSeqNumber; }

    public Map<Position, Integer> getBookmarksMap() { return bookmarksMap; }
}
