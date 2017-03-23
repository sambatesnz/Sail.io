package seng302.controller;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import seng302.Model.CompoundMark;

import java.util.ArrayList;

/**
 * Created by osr13 on 24/03/17.
 */
public class CanvasController {
    @FXML Canvas mainCanvas;

    public int convertLatitudeToX(double lat) {
        int x =  (int) ((mainCanvas.getWidth()/360.0) * (180 + lat));
        return x;
    }

    public int convertLongitudeToY(double longitude) {
        int y =  (int) ((mainCanvas.getHeight()/180.0) * (90 - longitude));
        return y;
    }

    public class XYPoint {
        public int x;
        public int y;

        public XYPoint(int x, int y){
            this.x = x;
            this.y = y;
        }
    }

    public ArrayList<XYPoint> convertLatLongtoXY(ArrayList<CompoundMark> raceMarks) {
        ArrayList<XYPoint> compoundMarksXY = new ArrayList<>();
        for (CompoundMark mark : raceMarks) {
            for (int i = 0; i < mark.getCompoundMarks().size(); i++) {
                int x = convertLatitudeToX(mark.getCompoundMarks().get(i).getLatitude());
                int y = convertLongitudeToY(mark.getCompoundMarks().get(i).getLongitude());

                compoundMarksXY.add(new XYPoint(x, y));
            }
        }
        System.out.println(compoundMarksXY);
        return compoundMarksXY;
    }
}
