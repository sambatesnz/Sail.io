package seng302.Modes;

import seng302.DataGeneration.IServerData;
import seng302.LocationSpawner;
import seng302.PacketGeneration.BinaryMessage;
import seng302.PacketGeneration.YachtEventGeneration.YachtEventMessage;
import seng302.PacketGeneration.YachtEventGeneration.YachtIncidentEvent;
import seng302.RaceObjects.AgarBoat;
import seng302.RaceObjects.AgarBoatCollision;
import seng302.RaceObjects.Boat;
import seng302.RaceObjects.BoatDecorator;
import seng302.RaceObjects.BoatInterface;
import seng302.RaceObjects.BoatCollision;

import java.util.ArrayList;
import java.util.List;

/**
 * Extension of base race
 */
public class AgarRace extends Race {

    AgarRace() {
        super();
    }

    @Override
    public void checkCollisions(IServerData raceManager){
        for (BoatInterface boat : getBoats()) {
            BoatCollision collision = collisionDetector.checkBoatCollision(boat);
            if (null != collision) {
                BinaryMessage boatCollisionEventMessage = new YachtEventMessage(
                        boat.getSourceId(), YachtIncidentEvent.BOATCOLLISION
                );

                if (!collision.isReactedToCollision()) {
                    collision.getWinner().setAgarSize(collision.getWinner().getAgarSize() + collision.getLoser().getAgarSize());
                    System.out.println(collision.getWinner().getAgarSize() + collision.getLoser().getAgarSize());
                    collision.setReactedToCollision(true);
                }
                killBoat(collision.getLoser());

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

    private void killBoat(BoatInterface loser) {
        loser.setSailsOut(false);
        List<BoatInterface> boats = new ArrayList<>();
        boats.add(loser);
        LocationSpawner.generateSpawnPoints(boats, super.getBoundaries(), collisionDetector);
    }

    @Override
    public BoatInterface addBoat(int clientSocketSourceID) throws Exception {
        if (boats.size() < MAX_NUMBER_OF_BOATS){
            BoatDecorator boat = new AgarBoat(boatGenerator.generateBoat());
            clientIDs.put(clientSocketSourceID, boat.getSourceId());
            boats.add(boat);
            LocationSpawner.generateSpawnPoints(boats, super.getBoundaries(), collisionDetector);
            return boat;
        } else {
            throw new Exception("cannot create boat");
        }
    }
}
