package seng302.Server;

import seng302.Race;
import seng302.RaceObjects.Boat;

public class Delegator {

    private Race race;

    public Delegator(Race race) {
        this.race = race;
    }


    public void processCommand(int messageCommand) {
        int boatID = 103; //Hardcoded to NZL for testing purposes, and while packets don't contain ID
        switch (messageCommand) {
            case 1:
                VMG(boatID);
                break;
            case 4:
                tackOrGybeBoat(boatID);
                break;
            case 5:  //Upwind command
                changeBoatHeading(boatID, true);
                break;
            case 6:  //Downwind command
                changeBoatHeading(boatID, false);
                break;
        }
    }

    private void VMG(int sourceID) {
        Boat boat = race.getBoatByID(sourceID);
        int windHeading = race.getWindHeading();
        boat.setHeadingToVMG(windHeading);
    }

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
