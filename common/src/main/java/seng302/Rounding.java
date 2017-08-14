package seng302;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum Rounding {
    PORT_STARBOARD("PS"),
    STARBOARD_PORT("SP"),
    PORT("Port"),
    STARBOARD("Stbd");

    private final String rounding;
    private static Map<String, Rounding> enumMap = Collections.unmodifiableMap(initMap());

    Rounding(String rounding){
        this.rounding = rounding;
    }

    public String getRounding() {
        return rounding;
    }

    public static Rounding getEnum(String value) {
        return enumMap.get(value);
    }

    private static Map<String, Rounding> initMap() {
        Map<String, Rounding> map = new HashMap<>();
        for (Rounding rounding : values()) {
            map.put(rounding.getRounding(), rounding);
        }
        return map;
    }
}
