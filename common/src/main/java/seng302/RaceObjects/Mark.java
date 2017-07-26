package seng302.RaceObjects;

/**
 * Class to hold a position (set of coordinates)
 */
public class Mark {
    private static double EARTH_CIRCUMFERENCE = 40075000;
    private double latitude;
    private double longitude;
    private double x;
    private double y;
    private int sourceId;

    public Mark(){}
    /**
     * Generates X and Y positions on meters based on the Latitude and Longitude
     * @param longitude longitude of the position
     * @param latitude latitude of the position
     */
    public Mark(double latitude, double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
        this.x = convertToX(longitude);
        this.y = convertToY(latitude);
    }

    public Mark(double latitude, double longitude, int sourceId) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.x = convertToX(longitude);
        this.y = convertToY(latitude);
        this.sourceId = sourceId;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
        this.y = convertToY(latitude);
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
        this.x = convertToX(longitude);
    }

    public Mark getCopy(){
        return new Mark(this.latitude, this.longitude);
    }

    /**
     * Converts a longitude to x
     * @param lon Longitude to convert
     * @return converted x value
     */
    private double convertToX(double lon){
        return lon * EARTH_CIRCUMFERENCE / 360;
    }

    /**
     * Converts a latitude to y
     * @param lat Longitude to convert
     * @return converted y value
     */
    private double convertToY(double lat){
        return lat * EARTH_CIRCUMFERENCE / 180;
    }

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

    public void setX(double x) {
        this.x = x;
        this.longitude = x * 360 / EARTH_CIRCUMFERENCE;
    }
//  x = lon * EARTH_CIRCUMFERENCE / 360;
    public void setY(double y) {
        this.y = y;
        this.latitude = y * 180 / EARTH_CIRCUMFERENCE;
    }

    public int getSourceId() {
        return sourceId;
    }

    public void setSourceId(int sourceId) {
        this.sourceId = sourceId;
    }
}
