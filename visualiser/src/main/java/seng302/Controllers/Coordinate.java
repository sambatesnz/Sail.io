package seng302.Controllers;

import seng302.Mark;

/**
 * Holds information relating to the coordinates within the window.
 * Contains methods that get relative positions so that items are scaled when the window is resized.
 * Created by Matt Simpson on 20/03/2017.
 */
public class Coordinate {

    private static double windowX;
    private static double windowY;

    /**
     * Number of pixels around the ede of the window to not use for the race.
     */
    private static double BorderX = 0;
    private static double BorderY = 0;
    private static double BorderConstant = 0;
    private static int sidePaneWidth = 248;

    private static Mark viewMin;
    private static Mark viewMax;

    /**
     * Set the coordinates of the minimum postion on the map to be displayed
     * @param min A minimum coordinate to be viewed
     */
    public static void setViewMin(Mark min) {
        viewMin = min;
    }

    /**
     * Set the coordinates of the maximum position on the map to be displayed
     * @param max A maximum coordinate to be viewed
     */
    public static void setViewMax(Mark max) {
        viewMax = max;
    }

    /**
     * Sets the width of the race window
     * @param x pixel width of the race window
     */
    public static void setWindowX(double x) {
        windowX = x - sidePaneWidth;
    }

    /**
     * Sets the height of the race window
     * @param y pixel height of the race window
     */
    public static void setWindowY(double y) {
        windowY = y;
    }

    /**
     * Getter for the width of the window
     * @return the pixel value of the window width
     */
    public static double getWindowX() {
        return windowX;
    }

    /**
     * Getter for the height of the window
     * @return the pixel value of the window height
     */
    public static double getWindowY() {
        return windowY;
    }

    /**
     * Updates the border size within the window
     */
    public static void updateBorder(){
        double sizeX = viewMax.getX() - viewMin.getX();
        double sizeY = viewMax.getY() - viewMin.getY();

        if ((windowX / sizeX) > windowY / sizeY ) {
            BorderX = BorderConstant + (windowX - windowY / sizeY * sizeX) / 2;
        }else if((windowX / sizeX) < windowY / sizeY ) {
            BorderY = BorderConstant + (windowY - windowX / sizeX * sizeY) / 2;
        }
    }

    /**
     * Converts the y value handed in to an a value relative to the width of the window.
     * @param standardY The y value to be converted to a Y that is relative to how big the window is
     * @return the absolute y value on the window relative to the y value passed in.
     */
    public static double getRelativeY(double standardY){
        return windowY - (windowY - 2 * BorderY) * (standardY - viewMin.getY()) /
                (viewMax.getY() - viewMin.getY()) - BorderY;
    }

    /**
     * Converts the x value handed in to an a value relative to the height of the window.
     * @param standardX The x value to be converted to an x that is relative to how big the window is
     * @return the absolute x value on the window relative to the x value passed in.
     */
    public static double getRelativeX(double standardX){
        return (windowX - 2 * BorderX) * (standardX - viewMin.getX()) /
                (viewMax.getX() - viewMin.getX()) + BorderX;
    }
}
