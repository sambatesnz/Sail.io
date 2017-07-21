package seng302.Client.Messages;


import seng302.Race.Race;

import java.util.Arrays;

/**
 * Class that holds anc can update the details for a race given by a race status packet.
 * Passes on the boat status to the BoatStatusMessage class.
 */

public class RaceStatusMessage {
    private long currentTime;
    private long raceID;
    private int raceStatus;
    private long expectedStartTime;
    private double windDirection;
    private int windSpeed;
    private int numBoatsInRace;
    private int raceType;

    private BoatStatusMessage[] boatDetailsList;

    private Race race;

    /**
     * Constructor for the class. Takes in an array of bytes from a Race Status packet
     * as a parameter, and extracts the relevant information from it so it can be used to
     * update the status of the race, and create Boat Status Messages for each boat in the race.
     * @param bytes The array of bytes from the body of a race status packet
     */
    public RaceStatusMessage(byte[] bytes, Race race) {
        currentTime = Message.byteArrayToLong(bytes, 1, 6);
        raceID = Message.byteArrayToLong(bytes, 7, 4);
        raceStatus = Message.byteArrayToInt(bytes, 11, 1);
        expectedStartTime = Message.byteArrayToLong(bytes, 12, 6);
        windDirection = Message.byteArrayToInt(bytes, 18, 2) * 360 / 65536.0;
        windSpeed = Message.byteArrayToInt(bytes, 20, 2);
        numBoatsInRace = Message.byteArrayToInt(bytes, 22, 1); //To convert from char to int
        raceType = Message.byteArrayToInt(bytes, 23, 1);
        this.race = race;


        boatDetailsList = new BoatStatusMessage[numBoatsInRace];
        int offset = 24;

        for (int i = 0; i < numBoatsInRace; i++) {
            long boatSourceID = Message.byteArrayToLong(bytes, offset, 4);
            int boatStatus = Message.byteArrayToInt(bytes, offset + 4, 1);
            int boatLegNumber = Message.byteArrayToInt(bytes, offset + 5, 1);
            long boatTimeToNextMark = Message.byteArrayToLong(bytes, offset + 8, 6);
            long boatTimeToFinish = Message.byteArrayToLong(bytes, offset + 14, 6);

            BoatStatusMessage boatDetails = new BoatStatusMessage(boatSourceID, boatStatus,
                    boatLegNumber, boatTimeToNextMark, boatTimeToFinish, race);
            boatDetailsList[i] = boatDetails;

            offset += 20;
        }

        updateRaceDetails();
        for (BoatStatusMessage boatInfo : boatDetailsList) {
            boatInfo.setBoatDetails();
        }
    }

    /**
     * Updates the following details of a yacht race:
     * - Wind Heading
     * - Wind Speed
     * - Expected Start Time
     * - Race Status
     */
    public void updateRaceDetails() {
        race.setWindHeading(windDirection);
        race.setWindSpeed(windSpeed);
        race.setExpectedStartTime(expectedStartTime);
        race.setRaceStatus(raceStatus);
        race.setCurrentTime(currentTime);
    }


}
