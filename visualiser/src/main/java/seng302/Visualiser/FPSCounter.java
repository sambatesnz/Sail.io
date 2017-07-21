package seng302.Visualiser;

import javafx.scene.control.Label;

/**
 * Created by sha162 on 9/06/17.
 */
public class FPSCounter {
    private long frameCount = 0;
    private long TIMER_UPDATE = 1000000000;
    private long previousTime = 0;
    private long currentFPS  = 0;

    private Label fpsLabel;

    public FPSCounter(Label fpsLabel) {
        this.fpsLabel = fpsLabel;
    }

    public void update(long currentTime) {
        frameCount++;
        if (currentTime - previousTime >= TIMER_UPDATE) {
            fpsLabel.setText(frameCount + " fps");
            resetFrameCount();
            previousTime = currentTime;
        }
    }
    public long getFrameCount() {
        return frameCount;
    }
    private void resetFrameCount(){
        frameCount = 0;
    }


}
