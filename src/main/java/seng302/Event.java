package seng302;

/**
 * Created by osr13 on 3/03/17.
 */
public class Event {

    private String eventName;
    public int eventOrder;
    public float distToPrevEvent;

    public Event(String eventName, int eventOrder, float dist) {
        this.eventName = eventName;
        this.eventOrder = eventOrder;
        this.distToPrevEvent = dist;
    }

    public String getEventName() {
        return eventName;
    }
}
