package seng302.Modes;

import javafx.util.Pair;
import seng302.DataGeneration.IServerData;
import seng302.DataGenerator;
import seng302.LocationSpawner;
import seng302.PacketGeneration.BinaryMessage;
import seng302.PacketGeneration.YachtEventGeneration.YachtEventMessage;
import seng302.PacketGeneration.YachtEventGeneration.YachtIncidentEvent;
import seng302.PacketParsing.XMLParser;
import seng302.RaceObjects.*;
import seng302.Rounding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Extension of base race
 */
public class AgarRace extends Race {

    AgarRace() {
        super();
    }


    @Override
    public void parseCourseXML(String fileName){
        System.out.println("++++++++++++++++++++++++++++++++");

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
