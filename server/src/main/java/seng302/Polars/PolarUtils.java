package seng302.Polars;

import seng302.RaceObjects.GenericBoat;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.abs;

/**
 * Created by osr13 on 26/07/17.
 */
public class PolarUtils {

    private PolarUtils(){}


    /**
     * Updates a boats speed based on the nearest polar value
     * @param boat boat to update
     * @param windHeading current wind heading
     * @param windSpeed current wind speed
     */
    public static void updateBoatSpeed(GenericBoat boat, double windHeading, int windSpeed){
        Map<Integer, PolarRatio> polarMap = generatePolarMap();
        double diff = abs(windHeading - boat.getHeading());
        double angleRelative = abs(180-diff);
        int closestRefAngle = 0;

        for(Integer angle: polarMap.keySet()){
            // find closest angle
            double d1 = abs(angle - angleRelative);
            if (d1 <= 7.5) {
                closestRefAngle = angle;
                break;
            }
        }
        int newSpeed = (int) (polarMap.get(closestRefAngle).getRatio() * windSpeed);
        if (!boat.isSailsOut()) {
            newSpeed = 0;
        }
        boat.setSpeed(newSpeed);
    }

    private static Map<Integer,PolarRatio> generatePolarMap() {
        Map<Integer,PolarRatio> polarMap = new HashMap<>();
        polarMap.put(0, PolarRatio.ANGLE0);
        polarMap.put(15, PolarRatio.ANGLE15);
        polarMap.put(30, PolarRatio.ANGLE30);
        polarMap.put(45, PolarRatio.ANGLE45);
        polarMap.put(60, PolarRatio.ANGLE60);
        polarMap.put(75, PolarRatio.ANGLE75);
        polarMap.put(90, PolarRatio.ANGLE90);
        polarMap.put(105, PolarRatio.ANGLE105);
        polarMap.put(120, PolarRatio.ANGLE120);
        polarMap.put(135, PolarRatio.ANGLE135);
        polarMap.put(150, PolarRatio.ANGLE150);
        polarMap.put(165, PolarRatio.ANGLE165);
        polarMap.put(180, PolarRatio.ANGLE180);
        return polarMap;
    }
}
