package seng302.RaceObjects;

import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * Represents a mark, gate, starting line, or finishing line in a yacht race
 */
public class CompoundMark {

    private ArrayList<Mark> marks;
    private String name;
    private Color color;
    private int id;
    private String type;
    private String rounding;
    private double longitude;
    private double latitude;

    /**
     * asd
     * @param name Name of CM
     * @param marks The points that the CM consists of
     * @param color Color of the mark
     * @param id the unique id
     * @param type is it a gate or a mark etc
     */
    public CompoundMark(String name, ArrayList<Mark> marks, Color color, int id, String type) {
        this.name = name;
        this.marks = marks;
        this.color = color;
        this.id = id;
        this.type = type;
    }

    /**
     * Getter for the colour of the landmark.
     * @return the color of the landmark
     */
    public Color getColor() {
        return color;
    }

    /**
     * Get the landmarks x coordinate
     * @return the landmarks x coordinate
     */
    public double getX() {
        return marks.stream().mapToDouble(Mark::getX).sum() / marks.size();
    }

    /**
     * Get the landmarks y coordinate
     * @return the landmarks y coordinate
     */
    public double getY() {
        return marks.stream().mapToDouble(Mark::getY).sum() / marks.size();
    }

    public double getXByIndex(int i) {
        return this.marks.get(i).getX();
    }
    
    public double getYByIndex(int i) {
        return this.marks.get(i).getY();
    }

    /**
     * Get the landmarks name
     * @return the landmarks name
     */
    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public ArrayList<Mark> getMarks() {
        return marks;
    }

    public void setRounding(String rounding) {
        this.rounding = rounding;
    }

    public String getRounding() {
        return rounding;
    }

    public double getLongitude() {
        if (marks.size() > 1) {
            //average
            double l1 = marks.get(0).getLongitude();
            double l2 = marks.get(1).getLongitude();
            return (l1+l2)/2;
        }
        return longitude;
    }

    public double getLatitude() {
        if (marks.size() > 1) {
            //average
            double l1 = marks.get(0).getLatitude();
            double l2 = marks.get(1).getLatitude();
            return (l1+l2)/2;
        }
        return latitude;
    }
}
