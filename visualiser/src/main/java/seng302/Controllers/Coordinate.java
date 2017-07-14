package seng302.Controllers;

import seng302.Race.Mark;

/**
 * Holds information relating to the coordinates within the window.
 * Contains methods that get relative Marks so that items are scaled when the window is resized.
 * Created by Matt Simpson on 20/03/2017.
 */
public final class  Coordinate {

    //Window resolution
    private static double windowWidthX;
    private static double windowHeightY;

    /**
     * Number of pixels around the edge of the window to not use for the race.
     */
    private static double derivedBorderWidthX = 0;
    private static double derivedBorderHeightY = 0;

    private static Mark viewMin;
    private static Mark viewMax;
    private static double zoom = 0; //0 is default ratio of zoom
    private static Mark offset = new Mark(0, 0);
    private static Mark defaultCourseMin;
    private static Mark defaultCourseMax;
    private static Mark center;

    private static boolean trackingBoat = false;

    private Coordinate(){}

    /**
     * Set the coordinates of the minimum postion on the map to be displayed
     * @param min A minimum coordinate to be viewed
     */
    public static void setDefaultCourseMin(Mark min) {
        defaultCourseMin = min;
    }

    public static void updateViewCoordinates(){
        double zoomOffsetX = zoom*((defaultCourseMax.getX() - center.getX()));
        double zoomOffsetY = zoom*((defaultCourseMax.getY() - center.getY()));
        viewMin.setX(defaultCourseMin.getX() + offset.getX() - zoomOffsetX);
        viewMin.setY(defaultCourseMin.getY() + offset.getY() - zoomOffsetY);
        viewMax.setX(defaultCourseMax.getX() + offset.getX() + zoomOffsetX);
        viewMax.setY(defaultCourseMax.getY() + offset.getY() + zoomOffsetY);
    }

    /**
     * Updates the border size within the window
     */
    public static void updateBorder(){
        double raceWidthX = viewMax.getX() - viewMin.getX();
        double raceHeightY = viewMax.getY() - viewMin.getY();

        if ((windowWidthX / raceWidthX) > windowHeightY / raceHeightY ) {
            derivedBorderWidthX = (windowWidthX - windowHeightY / raceHeightY * raceWidthX) / 2;
        }else if((windowWidthX / raceWidthX) < windowHeightY / raceHeightY ) {
            derivedBorderHeightY = (windowHeightY - windowWidthX / raceWidthX * raceHeightY) / 2;
        }
    }


    /**
     * Zooming in decreases zoom value until a maximum zoom is reached
     */
    public static void increaseZoom(){
        double MAX_ZOOM = -0.9;
        if (zoom > MAX_ZOOM) {
            zoom -= 0.05;
        }
    }

    /**
     * Zooming out increases zoom value until a minimum zoom is reached
     */
    public static void decreaseZoom(){
        double MIN_ZOOM = -0.05;
        if (zoom < MIN_ZOOM) {
            zoom += 0.05;
        }
    }

    public static void setTrackingBoat(boolean value){
        trackingBoat = value;
    }

    public static boolean isTrackingBoat(){
        return trackingBoat;
    }

    /**
     * Converts the y value handed in to an a value relative to the width of the window.
     * @param standardY The y value to be converted to a Y that is relative to how big the window is
     * @return the absolute y value on the window relative to the y value passed in.
     */
    public static double getRelativeY(double standardY){
        return windowHeightY - (windowHeightY - 2 * derivedBorderHeightY) * (standardY - viewMin.getY()) /
                (viewMax.getY() - viewMin.getY()) - derivedBorderHeightY;
    }

    /**
     * Converts the x value handed in to an a value relative to the height of the window.
     * @param standardX The x value to be converted to an x that is relative to how big the window is
     * @return the absolute x value on the window relative to the x value passed in.
     */
    public static double getRelativeX(double standardX){
        return (windowWidthX - 2 * derivedBorderWidthX) * (standardX - viewMin.getX()) /
                (viewMax.getX() - viewMin.getX()) + derivedBorderWidthX;
    }


    /**
     * If needing to reset zoom set this value to 0
     * @param zoom used to set the current zoom
     */
    public static void setZoom(double zoom) {
        Coordinate.zoom = zoom;
    }

    public static void setCenter(Mark center) {
        Coordinate.center = center;
    }

    public static void setOffset(Mark offset) {
        Coordinate.offset = offset;
    }

    public static void setViewMin(Mark viewMin) {
        Coordinate.viewMin = viewMin;
    }

    public static void setViewMax(Mark viewMax) {
        Coordinate.viewMax = viewMax;
    }

    /**
     * Set the coordinates of the maximum Mark on the map to be displayed
     * @param max A maximum coordinate to be viewed
     */
    public static void setDefaultCourseMax(Mark max) {
        defaultCourseMax = max;
    }


    /**
     * @return the current zoom level
     */
    public static double getZoom() {
        return zoom;
    }

    /**
     * Sets the width of the race window
     * @param x pixel width of the race window
     */
    public static void setWindowWidthX(double x) {
        int SIDE_PANE_WIDTH = 248;
        windowWidthX = x - SIDE_PANE_WIDTH;
    }

    /**
     * Sets the height of the race window
     * @param y pixel height of the race window
     */
    public static void setWindowHeightY(double y) {
        windowHeightY = y;
    }

    /**
     * Getter for the width of the window
     * @return the pixel value of the window width
     */
    public static double getWindowWidthX() {
        return windowWidthX;
    }

    /**
     * Getter for the height of the window
     * @return the pixel value of the window height
     */
    public static double getWindowHeightY() {
        return windowHeightY;
    }

}
