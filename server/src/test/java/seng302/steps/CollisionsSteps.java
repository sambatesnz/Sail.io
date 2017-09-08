package seng302.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import seng302.BoatGenerator;
import seng302.DataGeneration.RaceManager;
import seng302.Race;
import seng302.RaceObjects.Boat;
import seng302.RaceObjects.BoatCollision;
import seng302.Server.Delegator;
import seng302.UserInputController.BoatAction;

import java.util.concurrent.TimeUnit;

import static junit.framework.TestCase.assertEquals;

public class CollisionsSteps {
    private Boat boat1;
    private Boat boat2;
    private BoatCollision bc;

    @Given("^There are 2 boats$")
    public void there_are_boats() throws Throwable {
        BoatGenerator bg = new BoatGenerator();
        boat1 = bg.generateBoat();
        boat2 = bg.generateBoat();
        bc = new BoatCollision(boat1, boat2);
    }

    @When("^the boats are within the collision range$")
    public void the_boats_are_within_the_collision_range() throws Throwable {
        boat1.getMark().setX(100);
        boat1.getMark().setY(100);
        boat2.getMark().setX(104);
        boat2.getMark().setY(104);
    }

    @When("^the boats are not within the collision range$")
    public void the_boats_are_not_within_the_collision_range() throws Throwable {
        boat1.getMark().setX(100);
        boat1.getMark().setY(100);
        boat2.getMark().setX(150);
        boat2.getMark().setY(100);
    }

    @Then("^a collision will be detected$")
    public void a_collision_will_be_detected() throws Throwable {
        Assert.assertTrue(bc.isColliding());
    }

    @Then("^a collision will not be detected$")
    public void a_collision_will_not_be_detected() throws Throwable {
        Assert.assertFalse(bc.isColliding());
    }


}
