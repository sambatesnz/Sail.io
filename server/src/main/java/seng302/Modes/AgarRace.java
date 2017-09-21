package seng302.Modes;

import seng302.DataGeneration.IServerData;
import seng302.DataGenerator;
import seng302.LocationSpawner;
import seng302.PacketGeneration.BinaryMessage;
import seng302.PacketGeneration.YachtEventGeneration.YachtEventMessage;
import seng302.PacketGeneration.YachtEventGeneration.YachtIncidentEvent;
import seng302.PacketParsing.XMLParser;
import seng302.RaceObjects.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.currentTimeMillis;

/**
 * Extension of base race
 */
public class AgarRace extends Race {

    private static final int AGAR_SIZE_DECREMENT = 1;
    private static final int MINIMUM_AGAR_SIZE = 0;
    private static final int SIZE_DECREASE_TICK_MS = 50;

    AgarRace() {
        super();
    }


    @Override
    public void parseCourseXML(String fileName){

        try {
            DataGenerator dataGenerator = new DataGenerator();
            String xmlString = dataGenerator.loadFile(fileName);
            XMLParser xmlParser = new XMLParser(xmlString);
            compoundMarks = new ArrayList<>();
            boundaries = xmlParser.getCourseLimits();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void checkCollisions(IServerData raceManager){
        for (GenericBoat boat : getBoats()) {
            BoatCollision collision = collisionDetector.checkBoatCollision(boat, boats, collisionMap);
            if (collision != null) {
                BinaryMessage boatCollisionEventMessage = new YachtEventMessage(
                        boat.getSourceId(), YachtIncidentEvent.BOATCOLLISION
                );

                if (!collision.isReactedToCollision()) {
                    GenericBoat winner = collision.getWinner();
                    GenericBoat loser = collision.getOther(winner);

                    System.out.println("Collision Occurred!");
                    System.out.println("Winner: " + winner + " Loser: " + loser);
                    System.out.println("Winners old size: " + winner.getAgarSize());

                    winner.setAgarSize(winner.getAgarSize() + loser.getAgarSize());
                    collision.setReactedToCollision(true);
                    killBoat(loser);

                    System.out.println("Winners new size: " + winner.getAgarSize());

                }

                raceManager.addMessage(boatCollisionEventMessage.createMessage());
            }

            if (collisionDetector.checkMarkCollisions(boat, getCompoundMarks()) || !collisionDetector.checkWithinBoundary(boat, getBoundaries())) {
                BinaryMessage markCollisionEventMessage = new YachtEventMessage(
                        boat.getSourceId(), YachtIncidentEvent.MARKCOLLISION
                );
                reduceBoatSize(boat);

                raceManager.addMessage(markCollisionEventMessage.createMessage());
            }
        }
    }

    private void killBoat(GenericBoat loser) {
        loser.loseLife();
        loser.setSailsOut(false);
        List<GenericBoat> boats = new ArrayList<>();
        boats.add(loser);
        LocationSpawner.generateSpawnPoints(boats, super.getBoundaries(), collisionDetector, collisionMap);
    }

    private void reduceBoatSize(GenericBoat boat) {
        if (currentTimeMillis() - boat.getLastAgarSizeDecreaseTime() > SIZE_DECREASE_TICK_MS) {
            boat.setAgarSize(boat.getAgarSize() - AGAR_SIZE_DECREMENT);
            if (boat.getAgarSize() <= MINIMUM_AGAR_SIZE) {
                killBoat(boat);
            }
            boat.setLastAgarSizeDecreaseTime(currentTimeMillis());
        }
    }

    @Override
    public GenericBoat addBoat(int clientSocketSourceID) throws Exception {
        if (boats.size() < MAX_NUMBER_OF_BOATS){
            BoatDecorator boat = new AgarBoat(boatGenerator.generateBoat());
            clientIDs.put(clientSocketSourceID, boat.getSourceId());
            boats.add(boat);
            LocationSpawner.generateSpawnPoints(boats, super.boundaries, collisionDetector, super.collisionMap);
            return boat;
        } else {
            throw new Exception("cannot create boat");
        }
    }
}
