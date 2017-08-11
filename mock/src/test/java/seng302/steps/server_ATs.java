package seng302.steps;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * Acceptance Testing for the Server Refactor to allow multiple connections to the same race instance.
 */
public class server_ATs {
    @When("^the race starts$")
    public void theRaceStarts() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Given("^that (\\d+) people have connected to a race$")
    public void thatPeopleHaveConnectedToARace(int arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^each person can control a unique boat$")
    public void eachPersonCanControlAUniqueBoat() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}
