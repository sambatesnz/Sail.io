package seng302.Messages;


import seng302.Race;

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
    public RaceStatusMessage(byte[] bytes) {
        currentTime = Message.byteArrayToLong(bytes, 1, 6);
        raceID = Message.byteArrayToLong(bytes, 7, 4);
        raceStatus = Message.byteArrayToInt(bytes, 11, 1);
        expectedStartTime = Message.byteArrayToLong(bytes, 12, 6);
        windDirection = Message.byteArrayToInt(bytes, 18, 2) * 360 / 65536.0;
        windSpeed = Message.byteArrayToInt(bytes, 20, 2);
        numBoatsInRace = Message.byteArrayToInt(bytes, 22, 1);
        raceType = Message.byteArrayToInt(bytes, 23, 1);

        int indent = 24;

        boatDetailsList = new BoatStatusMessage[numBoatsInRace];

        for (int i = 0; i < numBoatsInRace; i++) {
            long boatSourceID = Message.byteArrayToLong(bytes, indent, 4);
            int boatStatus = Message.byteArrayToInt(bytes, indent + 4, 1);
            int boatLegNumber = Message.byteArrayToInt(bytes, indent + 5, 1);
            long boatTimeToNextMark = Message.byteArrayToLong(bytes, indent + 8, 6);
            long boatTimeToFinish = Message.byteArrayToLong(bytes, indent + 14, 6);

            BoatStatusMessage boatDetails = new BoatStatusMessage(boatSourceID, boatStatus,
                    boatLegNumber, boatTimeToNextMark, boatTimeToFinish);
            boatDetailsList[i] = boatDetails;

            indent += 20;
        }

//        for (BoatStatusMessage boatInfo : boatDetailsList) {
//            boatInfo.setBoatDetails();
//        }
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
    }


}