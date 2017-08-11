package seng302.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import seng302.DataGeneration.RaceManager;
import seng302.Race;
import seng302.Server.Delegator;
import seng302.UserInputController.BoatAction;

import java.util.concurrent.TimeUnit;

import static junit.framework.TestCase.assertEquals;

public class BoatTurningSteps {
    Race race;
    Delegator delegator = new Delegator(race);
    int messageCommand;

    @Given("^the race is running$")
    public void the_race_is_running() throws Throwable {
        RaceManager mockData = new RaceManager(); //Default race
        race = mockData.getRace();
        delegator = new Delegator(race);
    }

    @Given("^a boatAction packet has been received with a value of (\\d+)$")
    public void a_boatAction_packet_has_been_received_with_a_value_of(int command) throws Throwable {
        messageCommand = command;
    }

    @Given("^the boats current heading is (\\d+) degrees$")
    public void the_boats_current_heading_is_degrees(int boatHeading) throws Throwable {
        race.getBoatByID(103).setHeading(boatHeading);
    }

    @Given("^the current wind direction is (\\d+)$")
    public void the_current_wind_direction_is(int windDirection) throws Throwable {
        race.setWindHeading((short)windDirection);
    }

    @When("^the received boatAction packet is processed$")
    public void the_received_boatAction_packet_is_processed() throws Throwable {
        delegator.processCommand(BoatAction.getAction(messageCommand), 101); //I love hard coding in boats
    }

    @Then("^the boats current heading is altered to (\\d+) degrees$")
    public void the_boats_current_heading_is_altered_to_degrees(int finalHeading) throws Throwable {
        assertEquals(finalHeading, (int)race.getBoatByID(103).getHeading() );
    }

    @Then("^the boats final heading is altered to (\\d+) degrees$")
    public void the_boats_final_heading_is_altered_to_degrees(int finalHeading) throws Throwable {
        TimeUnit.SECONDS.sleep(5); //Need to wait for tack/gybe to finish its cycle
        assertEquals(finalHeading, (int)race.getBoatByID(103).getHeading(), 1); //Margin of error within +- ~10 degress
    }
}
