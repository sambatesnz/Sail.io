package seng302.RaceObjects;

import static java.lang.Math.abs;

public class AgarBoatCollision extends BoatCollision {


    public AgarBoatCollision(GenericBoat collider1, GenericBoat collider2) {
        super(collider1, collider2);
    }

    public GenericBoat getWinner(){
        if(abs(getCollisionFactor(collider1) - getCollisionFactor(collider2)) < 2) return collider1; //should be null when not testing
        return getCollisionFactor(collider1) < getCollisionFactor(collider2) ? collider2 : collider1;
    }
    public GenericBoat getLoser(){
        return getCollisionFactor(collider1) > getCollisionFactor(collider2) ? collider2 : collider1;
    }

    private double getCollisionFactor(GenericBoat boat){
        return boat.getAgarSize();
    }
}


