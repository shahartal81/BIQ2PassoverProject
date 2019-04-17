package player;

import additionalclasses.Position;

public class PlayerFactory {

    public Player createPlayer(Position mazeDimensions){
        if (mazeDimensions.getColumn() <= 3 && mazeDimensions.getRow() <= 3) {
            return new PlayerSimple();
        } else if (mazeDimensions.getColumn() <= 5 && mazeDimensions.getRow() <= 5) {
            return new PlayerAdvanced();
        } else {
            return new PlayerVeryAdvanced();
        }
    }
}
