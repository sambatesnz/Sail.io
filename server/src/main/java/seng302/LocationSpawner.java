package seng302;

import seng302.RaceObjects.BoatCollision;
import seng302.RaceObjects.CourseLimit;
import seng302.RaceObjects.GenericBoat;
import seng302.RaceObjects.Mark;

import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Class that generates points around a course for boats to respawn at
 */
public class LocationSpawner {

    public static void generateSpawnPoints(List<GenericBoat> boats,
                                           List<CourseLimit> spawnLimits,
                                           List<CourseLimit> courseLimits,
                                           CollisionDetector collisionDetector,
                                           Map<BoatPair, BoatCollision> collisionMap) {

        for (GenericBoat boat : boats) {
            getRandomSpawnLocation(boat, spawnLimits);
            while (collisionDetector.hasCollision(boat, courseLimits, boats, collisionMap)) {
                getRandomSpawnLocation(boat, spawnLimits);
            }
            boat.resetAgarSize();
        }
    }

    private static void getRandomSpawnLocation(GenericBoat boat, List<CourseLimit> courseLimits) {
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
