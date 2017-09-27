package seng302.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import seng302.ClientConnections.Client;
import seng302.DataGeneration.IServerData;
import seng302.Modes.RaceManager;
import seng302.PacketGeneration.RaceStatus;
import seng302.RaceObjects.GenericBoat;
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
    int port;
    int numberOfConnections;


    private void makeConnections(int numberOfConnections, int portNumber) throws InterruptedException {
        for (int i = 0; i < numberOfConnections; i++) {
            new Thread(() -> {
                Client testClient = new TestClient("http://localhost", portNumber);
                try {
                    testClient.connect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
            Thread.sleep(500); // Give it some time
        }
        Thread.sleep(500);
    }

    private void makeServer(int portNumber, IServerData race){
        new Thread(() -> {
            try {
                this.server = new Server(portNumber, race);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }


    @Given("^that (\\d+) people have connected to a race$")
    public void that_people_have_connected_to_a_race(int numConnections) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        numberOfConnections = numConnections;
        race = new RaceManager();
        this.port = 4940;
        makeServer(this.port, race);
       makeConnections(numConnections,this.port);
    }

    @When("^the race starts$")
    public void the_race_starts() throws Throwable {
        race.getRace().setRaceStatus(RaceStatus.STARTED);
    }

    @Then("^Each player should be given a unique source id for a boat$")
    public void each_player_should_be_given_a_unique_source_id_for_a_boat() throws Throwable {
        System.out.println(this.port);
        Thread.sleep(3000);
        Set<Integer> set = new HashSet<>();
        for (GenericBoat boat: race.getRace().getBoats()){
            set.add(boat.getSourceId());
        }

        assertEquals(numberOfConnections , set.size());
    }


    @Given("^The maximum number of people in a race is (\\d+)$")
    public void the_maximum_number_of_people_in_a_race_is(int maxNumberOfPeople) throws Throwable {
        numberOfConnections = maxNumberOfPeople;
        race = new RaceManager();
        this.port = 4942;
        makeServer(this.port, race);
        for (int i = 0; i < maxNumberOfPeople; i++) {
            race.getRace().addBoat(0);
        }
    }

    @When("^(\\d+) person tries to participate in the race$")
    public void person_tries_to_participate_in_the_race(int numPeopleWantingToConnect) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        makeConnections(numPeopleWantingToConnect, this.port);

    }

    @Then("^There should still only be (\\d+) people in the race$")
    public void there_should_still_only_be_people_in_the_race(int maxNumberOfPeople) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        int boatsInRace = race.getRace().getBoats().size();
        assertEquals(maxNumberOfPeople, boatsInRace);
    }

    @Given("^There is currently (\\d+) people connected$")
    public void there_is_currently_people_connected(int numberOfConnections) throws Throwable {
        this.race = new RaceManager();
        System.out.println(this.race.getRace().getBoats());
        this.port = 4943;
        makeServer(this.port, this.race);
        Thread.sleep(1000);
        makeConnections(numberOfConnections, this.port);
        Thread.sleep(1000);
        assertEquals(this.race.getRace().getBoats().size(), numberOfConnections);
    }

    @Given("^That race has less than (\\d+) minute until it starts$")
    public void that_race_has_less_than_minute_until_it_starts(int arg1) throws Throwable {
        Thread.sleep(1000);
        assertEquals(this.race.getRace().getBoats().size(), 3);
        race.getRace().setRaceStatus(RaceStatus.PRESTART);
        assertEquals(this.race.getRace().getBoats().size(), 3);
    }

    @When("^there is (\\d+) person trying to participate$")
    public void there_is_person_trying_to_participate(int numberOfConnections) throws Throwable {
        System.out.println("\n  ----> " + race.getRace().getRaceStatus());
        System.out.println(this.port);
        assertEquals(this.race.getRace().getBoats().size(), 3);
        Thread.sleep(3000);
        makeConnections(numberOfConnections, this.port);

    }

    @Then("^the race should only contain (\\d+) boats$")
    public void the_race_should_only_contain_boats(int expectedNumberOfBoats) throws Throwable {
        int actualNumberOfBoats = race.getRace().getBoats().size();
        Thread.sleep(5000);
        System.out.println(race.getRace().getBoats());
        assertEquals(expectedNumberOfBoats, actualNumberOfBoats);
    }

}
