package seng302.Server;

import seng302.Race;
import seng302.RaceObjects.Boat;
import seng302.UserInput.BoatAction;

public class Delegator {

    private Race race;

    public Delegator(Race race) {
        this.race = race;
    }


    public void processCommand(int messageCommand) {
        int boatID = 103; //Hardcoded to NZL for testing purposes, and while packets don't contain ID

        if (messageCommand == BoatAction.UPWIND.getBoatAction()) {
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
     * Updates the heading of a boat identified by the given source ID
     * @param sourceID The ID of the boat to be updated
     * @param upwind True if the boat is turning upwind, false if turning downwind
     */
    public void changeBoatHeading(int sourceID, boolean upwind){
        Boat boat = race.getBoatByID(sourceID);
        int windDirection = ((((race.updateWindDirection()  * 360) / 65536)+360)%360);  //TODO Make this into a function
        boat.updateHeading(windDirection, upwind);
    }
}
