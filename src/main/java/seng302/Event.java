package seng302;

/**
 * Created by osr13 on 3/03/17.
 */
public class Event {

    private String eventName;
    private int eventOrder;

    public Event(String eventName, int eventOrder) {
        this.eventName = eventName;
        this.eventOrder = eventOrder;
    }

    public String getEventName() {
        return eventName;
    }
}
