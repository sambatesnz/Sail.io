package seng302.objects;

/**
 * Created by osr13 on 3/03/17.
 */
public class Event {

    private String eventName;
    public int eventOrder;
    public float distToPrevEvent;
    public int nextHeading;

    public Event(String eventName, int eventOrder, float dist, int nextHeading) {
        this.eventName = eventName;
        this.eventOrder = eventOrder;
        this.distToPrevEvent = dist;
        this.nextHeading = nextHeading;
    }

    public String getEventName() {
        return eventName;
    }
}
