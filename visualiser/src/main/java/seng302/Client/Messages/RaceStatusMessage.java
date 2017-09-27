package seng302.Client.Messages;


import seng302.PacketGeneration.PacketUtils;
import seng302.PacketGeneration.RaceStatus;
import seng302.PacketGeneration.RaceStatusGeneration.BoatStatus;
import seng302.PacketParsing.PacketParserUtils;
import seng302.RaceObjects.Race;

/**
 * Class that holds anc can update the details for a race given by a race status packet.
 * Passes on the boat status to the BoatStatusMessage class.
 */

public class RaceStatusMessage {
    private long currentTime;
    private long raceID;
    private RaceStatus raceStatus;
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
     * @param race the race being run
     */
    public RaceStatusMessage(byte[] bytes, Race race) {
        currentTime = PacketParserUtils.byteArrayToLong(bytes, 1, 6);
        raceID = PacketParserUtils.byteArrayToLong(bytes, 7, 4);
        int raceStatus = PacketParserUtils.byteArrayToInt(bytes, 11, 1);
        this.raceStatus = RaceStatus.getStatus(raceStatus);
        expectedStartTime = PacketParserUtils.byteArrayToLong(bytes, 12, 6);
        windDirection = PacketParserUtils.byteArrayToInt(bytes, 18, 2) * 360 / 65536.0;
        windSpeed = PacketParserUtils.byteArrayToInt(bytes, 20, 2);
        numBoatsInRace = PacketParserUtils.byteArrayToInt(bytes, 22, 1); //To convert from char to int
        raceType = PacketParserUtils.byteArrayToInt(bytes, 23, 1);
        this.race = race;


        boatDetailsList = new BoatStatusMessage[numBoatsInRace];
        int offset = 24;

        for (int i = 0; i < numBoatsInRace; i++) {
            long boatSourceID = PacketParserUtils.byteArrayToLong(bytes, offset, 4);

            byte[] tempMessage = new byte[8];
            char boatStatus = PacketUtils.getCharFromByteArray(bytes, offset + 4, tempMessage, 1);
            BoatStatus status = BoatStatus.getAction(boatStatus);
            int boatLegNumber = PacketParserUtils.byteArrayToInt(bytes, offset + 5, 1);
            long boatTimeToNextMark = PacketParserUtils.byteArrayToLong(bytes, offset + 8, 6);
            long boatTimeToFinish = PacketParserUtils.byteArrayToLong(bytes, offset + 14, 6);

            BoatStatusMessage boatDetails = new BoatStatusMessage(boatSourceID, status,
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
     * - Current Time
     */
    public void updateRaceDetails() {
        race.setWindHeading(windDirection);
        race.setWindSpeed(windSpeed);
        race.setExpectedStartTime(expectedStartTime);
        race.setRaceStatus(raceStatus);
        race.setCurrentTime(currentTime);
    }
}
