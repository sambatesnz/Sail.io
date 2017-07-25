package seng302.RaceObjects;

import seng302.RaceObjects.CompoundMark;

/**
 * Represents a section of the Race
 */
public class Leg {
    private double distance;
    private CompoundMark start;
    private CompoundMark dest;
    private double heading;

    /**
     * Setter for the start landmark of the leg
     * @param start, the landmark that is to be the start.
     */
    public void setStart(CompoundMark start) {
        this.start = start;
    }

    /**
     * Setter for the dest landmark of the leg
     * @param dest, the landmark that is to be the dest.
     */
    public void setDest(CompoundMark dest) {
        this.dest = dest;
    }

    /**
     * Get the bearing that the boats are travelling on the leg
     * @return the heading of the boat
     */
    public double getHeading() {
        return heading;
    }

    /**
     * Get the distance of the leg
     * @return the distance of the leg
     */
    public double getDistance() {
        return distance;
    }

    /**
     * Get the landmark at the end of a leg
     * @return the landmark at the end of a leg
     */
    public CompoundMark getDest() {
        return dest;
    }

    /**
     * Get the landmark at the start of a leg
     * @return the landmark at the start of a leg
     */
    public CompoundMark getStart() {
        return start;
    }

    /**
     * Calculates the euclidian distance between start and dest
     * @return the distance between start and dest
     */
    private double calculateDistance() {
        double x1 = start.getX();
        double y1 = start.getY();
        double x2 = dest.getX();
        double y2 = dest.getY();
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    /**
     * Calculates the heading from start to dest
     * @return the heading from start to dest
     */
    private double calculateHeading() {

        double x1 = start.getX();
        double y1 = start.getY();
        double x2 = dest.getX();
        double y2 = dest.getY();
        double angle = Math.toDegrees(Math.atan2(Math.abs(y1-y2), Math.abs(x1-x2)));
        if (x1 <= x2 && y1 <= y2) {
            return 90 - angle;
        } else if (x1 < x2 && y1 >= y2) {
            return 90 + angle;
        } else if (x1 >= x2 && y1 > y2) {
            return 270 - angle;
        } else if (x1 > x2 && y1 < y2) {
            return 270 + angle;
        }
        return Double.NaN;
    }

    /**
     * Constructs a new leg
     * @param start landmark at the start of the leg
     * @param dest landmark at the end of the leg
     */
    public Leg(CompoundMark start, CompoundMark dest) {
        this.start = start;
        this.dest = dest;
        distance = calculateDistance();
        heading = calculateHeading();
    }
}
