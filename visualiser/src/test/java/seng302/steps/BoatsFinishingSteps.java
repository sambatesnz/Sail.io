package seng302.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import seng302.BoatGenerator;
import seng302.PacketGeneration.BinaryMessage;
import seng302.PacketGeneration.YachtEventGeneration.YachtEventMessage;
import seng302.PacketGeneration.YachtEventGeneration.YachtIncidentEvent;
import seng302.RaceObjects.Boat;
import seng302.RaceObjects.Race;

import java.util.*;

import static org.junit.Assert.assertEquals;

/**
 * Acceptance tests for when boats finish the race.
 */
public class BoatsFinishingSteps {
    private Race race;
    private BoatGenerator generator;
    Map<Integer, Boat> boatsList;
    List<Integer> boatsIdList;

    @Given("^the race is already running$")
    public void the_race_is_already_running() throws Throwable {
        race = new Race();
        generator = new BoatGenerator();
        boatsList = new HashMap<>();
        boatsIdList = new ArrayList<>();
    }

    @Given("^there are (\\d+) boats in the race$")
    public void there_are_boats_in_the_race(int numberOfBoats) throws Throwable {
        for (int i = 0; i < numberOfBoats; i++) {
            Boat boat = generator.generateBoat();
            boatsList.put(boat.getSourceId(), boat);
            boatsIdList.add(boat.getSourceId());
        }
        race.setParticipants(boatsIdList);
        race.setBoats(boatsList);
    }

    @Given("^(\\d+) boats have finished the race$")
    public void boats_have_finished_the_race(int initialFinishedBoats) throws Throwable {
        for (int i = 0; i < initialFinishedBoats; i++) {
            BinaryMessage message = new YachtEventMessage(boatsIdList.get(i), YachtIncidentEvent.FINISHED);
            seng302.Client.Messages.YachtEventMessage yachtEventMessage = new seng302.Client.Messages.YachtEventMessage(message.getBody());
            yachtEventMessage.updateRace(race);
        }
        assertEquals(initialFinishedBoats, race.getFinishedBoats().size());
    }

    @When("^(\\d+) more boats finish the race after the initial (\\d+) boats$")
    public void more_boats_finish_the_race_after_the_initial_boats(int finishingBoats, int initialFinishedBoats) throws Throwable {
        for (int i = initialFinishedBoats; i < finishingBoats + initialFinishedBoats; i++) {
            BinaryMessage message = new YachtEventMessage(boatsIdList.get(i), YachtIncidentEvent.FINISHED);
            seng302.Client.Messages.YachtEventMessage yachtEventMessage = new seng302.Client.Messages.YachtEventMessage(message.getBody());
            yachtEventMessage.updateRace(race);
        }
    }

    @Then("^there will be (\\d+) boats listed as having finished$")
    public void there_will_be_boats_listed_as_having_finished(int totalFinishedBoats) throws Throwable {
        assertEquals(totalFinishedBoats, race.getFinishedBoats().size());
    }
}
