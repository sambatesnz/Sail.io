package seng302.RaceObjects;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

public class BoatCollision {
    int collisionType;
    private BoatInterface collider1;
    private BoatInterface collider2;
    private boolean isColliding;

    public BoatCollision(BoatInterface collider1, BoatInterface collider2){
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

    public Boat getWinner(){
        if(abs(getCollisionFactor(collider1) - getCollisionFactor(collider2)) < 2)
        {
            System.out.println("EVEN STEVEN");
            return collider1; //should be null when not testing
        }
        return getCollisionFactor(collider1) < getCollisionFactor(collider2) ? collider2 : collider1;
    }
    public Boat getLoser(){
        if(abs(getCollisionFactor(collider1) - getCollisionFactor(collider2)) < 2)
        {
            System.out.println("EVEN STEVEN2");
            return collider2; //should be null when not testing
        }
        return getCollisionFactor(collider1) < getCollisionFactor(collider2) ? collider1 : collider2;
    }

    private double getCollisionFactor(Boat boat){
        return boat.getAgarSize();
    }

}


