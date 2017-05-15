package seng302.Messages;


public class RaceStatusMessage {
    private long currentTime;
    private long raceID;
    private int raceStatus;
    private long expectedStartTime;
    private double windDirection;
    private int windSpeed;
    private int numBoatsInRace;
    private int raceType;

    private long boatSourceID;
    private int boatStatus;
    private int boatLegNumber;
    private long boatTimeToNextMark;
    private long boatTimeToFinish;

    private BoatStatusMessage[] boatDetailsList;

    public RaceStatusMessage(byte[] bytes) {
        currentTime = Message.byteArrayToLong(bytes, 1, 6);
        raceID = Message.byteArrayToLong(bytes, 7, 4);
        raceStatus = Message.byteArrayToInt(bytes, 11, 1);
        expectedStartTime = Message.byteArrayToLong(bytes, 12, 6);
        windDirection = Message.byteArrayToInt(bytes, 18, 2) * 360 / 65536.0;
        windSpeed = Message.byteArrayToInt(bytes, 20, 2);
        numBoatsInRace = Message.byteArrayToInt(bytes, 22, 1);
        raceType = Message.byteArrayToInt(bytes, 23, 1);
//        System.out.println("Current time: " + currentTime);
//        System.out.println("Race ID: " + raceID);
//        System.out.println("Race Status: " + raceStatus);
//        System.out.println("Expected Start Time: " + expectedStartTime);
//        System.out.println("Wind Direction: " + windDirection);
//        System.out.println("Wind Speed: " + windSpeed);
//        System.out.println("Number of Boats in Race: " + numBoatsInRace);
//        System.out.println("Race Type: " + raceType);

        int indent = 24; //25th byte

        boatDetailsList = new BoatStatusMessage[numBoatsInRace];

        for (int i = 0; i < numBoatsInRace; i++) {
            boatSourceID = Message.byteArrayToLong(bytes, indent, 4);
            boatStatus = Message.byteArrayToInt(bytes, indent + 4, 1);
            boatLegNumber = Message.byteArrayToInt(bytes, indent + 5, 1);
            boatTimeToNextMark = Message.byteArrayToLong(bytes, indent + 8, 6);
            boatTimeToFinish = Message.byteArrayToLong(bytes, indent + 14, 6);

            BoatStatusMessage boatDetails = new BoatStatusMessage(boatSourceID, boatStatus,
                    boatLegNumber, boatTimeToNextMark, boatTimeToFinish);
            boatDetailsList[i] = boatDetails;

            indent += 20;
        }
    }


}
