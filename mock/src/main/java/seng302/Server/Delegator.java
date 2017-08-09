package seng302.Server;

import seng302.Race;
import seng302.RaceObjects.Boat;
import seng302.UserInput.BoatAction;

public class Delegator {

    private Race race;
    public Delegator(Race race) {
        this.race = race;
    }

    /**
     * Takes in a Boat Action, and delegates functions to responds to the key-presses
     * @param messageCommand Boat Action value
     */
    public void processCommand(int messageCommand) {
        int boatID = 103; //Hardcoded to NZL for testing purposes, and while packets don't contain ID
        if (messageCommand == BoatAction.VMG.getBoatAction()) {
            VMG(boatID);
        } else if (messageCommand == BoatAction.TACK_OR_GYBE.getBoatAction()) {
            tackOrGybeBoat(boatID);
        } else if (messageCommand == BoatAction.UPWIND.getBoatAction()) {
            changeBoatHeading(boatID, true);
        } else if (messageCommand == BoatAction.DOWNWIND.getBoatAction()) {
            changeBoatHeading(boatID, false);
        } else if (messageCommand == BoatAction.SAILS_IN.getBoatAction()) {
            Boat boat = race.getBoatByID(boatID);
            boat.setSailsOut(false);
        } else if (messageCommand == BoatAction.SAILS_OUT.getBoatAction()) {
            Boat boat = race.getBoatByID(boatID);
            boat.setSailsOut(true);
        }
    }

    /**
     * Forces the boat to change heading to VMG based on its current position
     * @param sourceID source id of the boat
     */
    private void VMG(int sourceID) {
        Boat boat = race.getBoatByID(sourceID);
        int windHeading = race.getWindHeading();
        boat.setHeadingToVMG(windHeading);
    }

    /**
     * Forces the boat to tack/gybe based on its current position
     * @param sourceID source id of the boat
     */
    private void tackOrGybeBoat(int sourceID) {
        Boat boat = race.getBoatByID(sourceID);
        int windHeading = race.getWindHeading();
        boat.tackOrGybe(windHeading);
    }

    /**
     * Updates the heading of a boat identified by the given source ID
     * @param sourceID The ID of the boat to be updated
     * @param upwind True if the boat is turning upwind, false if turning downwind
     */
    private void changeBoatHeading(int sourceID, boolean upwind){
        Boat boat = race.getBoatByID(sourceID);
        int windHeading = race.getWindHeading();
        boat.updateHeading(windHeading, upwind);
    }
}
