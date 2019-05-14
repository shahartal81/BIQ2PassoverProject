package player;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import enums.Move;
import org.junit.Assert;

public class PlayerIntelligentStepdefs {

    private PlayerIntelligent player = new PlayerIntelligent(3);

    private Move move;

    @Given("we create PlayerIntelligent")
    public void weCreatePlayerIntelligent() {
        player = new PlayerIntelligent(3);
    }

    @When("we do a move")
    public void weDoAMove() {
        move = player.move();
    }

    @Then("we get valid move")
    public void weGetValidMove() {
        Assert.assertTrue("move " + move + " is not valid", move.equals(Move.DOWN) || move.equals(Move.UP) || move.equals(Move.LEFT) || move.equals(Move.RIGHT));
    }

    @And("move is saved as last move")
    public void moveIsSavedAsLastMove() {
        Assert.assertEquals("last move should be " + move, move, player.getLastMove());
    }

    @And("bookmark sequence is {int}")
    public void bookmarkSequenceIs(int sequenceNumber) {
        Assert.assertEquals("bookmark sequence should be " + sequenceNumber, sequenceNumber, player.getSeqNumber());
    }

    @And("we hit wall")
    public void weHitWall() {
        player.hitWall();
    }

    @Then("we create bookmark with sequence {int}")
    public void weCreateBookmarkWithSequence(int sequenceNumber) {
        Assert.assertEquals("bookmark sequence should be " + sequenceNumber, sequenceNumber, player.getSeqNumber());
        Assert.assertTrue("bookmark " + sequenceNumber + " should be in map", player.getPositionToBookmarksMap().containsKey(sequenceNumber));
    }

    @And("bookmark {int} contains last move")
    public void bookmarkContainsLastMove(int sequenceNumber) {
        Assert.assertTrue("bookmark " + sequenceNumber + " should contain " + move,
                player.getPositionToBookmarksMap().get(sequenceNumber).getMovesPerformed().contains(move));
    }


}