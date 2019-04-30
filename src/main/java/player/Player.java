package main.java.player;

import main.java.enums.Move;

public interface Player {
    Move move();
    void hitWall();
    void hitBookmark(int seq);
}
