package seng302.UserInputController;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import seng302.PacketGeneration.BinaryMessage;


import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * Created by tjg73 on 13/07/17.
 */
public class KeyBindingUtility {

    private static boolean sailStatus;
    private static Queue<byte[]> bytes = new LinkedList<>();


    private KeyBindingUtility(){
        sailStatus = false;
    }

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
                        }else {
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
                System.out.println(event.getCode().getName());
                event.consume();
                bytes.add(boatActionMessage.createMessage());
            }
        });

    }

    public static byte[] getUserInputData() {
        try {
            return bytes.remove();
        } catch (NoSuchElementException e) {
            return new byte[0];
        }
    }

    public static boolean keyPressed() {
        return !bytes.isEmpty();
    }

    private static void alternateSailStatus(){
        sailStatus = !sailStatus;
    }

}
