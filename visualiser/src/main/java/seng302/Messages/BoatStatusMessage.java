package seng302.Messages;


public class BoatStatusMessage {
    private long sourceID;
    private int status;
    private int legNumber;
    private long estTimeToNextMark;
    private long estTimeToFinish;

    public BoatStatusMessage(long sourceID, int status, int legNumber,
                             long estTimeToNextMark, long estTimeToFinish) {
        this.sourceID = sourceID;
        this.status = status;
        this.legNumber = legNumber;
        this.estTimeToNextMark = estTimeToNextMark;
        this.estTimeToFinish = estTimeToFinish;
        System.out.println(legNumber);
    }
}
