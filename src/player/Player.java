package player;

import enums.Move;

public interface Player {
    Move move();
    void hitWall();
}
