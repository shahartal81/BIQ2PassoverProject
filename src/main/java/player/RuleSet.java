package player;

import enums.Move;

public class RuleSet {

    private final Move firstDirection;
    private final Move secondDirection;

    public RuleSet(Move firstDirection, Move secondDirection) {
        this.firstDirection = firstDirection;
        this.secondDirection = secondDirection;
    }

    public Move getFirstDirection() {
        return firstDirection;
    }

    public Move getSecondDirection() {
        return secondDirection;
    }
}
