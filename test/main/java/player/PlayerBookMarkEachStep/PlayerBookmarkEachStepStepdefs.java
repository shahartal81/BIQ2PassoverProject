package player.PlayerBookMarkEachStep;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import enums.Move;
import org.junit.Assert;
import player.PlayerBookmarkEachStep;

public class PlayerBookmarkEachStepStepdefs {

    private PlayerBookmarkEachStep player = new PlayerBookmarkEachStep();

    private Move move;

    @Given("we create PlayerBookmarkEachStep")
    public void weCreatePlayerBookmarkEachStep() {
        player = new PlayerBookmarkEachStep();
    }

    @When("we do a move")
    public void weDoAMove() {
        move = player.move();
    }

    @When("we do another move")
    public void weDoAnotherMove() {
        player.move();
    }

    @Then("move is saved as last move")
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

    @And("bookmark {int} contains last move")
    public void bookmarkContainsLastMove(int sequenceNumber) {
        Assert.assertTrue("bookmark " + sequenceNumber + " should contain last move as value",
                player.getBookmarks().get(sequenceNumber).getMovesPerformed().contains(move));
    }


    @Then("we mark next turn as Bookmark")
    public void weMarkNextTurnAsBookmark() {
        Assert.assertTrue("next turn bookmark flag should be true", player.isUseBookmark());
    }

    @And("last move should be Bookmark")
    public void lastMoveShouldBeBookmark() {
        Assert.assertEquals("move should be bookmark", Move.BOOKMARK, player.getLastMove());
    }

    @And("bookmark {int} is saved")
    public void bookmarkIsSaved(int sequenceNumber) {
        Assert.assertTrue("bookmark " + sequenceNumber + " should be in map",
                player.getBookmarks().containsKey(sequenceNumber));
    }

    @And("we hit bookmark {int}")
    public void weHitBookmarkSeq(int sequenceNumber) {
        player.hitBookmark(sequenceNumber);
    }
}