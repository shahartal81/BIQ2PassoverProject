package player;

import additionalclasses.Position;

public class PlayerFactory {

    public Player createPlayer(Position mazeDimensions, Integer maxSteps){
        if (mazeDimensions.getColumn() <= 3 && mazeDimensions.getRow() <= 3) {
            System.out.println("Using PlayerSimple");
            return new PlayerSimple();
        } else if (mazeDimensions.getColumn() <= 5 && mazeDimensions.getRow() <= 5) {
            System.out.println("Using PlayerAdvanced");
            return new PlayerAdvanced();
        } else if ((mazeDimensions.getColumn() * mazeDimensions.getRow()) * 3 >= maxSteps){
            System.out.println("Using PlayerVeryAdvanced");
            return new PlayerVeryAdvanced();
        } else {
            System.out.println("Using PlayerBookmarkEachStep");
            return new PlayerBookmarkEachStep();
        }
    }
}
