package seng302;

import seng302.BoatPair;
import seng302.RaceObjects.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.Math.sqrt;

/**
 * Created by msi52 on 11/09/17.
 */
public class CollisionDetector {

    private static double MARK_SIZE = 5;

    public int checkBoatCollisions(Boat boat, List<Boat> boats) {

        double boatX = boat.getMark().getX();
        double boatY = boat.getMark().getY();

        for (Boat collisionBoat : boats) {

            if (boat != collisionBoat) {

                double collisionX = collisionBoat.getMark().getX();
                double collisionY = collisionBoat.getMark().getY();

                double xDist = boatX - collisionX;
                double yDist = boatY - collisionY;

                double distance = sqrt((xDist * xDist) + (yDist * yDist));

                if (distance < (boat.getSize() + collisionBoat.getSize())) {
                    return 1;
                }
            }
        }

        return -1;
    }

    public boolean checkMarkCollisions(Boat boat, List<CompoundMark> compoundMarks) {

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

    private boolean checkCollisions(Mark mark1, Mark mark2, double size1, double size2) {

        double mark1X = mark1.getX();
        double mark1Y = mark1.getY();

        double mark2X = mark2.getX();
        double mark2Y = mark2.getY();

        double xDist = mark1X - mark2X;
        double yDist = mark1Y - mark2Y;

        double distance = sqrt((xDist * xDist) + (yDist * yDist));

        if (distance < (size1 + size2)) {
            return true;
        }

        return false;
    }

    public boolean checkBoatCollision(Boat boat, Race race) {

        Map<BoatPair, BoatCollision> collisionMap = race.getCollisionMap();

        for (Boat checkBoat : race.getBoats()) {
            if (!checkBoat.equals(boat)) {
                BoatPair boatPair = new BoatPair(boat, checkBoat);
                BoatCollision boat1Collision = collisionMap.get(boatPair);
                if (boat1Collision.isColliding()) return true;
            }
        }
        return false;
    }

    public boolean checkWithinBoundary(Boat boat, List<CourseLimit> boundaries) {

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

}
