package player;

import enums.Move;

public interface Player {
    Move move();
    void hitWall();
    void hitBookmark(int seq);

    default String getPlayerName() {
        return getClass().getSimpleName();
    }
}
