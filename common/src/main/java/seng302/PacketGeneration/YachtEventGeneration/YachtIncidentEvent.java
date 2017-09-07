package seng302.PacketGeneration.YachtEventGeneration;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Status codes for yacht incident events
 */
public enum YachtIncidentEvent {
    DEFAULT(0),
    FINISHED(11);

    private int value;
    private static Map<Integer, YachtIncidentEvent> enumMap = Collections.unmodifiableMap(initMap());


    YachtIncidentEvent(int value){
        this.value = value;
    }

    public int getValue(){
        return this.value;
    }

    public static YachtIncidentEvent getEnum(int value) { return enumMap.get(value); }

    private static Map<Integer, YachtIncidentEvent> initMap() {
        Map<Integer, YachtIncidentEvent> map = new HashMap<>();
        for (YachtIncidentEvent event : values()) {
            map.put(event.getValue(), event);
        }
        return map;
    }

}
