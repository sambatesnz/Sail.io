package seng302.RaceObjects;


public class Rounding {

    /**
     * Determines whether a boat is to the left or to the right of a line between the last mark
     * a boat has passed and the mark a boat is heading to.
     * @param boat The boat in question
     * @param x0 Last Marks x coordinate
     * @param y0 Last Marks y coordinate
     * @param x1 Current Marks x coordinate
     * @param y1 Current Marks y coordinate
     * @return "Left" if the boat is left of (or on) the line, or "Right" if the boat is to the right
     */
    public static String getOrientation(Boat boat, double x0, double y0, double x1, double y1) {

        double x2 = boat.getX();
        double y2 = boat.getY();

        double value = (x1 - x0) * (y2 - y0) - (x2 - x0) * (y1 - y0);

        if (value >= 0) {
            return "Left";
        } else {
            return "Right";
        }
    }
}
