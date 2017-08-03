package seng302.UserInput;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import seng302.PacketGeneration.BinaryMessage;

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


    private KeyBindingUtility(){
        sailStatus = false;
    }

    /**
     * Apples the key bindings to the scene and adds an event listener for key presses
     * @param rootScene the scene you wish to key bind presses too
     */
    public static void setKeyBindings(Scene rootScene) {

        rootScene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                BinaryMessage boatActionMessage;
                switch (event.getCode()) {
                    case SPACE:
                        boatActionMessage = new BoatActionMessage(BoatAction.AUTOPILOT.getBoatAction());
                        break;
                    case SHIFT:
                        if(sailStatus){
                            boatActionMessage = new BoatActionMessage(BoatAction.SAILS_IN.getBoatAction());
                        } else {
                            boatActionMessage = new BoatActionMessage(BoatAction.SAILS_OUT.getBoatAction());
                        }
                        alternateSailStatus();
                        break;
                    case ENTER:
                        boatActionMessage = new BoatActionMessage(BoatAction.TACK_OR_GYBE.getBoatAction());
                        break;
                    case PAGE_UP:
                        boatActionMessage = new BoatActionMessage(BoatAction.UPWIND.getBoatAction());
                        break;
                    case PAGE_DOWN:
                        boatActionMessage = new BoatActionMessage(BoatAction.DOWNWIND.getBoatAction());
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
