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
            case 4: {
                tackOrGybeBoat(boatID);
                break;
            }
            case 5: { //Upwind command
                changeBoatHeading(boatID, true);
                break;
            }
            case 6: { //Downwind command
                changeBoatHeading(boatID, false);
                break;
            }

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
