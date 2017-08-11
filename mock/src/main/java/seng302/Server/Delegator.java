package seng302.Server;

import seng302.Race;
import seng302.RaceObjects.Boat;
import seng302.UserInputController.BoatAction;

public class Delegator {

    private Race race;

    public Delegator(Race race) {
        this.race = race;
    }


    public void processCommand(BoatAction messageCommand) {
        int boatID = 101; //Hardcoded to NZL for testing purposes, and while packets don't contain ID
        switch (messageCommand) {
            case UPWIND: {
                changeBoatHeading(boatID, true);
                break;
            }
            case DOWNWIND: {
                changeBoatHeading(boatID, false);
                break;
            }
        }
    }

    /**
     * Updates the heading of a boat identified by the given source ID
     * @param sourceID The ID of the boat to be updated
     * @param upwind True if the boat is turning upwind, false if turning downwind
     */
    public void changeBoatHeading(int sourceID, boolean upwind){
        System.out.println("changed boat heading based on command");
        Boat boat = race.getBoatByID(sourceID);
        int windDirection = ((((race.updateWindDirection()  * 360) / 65536)+360)%360);  //TODO Make this into a function
        boat.updateHeading(windDirection, upwind);
    }
}