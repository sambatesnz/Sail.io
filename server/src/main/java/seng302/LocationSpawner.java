package seng302;

import seng302.RaceObjects.BoatInterface;
import seng302.RaceObjects.CourseLimit;
import seng302.RaceObjects.Mark;

import java.util.List;
import java.util.Random;

/**
 * Created by osr13 on 20/09/17.
 */
public class LocationSpawner {

    public static void generateSpawnPoints(List<BoatInterface> boats, List<CourseLimit> courseLimits, CollisionDetector collisionDetector){
        for (BoatInterface boat : boats) {
            getRandomSpawnLocation(boat, courseLimits);
            while (!collisionDetector.checkAllCollisions(boat, courseLimits, boats)) {
                getRandomSpawnLocation(boat, courseLimits);
            }
        }
    }

    private static void getRandomSpawnLocation(BoatInterface boat, List<CourseLimit> courseLimits) {
        double maxLat = Double.MIN_VALUE;
        double maxLong = Double.MIN_VALUE;
        double minLat = Double.MAX_VALUE;
        double minLong = Double.MAX_VALUE;
        for (CourseLimit cl : courseLimits) {
            if (cl.getLat() > maxLat) {
                maxLat = cl.getLat();
            }
            if (cl.getLat() < minLat) {
                minLat = cl.getLat();
            }
            if (cl.getLon() > maxLong) {
                maxLong = cl.getLon();
            }
            if (cl.getLon() < minLong) {
                minLong = cl.getLon();
            }
        }
        Mark newMark = new Mark(getRandomDouble(maxLat, minLat), getRandomDouble(maxLong, minLong));
        boat.setMark(newMark);
        boat.setHeading(getRandomDouble(359, 0));
    }

    private static double getRandomDouble(double max, double min) {
        Random random = new Random();
        return min + (max - min) * random.nextDouble();
    }
}
