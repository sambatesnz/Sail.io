package seng302.Modes;

import seng302.DataGeneration.IServerData;
import seng302.PacketGeneration.BinaryMessage;
import seng302.PacketGeneration.YachtEventGeneration.YachtEventMessage;
import seng302.PacketGeneration.YachtEventGeneration.YachtIncidentEvent;
import seng302.RaceObjects.Boat;

/**
 * Extension of base race
 */
public class AgarRace extends Race {

    AgarRace() {
        super();
    }

    @Override
    public void checkCollisions(IServerData raceManager){
        for (Boat boat : getBoats()) {
            if (collisionDetector.checkBoatCollision(boat)) {
                BinaryMessage boatCollisionEventMessage = new YachtEventMessage(
                        boat.getSourceId(), YachtIncidentEvent.BOATCOLLISION
                );
                boat.setAgarSize(boat.getAgarSize() + 1);
                raceManager.addMessage(boatCollisionEventMessage.createMessage());
            }

            if (collisionDetector.checkMarkCollisions(boat, getCompoundMarks()) || !collisionDetector.checkWithinBoundary(boat, getBoundaries())) {
                BinaryMessage markCollisionEventMessage = new YachtEventMessage(
                        boat.getSourceId(), YachtIncidentEvent.MARKCOLLISION
                );
                raceManager.addMessage(markCollisionEventMessage.createMessage());
            }
        }
    }
}
