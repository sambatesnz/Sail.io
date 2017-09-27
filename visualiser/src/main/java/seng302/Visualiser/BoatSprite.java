package seng302.Visualiser;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polyline;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import seng302.RaceObjects.GenericBoat;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class BoatSprite {
    private Pane stack;
    private GenericBoat boatObject;
    private Polyline wake;
    private Text text;
    private Circle tc;
    private Circle controlCircle;
    private ImageView sail;
    private Image sailIn;
    private Image sailOut;


    public static final int CONTROL_CIRCLE = 0;
    public static final int IMAGE = 1;
    public static final int WAKE = 2;
    public static final int DOT = 3;
    public static final int SAIL = 4;
    public static final int TEXT = 5;


    public BoatSprite(GenericBoat boat, int clientSourceId){
        this.boatObject = boat;
        initialiseWake();
        text = new Text();
        tc = new Circle(2);
        tc.setCenterX(0);
        tc.setCenterY(0);
        initialiseSail();

        stack = new Pane();
        initialiseControlCircle(clientSourceId);
        addBoatImage();
        stack.getChildren().add(wake);
        stack.getChildren().add(tc);
        stack.getChildren().add(sail);
        stack.getChildren().add(text);
    }

    private void addBoatImage() {
        try {
            BufferedImage bi = ImageIO.read(Thread.currentThread().getContextClassLoader().getResourceAsStream("boat.png"));
            Color colour = boatObject.getColour();
            bi = (new ImageColourChanger(bi)).colouredImage(new int[] {
                    (int) colour.getRed()*255,
                    (int) colour.getGreen()*255,
                    (int) colour.getBlue()*255,
                    255});

            ImageView iv = new ImageView();
            iv.setImage(SwingFXUtils.toFXImage(bi, null));
            iv.setTranslateX(-bi.getWidth()/2);
            iv.setTranslateY(-bi.getHeight()/2);
            stack.getChildren().add(iv);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns a reference to the stack of the boat
     * @return the stack
     */
    public Pane getStack() {
        return stack;
    }

    public GenericBoat getBoat(){
        return boatObject;
    }

    private void initialiseWake(){
        wake = new Polyline();
        wake.getPoints().addAll(0.0, 0.0,
                                0.0, 1.0);
        wake.setStroke(Color.CYAN);
    }

    private void initialiseControlCircle(int clientSourceId){
        if (clientSourceId == boatObject.getSourceId()) {
            controlCircle = new Circle(10);
            controlCircle.setCenterX(0);
            controlCircle.setCenterY(0);
            controlCircle.setStroke(Color.INDIANRED);
            controlCircle.setFill(Color.TRANSPARENT);
        } else {
            controlCircle = new Circle(0);
        }
        stack.getChildren().add(controlCircle);
    }

    private void initialiseSail(){

        sailOut = new Image(Thread.currentThread().getContextClassLoader().getResourceAsStream("sail1.png"));
        sailIn = new Image(Thread.currentThread().getContextClassLoader().getResourceAsStream("sailIn.png"));

        sail = new ImageView(sailOut);
        sail.setTranslateX(-sailOut.getWidth()/2);
        sail.setTranslateY(-sailOut.getHeight()/2);
    }

    /**
     * Redraws the sail to be luffing
     */
    public void sailIn(){
        sail.setImage(sailIn);
    }

    /**
     * Redraws the sail to be powered
     */
    public void sailOut() {
        sail.setImage(sailOut);
    }

    /**
     * Sets the control circle fill colour to be red and opaque
     */
    public void collisionHighlight() {
        controlCircle.setFill(Color.INDIANRED);
        controlCircle.setOpacity(0.5);
    }

    /**
     * Removes the control circle fill colour, making it transparent
     */
    public void removeCollisionHighlight() {
        controlCircle.setOpacity(1.0);
        controlCircle.setFill(Color.TRANSPARENT);
    }
}

