package player;


import additionalclasses.Position;

public class PlayerFactory {

    public Player createPlayer(Position mazeDimensions, Integer maxSteps){
        if ((mazeDimensions.getColumn() * mazeDimensions.getRow()) * 3 < maxSteps){
            return new PlayerBookmarkEachStep();
        } else if (mazeDimensions.getColumn() < 3 && mazeDimensions.getRow() < 3){
            return new PlayerDistantGoing();
        } else {
            return new PlayerRuleSet();
        }
    }
}
