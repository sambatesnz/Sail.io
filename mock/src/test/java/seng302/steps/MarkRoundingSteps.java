package seng302.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import seng302.DataGeneration.IServerData;
import seng302.DataGeneration.RaceManager;
import seng302.Race;
import seng302.RaceObjects.Boat;

public class MarkRoundingSteps {
    Race race;
    Boat boat;

    @Given("^the race is already running$")
    public void the_race_is_already_running() throws Throwable {
        IServerData mockData = new RaceManager(); //Default race
        race = mockData.getRace();
        boat = race.addBoat();
    }

    @Given("^a boat is passing mark (\\d+)$")
    public void a_boat_is_passing_mark(int markNumber) throws Throwable {
        for (int i = 0; i < markNumber; i++) {
            boat.passMark();
        }
    }

    @When("^the the boat passes that mark$")
    public void the_the_boat_passes_that_mark() throws Throwable {
        boat.passMark();
    }

    @Then("^the boats next mark will be set to (\\d+)$")
    public void the_boats_next_mark_will_be_set_to(int n) throws Throwable {
        Assert.assertEquals(n, boat.getTargetMarkIndex());
    }
}
