package seng302.Polars;

import seng302.RaceObjects.Boat;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by osr13 on 26/07/17.
 */
public class PolarUtils {

    private Map<Integer, PolarRatio> polarMap;

    private PolarUtils(){
        polarMap = new HashMap<>();
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
    }

    public void updateBoatSpeed(Boat boat, double windHeading, double windSpeed){
        double angleRelative = (boat.getHeading() - windHeading) % 180; //TODO

        for(Integer angle: polarMap.keySet()){
            // find closest angle



        }

        //use angle found to get new boat speed from windspeed
    }
}
