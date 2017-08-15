package seng302.DataGeneration;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import seng302.Controllers.StartController;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Manages the race
 */
public class PracticeRaceManager extends RaceManager implements IServerData {

    private Timer timer = new Timer();

    public PracticeRaceManager(){
        super();
    }

    @Override
    public void beginGeneratingData() {
        timer.schedule(new XMLSender(), 0, 2000);
        timer.schedule(new RSMSender(), 100, 500);
        timer.schedule(new BoatPosSender(), 1000, 17);
        timer.schedule(new RaceRunner(), 2000, 17);
        timer.schedule(new ExitRace(), 15000);
    }

    class ExitRace extends TimerTask {
        @Override
        public void run() {
            // SHUTDOWN SERVER
            System.exit(0);
        }
    }

}
