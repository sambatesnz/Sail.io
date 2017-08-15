package seng302.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import seng302.ClientConnections.Client;
import seng302.DataGeneration.IServerData;
import seng302.DataGeneration.RaceManager;
import seng302.PacketGeneration.RaceStatus;
import seng302.RaceObjects.Boat;
import seng302.Server.ClientConnection;
import seng302.Server.Server;
import seng302.steps.Helper.TestClient;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

/**
 * Tests for multiple connnections
 */
public class MultipleConnectionSteps {

    IServerData race;
    Server server;
    int numberOfConnections;

    @Given("^that (\\d+) people have connected to a race$")
    public void that_people_have_connected_to_a_race(int numConnections) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        numberOfConnections = numConnections;
        race = new RaceManager();
        new Thread(() -> {
            try {
                this.server = new Server(4941, race);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        Thread.sleep(2000); //Wait for server to start

        for (int i = 0; i < numConnections; i++) {
            new Thread(() -> {
                Client testClient = new TestClient("http://localhost", 4941);
                try {
                    testClient.connect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
            Thread.sleep(2000);
        }
    }

    @When("^the race starts$")
    public void the_race_starts() throws Throwable {
        race.getRace().setRaceStatus(RaceStatus.STARTED);
    }

    @Then("^Each player should be given a unique source id for a boat$")
    public void each_player_should_be_given_a_unique_source_id_for_a_boat() throws Throwable {
        Thread.sleep(5000);
        Set<Integer> set = new HashSet<>();
        for (Boat boat: race.getRace().getBoats()){
            set.add(boat.getSourceId());
        }

        assertEquals(numberOfConnections , set.size());
    }

}
