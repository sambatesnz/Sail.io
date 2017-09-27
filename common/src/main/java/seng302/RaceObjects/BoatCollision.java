package seng302.RaceObjects;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

public class BoatCollision {
    int collisionType;
    GenericBoat collider1;
    GenericBoat collider2;
    boolean isColliding;
    boolean reactedToCollision;

    public BoatCollision(GenericBoat collider1, GenericBoat collider2){
        this.collider1 = collider1;
        this.collider2 = collider2;
        this.isColliding = false;
        reactedToCollision = false;
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
            reactedToCollision = false;
        }
    }

    /**
     * Checks if the boat is colliding
     * @return Whether or not the boat is colliding
     */
    public boolean isColliding() {
        updateCollision();
        return isColliding;
    }

    public GenericBoat getOther(GenericBoat boat){
        return boat == collider1 ? collider2 : collider1;
    }

    public GenericBoat getWinner(){
        if(abs(collider1.getCollisionFactor() - collider2.getCollisionFactor()) < 2)
        {
            System.out.println("EVEN STEVEN");
            return null; //should be null when not testing
        }
        return collider1.getCollisionFactor() < collider2.getCollisionFactor() ? collider2 : collider1;
    }

    public GenericBoat getLoser(){
        if(abs(collider1.getCollisionFactor() - collider2.getCollisionFactor()) < 2)
        {
            System.out.println("EVEN STEVEN2");
            return null; //should be null when not testing
        }
        return collider1.getCollisionFactor() < collider1.getCollisionFactor() ? collider1 : collider2;
    }

    public boolean isReactedToCollision(){
        return reactedToCollision;
    }
    public void setReactedToCollision(boolean bool){
        reactedToCollision = bool;
    }
}


