package seng302;

import seng302.RaceObjects.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static java.lang.Math.sqrt;

/**
 * Detects collisions between two boats, boats and marks, and boats and the boundary
 */
public class CollisionDetector {

    private static double MARK_SIZE = 5;
    private seng302.Modes.Race race;

    public CollisionDetector(seng302.Modes.Race race){
        this.race = race;
    }
    /**
     * When called, checks if the given boat is in the same location, and hence colliding with any of the marks in the
     * given list
     * @param boat the boat to check against
     * @param compoundMarks the marks to check against
     * @return true if the boat is colliding with one of the marks, otherwise, false.
     */
    public boolean checkMarkCollisions(BoatInterface boat, List<CompoundMark> compoundMarks) {

        Mark boatMark = boat.getMark();

        for (CompoundMark mark : compoundMarks) {
            ArrayList<Mark> gateMarks = mark.getMarks();

            for (Mark gateMark : gateMarks) {
                if (checkCollisions(boatMark, gateMark, boat.getSize(), MARK_SIZE)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     *Checks the collisions of two marks using their sizes and locations
     * @param mark1 first mark to be checked
     * @param mark2 second mark to be checked
     * @param size1 size of first mark
     * @param size2 size of second mark
     * @return true if colliding, false otherwise
     */
    private boolean checkCollisions(Mark mark1, Mark mark2, double size1, double size2) {

        double mark1X = mark1.getX();
        double mark1Y = mark1.getY();

        double mark2X = mark2.getX();
        double mark2Y = mark2.getY();

        double xDist = mark1X - mark2X;
        double yDist = mark1Y - mark2Y;

        double distance = sqrt((xDist * xDist) + (yDist * yDist));

        return distance < (size1 + size2);
    }

    /**
     * Check the collision of a boat
     * @param boat the boat being checked
     * @return the BoatCollision if the boat is colliding, null otherwise
     */
    public BoatCollision checkBoatCollision(BoatInterface boat) {

        Map<BoatPair, BoatCollision> collisionMap = race.getCollisionMap();

        for (BoatInterface checkBoat : race.getBoats()) {
            if (!checkBoat.equals(boat)) { //not the same boat
                BoatPair boatPair = new BoatPair(boat, checkBoat);
                BoatCollision boat1Collision = collisionMap.get(boatPair);
                if (boat1Collision.isColliding()) return boat1Collision;
            }
        }
        return null;
    }

    /**
     * Checks if the boat is within the boundary using a point in polygon algorithm
     * @param boat boat location to be checked
     * @param boundaries the boundaries to be checked
     * @return true or false depending on if it is in the course boundaries or not
     */
    public boolean checkWithinBoundary(BoatInterface boat, List<CourseLimit> boundaries) {

        boolean withinBoundaries = false;

        for (int j = 0; j < boundaries.size(); j++) {
            int i = j + 1;

            i = i % boundaries.size();

            Mark mark = new Mark();
            double iY = mark.convertToY(boundaries.get(i).getLat());
            double jY = mark.convertToY(boundaries.get(j).getLat());
            double iX = mark.convertToX(boundaries.get(i).getLon());
            double jX = mark.convertToX(boundaries.get(j).getLon());

            if ((iY > boat.getY()) != (jY > boat.getY())
                    && (boat.getX() < (jX - iX) * (boat.getY() - iY) /  (jY - iY) + iX)) {
                withinBoundaries = !withinBoundaries;
            }
        }
        return withinBoundaries;
    }

    public boolean checkAllCollisions(BoatInterface boat, List<CourseLimit> boundaries, List<BoatInterface> boats) {
        boolean isCollision = false;
        BoatCollision bc = checkBoatCollision(boat);
        if (bc != null) return true;
        isCollision = checkWithinBoundary(boat, boundaries)
                || checkMarkCollisions(boat, race.getCompoundMarks());
        return isCollision;
    }


    public Iterable<? extends BoatPair> getCurrentCollisions(seng302.Modes.Race race) {
        return new ArrayList<>();
    }
}
