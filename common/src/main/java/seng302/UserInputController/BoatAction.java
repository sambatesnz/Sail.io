package seng302.UserInputController;

/**
 * Enum for Boat Actions
 */
public enum BoatAction {

    AUTOPILOT(1),
    SAILS_IN(2),
    SAILS_OUT(3),
    TACK_OR_GYBE(4),
    UPWIND(5),
    DOWNWIND(6);

    private int boatAction;

    BoatAction(int boatAction){
        this.boatAction = boatAction;
    }

    public int getBoatAction(){
        return this.boatAction;
    }
}
