package seng302.PacketGeneration.ParticipantConfirmationGeneration;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Enum for the Participant Confirmation message status
 */
public enum ConfirmationStatus {

    SPECTATING(0),
    PLAYING(1),
    TUTORIAL_MODE(2),
    GHOSTING(3),
    GENERAL_FAILURE(10),
    RACE_FULL_FAILURE(11);  // slots in the race are exceeded

    private int status;
    private static Map<Integer, ConfirmationStatus> enumMap = Collections.unmodifiableMap(initMap());

    ConfirmationStatus(int status){
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public static ConfirmationStatus getEnum(int value) { return enumMap.get(value); }

    private static Map<Integer, ConfirmationStatus> initMap() {
        Map<Integer, ConfirmationStatus> map = new HashMap<>();
        for (ConfirmationStatus event : values()) {
            map.put(event.getStatus(), event);
        }
        return map;
    }
}
