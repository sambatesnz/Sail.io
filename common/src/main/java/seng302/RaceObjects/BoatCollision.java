package seng302.RaceObjects;

import static java.lang.Math.sqrt;

public class BoatCollision {
    int collisionType;
    private Boat collider1;
    private Boat collider2;
    private boolean isColliding;

    private void Collision(Boat collider1, Boat collider2){
        this.collider1 = collider1;
        this.collider2 = collider2;
    }
    
    public void updateCollision(){
        double boat1X = collider1.getX();
        double boat1Y = collider1.getY();
        double boat2X = collider2.getX();
        double boat2Y = collider2.getY();

        double xDist = boat1X - boat2X;
        double yDist = boat1Y - boat2Y;

        double distance = sqrt((xDist * xDist) + (yDist * yDist));

        if (distance < (collider1.getSize() + collider2.getSize())) {
            isColliding = true;
        }else{
            isColliding = false;
        }
        
    }
    
    public boolean isColliding() {
        updateCollision();
        return isColliding;
    }
}


