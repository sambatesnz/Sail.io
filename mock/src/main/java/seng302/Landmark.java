package seng302;

import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * Represents a mark, gate, starting line, or finishing line in a yacht race
 */
public class Landmark {

    private ArrayList<Position> positions;
    private String name;
    private Color color;
    private int id;
    private String type;

    /**
     * Constructs a landmark
     * @param name the name of the landmark
     * @param positions the position of the landmark in lat/long
     * @param color the color that the landmark appears on the map.
     */
    public Landmark(String name, ArrayList<Position> positions, Color color, int id, String type) {
        this.name = name;
        this.positions = positions;
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

    public double getXByIndex(int i) {
        return this.positions.get(i).getX();
    }

    public double getYByIndex(int i) {
        return this.positions.get(i).getY();
    }

    /**
     * Get the landmarks x coordinate
     * @return the landmarks x coordinate
     */
    public double getX() {
        return this.positions.get(0).getX();
    }
    /**
     * Get the landmarks y coordinate
     * @return the landmarks y coordinate
     */
    public double getY() {
        return this.positions.get(0).getY();
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

    public ArrayList<Position> getPositions() {
        return positions;
    }
}
