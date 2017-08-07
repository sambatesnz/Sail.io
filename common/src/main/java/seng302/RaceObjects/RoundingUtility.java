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
//        double xDummy = -yTarget;
//        double yDummy = xTarget;
        double xDummy = -Math.abs(yTarget - yLast);
        double yDummy = Math.abs(xTarget - xLast);
        
        String orientPerpendicular = RoundingUtility.getOrientation(boat, 0, 0, xDummy, yDummy);
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

        if (courseRoundingInfo.get(boat.getTargetMarkIndex()).getKey().getMarks().size() > 1) {
            Mark leftMark;
            Mark rightMark;
            if (boat.isRightOfNextGate()) {
                leftMark = currentTarget.getMarks().get(0);
                rightMark = currentTarget.getMarks().get(1);
            } else {
                rightMark = currentTarget.getMarks().get(0);
                leftMark = currentTarget.getMarks().get(1);
            }
            orientPerpendicular = RoundingUtility.getOrientation(boat, leftMark.getX(), leftMark.getY(), rightMark.getX(), rightMark.getY());
//            leftMark.getX() + rightMark.getX()

            double xDummyLeft = leftMark.getX() - rightMark.getY();
            double yDummyLeft = leftMark.getY() - rightMark.getX();
            xDummyLeft = -yDummyLeft;
            yDummyLeft = xDummyLeft;

            double xDummyRight = rightMark.getX() - rightMark.getY();
            double yDummyRight = rightMark.getY() - rightMark.getX();
            xDummyRight = yDummyRight;
            yDummyRight = -xDummyRight;

            String orientParallelLeft = RoundingUtility.getOrientation(boat, leftMark.getX(), leftMark.getY(), xDummyLeft, yDummyLeft);
            String orientParallelRight = RoundingUtility.getOrientation(boat, rightMark.getX(), rightMark.getY(), xDummyRight, yDummyRight);

//            if (orientPerpendicular.equals(RIGHT) && orientParallel.equals(portLeft)) {
//                boat.resetRoundingStage();
//            } else if ((orientPerpendicular.equals(RIGHT) && orientParallel.equals(portRight) && boat.getRoundingStage() == 0) ||
//                    (orientPerpendicular.equals(LEFT) && orientParallel.equals(portRight) && boat.getRoundingStage() == 1) ||
//                    (orientPerpendicular.equals(LEFT) && orientParallel.equals(portLeft) && boat.getRoundingStage() == 2)) {
//                boat.updateRoundingStage();
//            }
            if (orientPerpendicular.equals(RIGHT)) {
                if (orientParallelLeft.equals(LEFT) || orientParallelRight.equals(RIGHT)) {
                    boat.resetRoundingStage();
                } else if (boat.getRoundingStage() == 0) {
                    boat.updateRoundingStage();
                }
            }
            else if (orientPerpendicular.equals(LEFT) && (boat.getRoundingStage() == 1 ||
                    ((orientParallelLeft.equals(LEFT) || orientParallelRight.equals(RIGHT)) && boat.getRoundingStage() == 2))) {
                boat.updateRoundingStage();
            }
        }



        if (boat.getRoundingStage() == 3) {
            boat.resetRoundingStage();
            boat.incrementTargetMarkIndex();
            if (courseRoundingInfo.get(boat.getTargetMarkIndex()).getKey().getMarks().size() > 1) {
                boat.setRightOfNextGate(isBoatRightOfGate(courseRoundingInfo, boat));
            }
        }
    }

    private static boolean isBoatRightOfGate(List<Pair<CompoundMark, String>> courseRoundingInfo, Boat boat) {
        Mark currentTarget1 = courseRoundingInfo.get(boat.getTargetMarkIndex()).getKey().getMarks().get(0);
        Mark currentTarget2 = courseRoundingInfo.get(boat.getTargetMarkIndex()).getKey().getMarks().get(1);
        String orientPerpendicularCheck = RoundingUtility.getOrientation(boat, currentTarget1.getX(),
                currentTarget1.getY(), currentTarget2.getX(), currentTarget2.getY());
        return orientPerpendicularCheck.equals(RIGHT);
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
