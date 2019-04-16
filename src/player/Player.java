package player;

import enums.Move;

public abstract class Player {
    public abstract Move move();
    public abstract void hitWall();
}
