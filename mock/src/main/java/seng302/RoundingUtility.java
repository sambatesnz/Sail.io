package seng302;


import javafx.util.Pair;
import seng302.RaceObjects.Boat;
import seng302.RaceObjects.CompoundMark;
import seng302.RaceObjects.Mark;

import java.util.List;

import static seng302.Rounding.*;

public class RoundingUtility {
    
    private static final String RIGHT = "Right";
    private static final String LEFT = "Left";

    /**
     * Determines where a boat is according to its current target mark, and updates the boat's target
     * mark if it has passed it.
     * @param courseRoundingInfo Race order of the marks in the race, with their rounding directions
     * @param boat The boat that the rounding info is wanted for
     */
    public static void determineMarkRounding(List<Pair<CompoundMark, Rounding>> courseRoundingInfo, Boat boat) {

        CompoundMark currentTarget = courseRoundingInfo.get(boat.getTargetMarkIndex()).getKey();

        if (currentTarget.getMarks().size() > 1) {
            Rounding rounding = courseRoundingInfo.get(boat.getTargetMarkIndex()).getValue();
            /*  More than 1 mark to pass, generally indicating a gate to pass between */
            Mark mark1 = currentTarget.getMarks().get(0);
            Mark mark2 = currentTarget.getMarks().get(1);

            /*  Create dummy mark coordinates to draw perpendicular lines to from the gate marks */
            double dummyMark1x = mark1.getX() - (mark1.getY() - mark2.getY());
            double dummyMark1y = mark1.getY() + (mark1.getX() - mark2.getX());

            double dummyMark2x = mark2.getX() + (mark2.getY() - mark1.getY());
            double dummyMark2y = mark2.getY() - (mark2.getX() - mark1.getX());

            /*  Always a line from mark1 to mark2 */
            String orientBetweenGates = RoundingUtility.getOrientation(boat.getX(), boat.getY(), mark1.getX(), mark1.getY(), mark2.getX(), mark2.getY());

            /*  Lines from each mark of the gate, going perpendicular clockwise to the line between the gates */
            String orientMark1 = RoundingUtility.getOrientation(boat.getX(), boat.getY(), mark1.getX(), mark1.getY(), dummyMark1x, dummyMark1y);
            String orientMark2 = RoundingUtility.getOrientation(boat.getX(), boat.getY(), mark2.getX(), mark2.getY(), dummyMark2x, dummyMark2y);

            CompoundMark lastTarget = courseRoundingInfo.get(boat.getTargetMarkIndex() - 1).getKey();

            String side = RoundingUtility.getOrientation(lastTarget.getX(), lastTarget.getY(), mark1.getX(), mark1.getY(), mark2.getX(), mark2.getY());
            String approachingSide;
            String passedSide;
            if (side.equals(RIGHT)) {
                approachingSide = RIGHT;
                passedSide = LEFT;
            } else {
                approachingSide = LEFT;
                passedSide = RIGHT;
            }

            if ((rounding.equals(PORT_STARBOARD) && side.equals(RIGHT)) || (rounding.equals(STARBOARD_PORT) && side.equals(LEFT))) {
                if (orientBetweenGates.equals(approachingSide)) {
                    if (orientMark1.equals(RIGHT) || orientMark2.equals(LEFT)) {
                        boat.resetRoundingStage();
                    } else if (boat.getRoundingStage() == 0) {
                        boat.updateRoundingStage();
                    }
                } else if (orientBetweenGates.equals(passedSide) &&
                        (((orientMark1.equals(LEFT) && orientMark2.equals(RIGHT)) && boat.getRoundingStage() == 1) ||
                        ((orientMark1.equals(RIGHT) || orientMark2.equals(LEFT)) && boat.getRoundingStage() == 2))) {
                    boat.updateRoundingStage();
                }
            } else if ((rounding.equals(STARBOARD_PORT) && side.equals(RIGHT)) || (rounding.equals(PORT_STARBOARD) && side.equals(LEFT))) {
                if (orientBetweenGates.equals(approachingSide)) {
                    if (orientMark1.equals(RIGHT) || orientMark2.equals(LEFT)) {
                        boat.resetRoundingStage();
                    } else if (orientMark1.equals(LEFT) && orientMark2.equals(RIGHT) && boat.getRoundingStage() == 2) {
                        boat.updateRoundingStage();
                    }
                } else if (orientBetweenGates.equals(passedSide)) {
                    if ((orientMark1.equals(RIGHT) || orientMark2.equals(LEFT)) && boat.getRoundingStage() == 0 ||
                        (orientMark1.equals(LEFT) && orientMark2.equals(RIGHT)) && boat.getRoundingStage() == 1) {
                        boat.updateRoundingStage();
                    }
                }
            }

        } else {
            /*  Only one mark to round */
            CompoundMark lastTarget = courseRoundingInfo.get(boat.getLastMarkIndex()).getKey();
            double xTarget = currentTarget.getX();
            double yTarget = currentTarget.getY();
            double xLast = lastTarget.getX();
            double yLast = lastTarget.getY();

            /*  Create dummy mark coordinates to draw a perpendicular line to from the target mark */
            double xDummy = Math.abs(xTarget + (yTarget - yLast));
            double yDummy = Math.abs(yTarget - (xTarget - xLast));

            /*  Create a line from the boats last mark to the boats target mark */
            String orientPerpendicular = RoundingUtility.getOrientation(boat.getX(), boat.getY(), xTarget, yTarget, xDummy, yDummy);

            /*  Create a line to the dummy mark, which is perpendicular clockwise to the line between the gates */
            String orientParallel = RoundingUtility.getOrientation(boat.getX(), boat.getY(), xLast, yLast, xTarget, yTarget);

            Rounding sideToPass = courseRoundingInfo.get(boat.getTargetMarkIndex()).getValue();
            String portLeft = "";
            String portRight = "";
            if (sideToPass.equals(PORT)) { /*  Boat must round the mark with the mark in its Port(Left) */
                portLeft = LEFT;
                portRight = RIGHT;
            } else if (sideToPass.equals(STARBOARD)) { /*  Boat must round the mark with the mark in its Starboard(Right) */
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
//        if (((int) System.currentTimeMillis()) % 2 == 0) {
        System.out.print(boat.getRoundingStage() + " ");
        System.out.flush();
//        }

        /*  If the boat has fully rounded the mark, change its current target mark index to the next mark in the race */
        if (boat.getRoundingStage() == 3) {
            boat.resetRoundingStage();
            boat.incrementTargetMarkIndex();
        }
    }

    /**
     * Determines whether a point is to the left or to the right of an infinite line between two marks
     * defined as going through the point (x0, y0) going towards, and through, the point (x1, y1).
     * @param x the target x in question
     * @param y the target y in question
     * @param x0 x coordinate of the first mark
     * @param y0 y coordinate of the first mark
     * @param x1 x coordinate of the second mark
     * @param y1 y coordinate of hte second mark
     * @return "Left" if the point is left of the line, or "Right" if the point is to the right of (or on) the line
     */
    public static String getOrientation(double x, double y, double x0, double y0, double x1, double y1) {
        double value = (x1 - x0) * (y - y0) - (x - x0) * (y1 - y0);

        if (value > 0) {
            return LEFT;
        } else {
            return RIGHT;
        }
    }
}
