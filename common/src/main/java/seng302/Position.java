package seng302;

/**
 * Class to hold a position (set of coordinates)
 */
public class Position {
    private static double EARTH_CIRCUMFERENCE = 40075000;
    private double latitude;
    private double longitude;
    private double x;
    private double y;

    /**
     * Generates X and Y positions on meters based on the Latitude and Longitude
     * @param longitude longitude of the position
     * @param latitude latitude of the position
     */
    public Position(double latitude, double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
        this.x = longitude * EARTH_CIRCUMFERENCE / 360;
        this.y = latitude * EARTH_CIRCUMFERENCE / 180;
    }

    public static double convertX(double x){
        return x / EARTH_CIRCUMFERENCE * 360;
    }
    public static double convertY(double y){
        return y / EARTH_CIRCUMFERENCE * 180;
    }

//    public Position (int integer, double x, double y){
//        this.latitude = y / EARTH_CIRCUMFERENCE * 180;
//        this.longitude = x / EARTH_CIRCUMFERENCE * 360;
//        this.x = x;
//        this.y = y;
//    }

    /**
     * Getter for the latitude.
     * @return the latitude as a double.
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Getter for the longitude.
     * @return the longitude.
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Getter for the x position.
     * @return the x position.
     */
    public double getX() {
        return x;
    }

    /**
     * Getter for the y position
     * @return the y position
     */
    public double getY() {
        return y;
    }

    /**
     * Sets x and the longitude from x
     * @param x the value used to set
     */
    public void setX(double x) {
        this.x = x;
        this.longitude = x / EARTH_CIRCUMFERENCE * 360;
    }

    /**
     * Sets y and the latitude from y
     * @param y the value used to set
     */
    public void setY(double y) {
        this.y = y;
        this.latitude= y / EARTH_CIRCUMFERENCE * 180;
    }
}
