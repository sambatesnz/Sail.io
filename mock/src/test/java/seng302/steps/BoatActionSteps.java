package seng302.steps;


import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import javafx.scene.paint.Color;
import seng302.Boat;

public class BoatActionSteps {
    @Given("^I am controlling a boat$")
    public void i_am_controlling_a_boat() throws Throwable {
        Boat boat = new Boat("Boaty mcBoatFace", 10, Color.ALICEBLUE, "BMB", 0);
    }

    @When("^I Press the \"([^\"]*)\" control$")
    public void i_Press_the_control(String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^The boat's sails should be pulled in$")
    public void the_boat_s_sails_should_be_pulled_in() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}
