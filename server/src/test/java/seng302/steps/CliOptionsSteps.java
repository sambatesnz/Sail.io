package seng302.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import seng302.RaceMode;
import seng302.Server.Server;

import static junit.framework.TestCase.assertEquals;


/**
 * CLI option steps
 */
public class CliOptionsSteps {

    String[] args;
    Server server;

    @Given("^I want to start the race in \"([^\"]*)\"$")
    public void i_want_to_start_the_race_in(String mode) throws Throwable {
        args = new String[1];
        args[0] = mode;
    }

    @When("^The server starts on (\\d+)$")
    public void the_server_starts_on(int port) throws Throwable {
        server = new Server(port, args);
    }

    @Then("^It should be running in \"([^\"]*)\"$")
    public void it_should_be_running_in(String expectedMode) throws Throwable {
        RaceMode mode = RaceMode.getRaceMode(expectedMode);
        RaceMode actualMode =  server.getRaceMode();
        assertEquals(mode, actualMode);
    }


}
