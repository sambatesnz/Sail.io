package seng302.RaceObjects;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

public class AgarBoatCollision extends BoatCollision {


    public AgarBoatCollision(BoatInterface collider1, BoatInterface collider2) {
        super(collider1, collider2);
    }

    public BoatInterface getWinner(){
        if(abs(getCollisionFactor(collider1) - getCollisionFactor(collider2)) < 2) return collider1; //should be null when not testing
        return getCollisionFactor(collider1) < getCollisionFactor(collider2) ? collider2 : collider1;
    }
    public BoatInterface getLoser(){
        return getCollisionFactor(collider1) > getCollisionFactor(collider2) ? collider2 : collider1;
    }

    private double getCollisionFactor(BoatInterface boat){
        return boat.getAgarSize();
    }
}


