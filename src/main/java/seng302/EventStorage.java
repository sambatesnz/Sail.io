package seng302;

/**
 * Created by osr13 on 6/03/17.
 */
public class EventStorage {

    public String boatName;
    public String eventName;
    public float eventTime;
    public int nextHeading;

    public EventStorage(String boatName, String eventName, float eventTime, int nextHeading) {
        this.boatName = boatName;
        this.eventName = eventName;
        this.eventTime = eventTime;
        this.nextHeading = nextHeading;
    }

    public float getEventTime() {
        return eventTime;
    }
}
