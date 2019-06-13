package gamemanager;

import player.Player;

public class GameResult {

    private Player player;
    private boolean isSolved;
    private int usedSteps;

    public GameResult(Player player, boolean isSolved, int usedSteps) {
        this.player = player;
        this.isSolved = isSolved;
        this.usedSteps = usedSteps;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isSolved() {
        return isSolved;
    }

    public int getUsedSteps() {
        return usedSteps;
    }
}
