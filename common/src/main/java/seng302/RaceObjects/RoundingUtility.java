package seng302.RaceObjects;


import javafx.util.Pair;

import java.util.List;

public class RoundingUtility {
    
    private static final String RIGHT = "Right";
    private static final String LEFT = "Left";

    public static void determineMarkRounding(List<Pair<CompoundMark, String>> courseRoundingInfo, Boat boat) {

        CompoundMark currentTarget = courseRoundingInfo.get(boat.getTargetMarkIndex()).getKey();
        CompoundMark lastTarget = courseRoundingInfo.get(boat.getLastMarkIndex()).getKey();
        double xTarget = currentTarget.getX();
        double yTarget = currentTarget.getY();
        double xLast = lastTarget.getX();
        double yLast = lastTarget.getY();
        double xDummy = -yTarget;
        double yDummy = xTarget;
        
        String orientPerpendicular = RoundingUtility.getOrientation(boat, xTarget, yTarget, xDummy, yDummy);
        String orientParallel = RoundingUtility.getOrientation(boat, xLast, yLast, xTarget, yTarget);

        String orientation = courseRoundingInfo.get(boat.getTargetMarkIndex()).getValue();
        String portLeft = "";
        String portRight = "";
        if (orientation.equals("Port")) {
            portLeft = LEFT;
            portRight = RIGHT;
        } else if (orientation.equals("Stbd")) {
            //  Mirror parallel line
            portLeft = RIGHT;
            portRight = LEFT;
        }
        if (orientPerpendicular.equals(RIGHT) && orientParallel.equals(portLeft)) {
            boat.resetRoundingStage();
        } else if ((orientPerpendicular.equals(RIGHT) && orientParallel.equals(portRight) && boat.getRoundingStage() == 0) ||
                   (orientPerpendicular.equals(LEFT) && orientParallel.equals(portRight) && boat.getRoundingStage() == 1) ||
                   (orientPerpendicular.equals(LEFT) && orientParallel.equals(portLeft) && boat.getRoundingStage() == 2)) {
            boat.updateRoundingStage();
        }
    }

    /**
     * Determines whether a boat is to the left or to the right of a line between the last mark
     * a boat has passed and the mark a boat is heading to.
     * @param boat The boat in question
     * @param x0 Last Marks x coordinate
     * @param y0 Last Marks y coordinate
     * @param x1 Current Marks x coordinate
     * @param y1 Current Marks y coordinate
     * @return "Left" if the boat is left of the line, or "Right" if the boat is to the right of (or on) the line
     */
    public static String getOrientation(Boat boat, double x0, double y0, double x1, double y1) {

        double x2 = boat.getX();
        double y2 = boat.getY();

        double value = (x1 - x0) * (y2 - y0) - (x2 - x0) * (y1 - y0);

        if (value > 0) {
            return LEFT;
        } else {
            return RIGHT;
        }
    }
}
