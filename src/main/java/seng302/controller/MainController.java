package seng302.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Screen;
import seng302.App;
import seng302.Model.*;

import javax.imageio.ImageIO;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by Justin on 15-Mar-17.
 */
public class MainController {
    private Course raceCourse;
    private Race mainRace;
    private HashMap<String, Text> annoText;
    private ObservableList<Boat> boats;

    @FXML private Canvas mainCanvas;
    @FXML private GridPane boatGridPane;
    @FXML private Group raceGroup;
    @FXML private Text windDirText;
    @FXML private TableColumn<BoatOrder, Integer> positionsColumn;
    @FXML private TableView<BoatOrder> PositionsTable;

    @FXML private TableView<Boat> boatInfoTableView;
    @FXML private TableColumn<Boat, String> boatNameColumn;
    @FXML private TableColumn<Boat, Integer> boatSpeedColumn;


    public void initialize() throws IOException {

        ObservableList<BoatOrder> boatOrder = FXCollections.observableArrayList();
        for (int i=0; i<6; i++){
            BoatOrder b = new BoatOrder(i+1);
            boatOrder.add(b);
        }

        positionsColumn.setCellValueFactory( new PropertyValueFactory<BoatOrder, Integer>("position"));
        PositionsTable.setItems(boatOrder);
        positionsColumn.prefWidthProperty().bind(PositionsTable.widthProperty().multiply(0.97));

        raceCourse = new Course("Americas Cup Race");
        mainCanvas.setHeight(Screen.getPrimary().getVisualBounds().getHeight());
        mainCanvas.setWidth(Screen.getPrimary().getVisualBounds().getWidth() * 0.8);

        ArrayList<XYPoint> courseXY = convertLatLongToXY();
        displayMarks(courseXY);

        displayWindDir(raceCourse.getWindDirection());

        mainRace = new Race(raceGroup, raceCourse, mainCanvas);
        mainRace.passMainController(this);
        mainRace.setRaceSpeed();
        mainRace.raceSetup();

        setUpAnno();

        updateAnnoPos(mainRace.getRacingBoats().get(0), 500, 500);

        boats = mainRace.getRacingBoats();

        boatNameColumn.setCellValueFactory(new PropertyValueFactory<Boat, String>("boatName"));
        boatSpeedColumn.setCellValueFactory(new PropertyValueFactory<Boat, Integer>("boatSpeed"));
        boatInfoTableView.setItems(boats);
        boatNameColumn.prefWidthProperty().bind(boatInfoTableView.widthProperty().multiply(0.69));
        boatSpeedColumn.prefWidthProperty().bind(boatInfoTableView.widthProperty().multiply(0.3));

        RaceAnimationTimer animation = new RaceAnimationTimer(mainRace);
        animation.start();

    }

    public void setUpAnno() {
        annoText = new HashMap<>();
         for (Boat boat : mainRace.getRacingBoats()) {
             String boatInfo = boat.getShorthandName() + ", " + boat.getBoatSpeed() + "kmph";
             Text boatText = new Text(400, 400, boatInfo);
             annoText.put(boat.getBoatName(), boatText);
             raceGroup.getChildren().add(boatText);
        }
    }

    public void updateAnnoPos(Boat boat, double posX, double posY) {
        if(!boat.hasFinished) {
            Text boatAnnotation = annoText.get(boat.getBoatName());
            boatAnnotation.setX(posX);
            boatAnnotation.setY(posY);
        }else{
            for(Boat observableBoat : boats){
                if (boat.getBoatName().equals(observableBoat.getBoatName())){
                    observableBoat.setBoatName(boat.getBoatName() + " (Finished)");
                    boatInfoTableView.refresh();
                }
            }
        }
    }

    public void displayWindDir(int windDir) throws IOException {
        windDirText.setText("Wind Direction\nBearing: " + windDir);

        try {
            //File g = new File("/home/cosc/student/sha162/Documents/team-4/arrow.png");
//            InputStream url = App.class.getClassLoader().getResourceAsStream(
//                    "transparent_wind_arrow");
            InputStream url = App.class.getClassLoader().getResourceAsStream("transparent_wind_arrow.png");
            Image image = new Image(url);
            GraphicsContext gc = mainCanvas.getGraphicsContext2D();

            gc.save();
            Rotate r = new Rotate(windDir, 50+image.getWidth()/2, 50+image.getHeight()/2);
            gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
            gc.drawImage(image, 50, 50);
            gc.restore();
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    public void displayMarks(ArrayList<XYPoint> courseXY) {
        XYPoint gateStart = null;
        XYPoint gateEnd;
        boolean flag = false;
        Line line;

        GraphicsContext gc = mainCanvas.getGraphicsContext2D();
        gc.setFill(Color.LIGHTCYAN);
        gc.fillRect(0,0, mainCanvas.getWidth(), mainCanvas.getHeight());

        for (XYPoint point : courseXY) {
            Rectangle r = new Rectangle(point.x-7.5, point.y-7.5, 15, 15);
            r.setFill(Color.BLACK);
            raceGroup.getChildren().add(r);
        }
        try {
            for (XYPoint point : courseXY) {
                if (point.name.startsWith("Start") || point.name.startsWith("Finish") || point.name.endsWith("Gate")) {

                    if (flag == false) {
                        gateStart = point;
                        flag = true;
                    } else if (flag == true) {
                        gateEnd = point;
                        //gc.strokeLine(gateStart.x, gateStart.y, gateEnd.x, gateEnd.y);
                        line = new Line(gateStart.x, gateStart.y, gateEnd.x, gateEnd.y);
                        line.getStrokeDashArray().add(10d);
                        line.setStrokeWidth(3);
                        if (point.name.startsWith("Start")) {
                            line.setStroke(Color.GREEN);
                        } else if (point.name.startsWith("Finish")) {
                            line.setStroke(Color.RED);
                        } else if (point.name.endsWith("Gate")) {
                            line.setStroke(Color.BLUE);
                        }
                        raceGroup.getChildren().add(line);
                        flag = false;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    private ArrayList<Double> getCanvasBounds(){
        double minXBound = 50;
        double minYBound = 50;
        double maxXBound = mainCanvas.getWidth() - 100;
        double maxYBound = mainCanvas.getHeight() - 100;

        ArrayList<Double> canvasBounds = new ArrayList<>();
        canvasBounds.add(minXBound);
        canvasBounds.add(maxXBound);
        canvasBounds.add(minYBound);
        canvasBounds.add(maxYBound);
        return canvasBounds;
    }


    public ArrayList<XYPoint> convertLatLongToXY() {
        ArrayList<XYPoint> compoundMarksXY = new ArrayList<>();
        ArrayList<Double> latLongBounds = raceCourse.findMaxMinLatLong();
        ArrayList<Double> canvasBounds = getCanvasBounds();
        double deltaLat = Math.abs((latLongBounds.get(1) - latLongBounds.get(0)));
        double deltaLong = Math.abs((latLongBounds.get(3) - latLongBounds.get(2)));

        for (CompoundMark mark : raceCourse.getCourseCompoundMarks()) {
            for (int i = 0; i < mark.getCompoundMarks().size(); i++){
                double markLat = mark.getCompoundMarks().get(i).getLatitude();
                double markLong = mark.getCompoundMarks().get(i).getLongitude();

                double maxWidth = canvasBounds.get(1);
                double maxHeight = canvasBounds.get(3);

                double minLat = latLongBounds.get(0);
                double minLong = latLongBounds.get(2);

                double x = maxWidth*(markLong - minLong)/deltaLong;
                double y = maxHeight - (maxHeight*(markLat - minLat)/deltaLat);

                XYPoint newPoint = new XYPoint(x+50, y+50, mark.getName());
                compoundMarksXY.add(newPoint);
            }
        }
        return compoundMarksXY;
    }
}

