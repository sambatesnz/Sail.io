package seng302.RaceObjects;

import static java.lang.Math.sqrt;

public class BoatCollision {
    int collisionType;
    private Boat collider1;
    private Boat collider2;
    private boolean isColliding;

    public BoatCollision(Boat collider1, Boat collider2){
        this.collider1 = collider1;
        this.collider2 = collider2;
        this.isColliding = false;
    }

    /**
     * Updates the collision data for the boat collision, checking if a collision is occuring
     */
    public void updateCollision(){
        if (collider1.getSourceId() == collider2.getSourceId()){
            isColliding = false;
            return;
            //A boat can't collide with itself!
        }

        double boat1X = collider1.getX();
        double boat1Y = collider1.getY();
        double boat2X = collider2.getX();
        double boat2Y = collider2.getY();

        double xDist = boat1X - boat2X;
        double yDist = boat1Y - boat2Y;

        double distance = sqrt((xDist * xDist) + (yDist * yDist));

        if (distance < (collider1.getSize() + collider2.getSize())) {
            isColliding = true;
        } else {
            isColliding = false;
        }
    }

    /**
     * Checks if the boat is colliding
     * @return
     */
    public boolean isColliding() {
        updateCollision();
        return isColliding;
    }
}


