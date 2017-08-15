package seng302.UserInput;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import seng302.PacketGeneration.BinaryMessage;
import seng302.RaceObjects.Race;
import seng302.UserInputController.BoatAction;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * Key bindings as defined by the SENG302 API Specification
 * Turn key presses in to BoatActionMessages
 * Can only be instantiated once
 */
public class KeyBindingUtility {

    private static boolean sailStatus;
    private static Queue<byte[]> bytes = new LinkedList<>();
    private Race race;


    private KeyBindingUtility(){
        sailStatus = false;
    }

    /**
     * Apples the key bindings to the scene and adds an event listener for key presses
     * @param rootScene the scene you wish to key bind presses too
     */
    public static void setKeyBindings(Scene rootScene, Race race) {

        rootScene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                BinaryMessage boatActionMessage;
                int sourceId = race.getClientSourceId();
                switch (event.getCode()) {
                    case SPACE:
                        boatActionMessage = new BoatActionMessage(BoatAction.VMG, sourceId);
                        break;
                    case SHIFT:
                        if(sailStatus){
                            boatActionMessage = new BoatActionMessage(BoatAction.SAILS_IN, sourceId);
                        } else {
                            boatActionMessage = new BoatActionMessage(BoatAction.SAILS_OUT, sourceId);
                        }
                        alternateSailStatus();
                        break;
                    case ENTER:
                        boatActionMessage = new BoatActionMessage(BoatAction.TACK_OR_GYBE, sourceId);
                        break;
                    case PAGE_UP:
                        boatActionMessage = new BoatActionMessage(BoatAction.UPWIND, sourceId);
                        break;
                    case PAGE_DOWN:
                        boatActionMessage = new BoatActionMessage(BoatAction.DOWNWIND, sourceId);
                        break;
                    default:
                        return;
                }
                event.consume();
                bytes.add(boatActionMessage.createMessage());
            }
        });

    }

    /**
     * Checks the input buffer to see if specific keys have been pressed
     * @return byte[] of any key presses that have been pressed othewise and empty byte array
     */
    public static byte[] getUserInputData() {
        try {
            return bytes.remove();
        } catch (NoSuchElementException e) {
            return new byte[0];
        }
    }

    /**
     * Checks whether any keys have been pressed
     */
    public static boolean keyPressed() {
        return !bytes.isEmpty();
    }

    private static void alternateSailStatus(){
        sailStatus = !sailStatus;
    }

}
