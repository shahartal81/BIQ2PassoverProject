package player;


import additionalclasses.Position;

public class PlayerFactory {

    public Player createPlayer(Position mazeDimensions, Integer maxSteps){
        if ((mazeDimensions.getColumn() * mazeDimensions.getRow()) * 3 < maxSteps){
            System.out.println("Using PlayerBookmarkEachStep");
            return new PlayerBookmarkEachStep();
        } else {
            System.out.println("Using PlayerSmart");
            return new PlayerSmart();
        }
    }
}
