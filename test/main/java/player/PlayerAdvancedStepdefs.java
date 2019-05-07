package player;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import main.java.player.PlayerAdvanced;
import main.java.enums.Move;
import org.junit.Assert;

public class PlayerAdvancedStepdefs {

    private PlayerAdvanced playerAdvanced;
    private Move move;

    @Given("we create PlayerAdvanced")
    public void weCreatePlayerAdvanced() {
        playerAdvanced = new PlayerAdvanced();
    }

    @When("we do a move")
    public void weDoAMove() {
        move = playerAdvanced.move();
    }

    @Then("we get valid move")
    public void weGetValidMove() {
        Assert.assertTrue("move is not valid", move.equals(Move.DOWN) || move.equals(Move.UP) || move.equals(Move.LEFT) || move.equals(Move.RIGHT));
    }

    @And("move is saved as last move")
    public void moveIsSavedAsLastMove() {
        Assert.assertEquals("move is not saved as last move", move, playerAdvanced.getLastMove());
    }

    @And("bookmark sequence is {int}")
    public void bookmarkSequenceIs(int sequenceNumber) {
        Assert.assertEquals("bookmark sequence should be 0", sequenceNumber, playerAdvanced.getSeqNumber());
    }
}