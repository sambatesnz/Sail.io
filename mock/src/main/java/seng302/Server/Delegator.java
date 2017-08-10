package seng302.Server;

import seng302.Race;
import seng302.RaceObjects.Boat;
import seng302.RaceObjects.Mark;
import seng302.UserInput.BoatAction;

public class Delegator {
    int ez = 1;

    private Race race;
    public Delegator(Race race) {
        this.race = race;
    }


    public void processCommand(int messageCommand) {
        int boatID = 103; //Hardcoded to NZL for testing purposes, and while packets don't contain ID

        if (messageCommand == BoatAction.TACK_OR_GYBE.getBoatAction()) {
            tackOrGybeBoat(boatID);
        } else if (messageCommand == BoatAction.UPWIND.getBoatAction()) {
            Boat boat = race.getBoatByID(boatID);
            boat.getMark().setX(boat.getX()+15*ez);
//            changeBoatHeading(boatID, true);
        } else if (messageCommand == BoatAction.DOWNWIND.getBoatAction()) {
            Boat boat = race.getBoatByID(boatID);
            boat.getMark().setY(boat.getY()+15*ez);
//            changeBoatHeading(boatID, false);
        } else if (messageCommand == BoatAction.SAILS_IN.getBoatAction()) {
//            Boat boat = race.getBoatByID(boatID);
//            boat.setSailsOut(false);
            ez = 1;
        } else if (messageCommand == BoatAction.SAILS_OUT.getBoatAction()) {
            Boat boat = race.getBoatByID(boatID);
            boat.setSailsOut(true);
            ez = -1;
        }
    }

    /**
     * Forces the boat to tack/gybe based on its current position
     * @param sourceId source id of the boat
     */
    public void tackOrGybeBoat(int sourceId) {
        Boat boat = race.getBoatByID(sourceId);
        int windDirection = race.getWindHeading();
        boat.tackOrGybe(windDirection);
    }

    /**
     * Updates the heading of a boat identified by the given source ID
     * @param sourceID The ID of the boat to be updated
     * @param upwind True if the boat is turning upwind, false if turning downwind
     */
    public void changeBoatHeading(int sourceID, boolean upwind){
        Boat boat = race.getBoatByID(sourceID);
        int windDirection = race.getWindHeading();
        boat.updateHeading(windDirection, upwind);
    }
}
