package seng302.Model;

import java.util.ArrayList;

/**
 * Holds all the points that Boats must visit on a course, and the order in which they must do so. Also contains many
 * Math-based
 */
public class Course {
    private String courseName;
    private ArrayList<CompoundMark> courseCompoundMarks = new ArrayList<>();

    /**
     * Constructor.
     * @param courseName
     * @param courseCompoundMarks
     */
    public Course(String courseName, ArrayList<CompoundMark> courseCompoundMarks) {
        this.courseCompoundMarks = courseCompoundMarks;
        this.courseName = courseName;
    }

    /**
     * Calculates the distance between two marks.
     * @param mark1
     * @param mark2
     * @return
     */
    private double findDistBetweenCompoundMarks(CompoundMark mark1, CompoundMark mark2) {
        if (mark1.getCompoundMarks().size() == 2) {
            mark1 = mark1.findAverageGate(mark1);
        }
        if (mark2.getCompoundMarks().size() == 2) {
            mark2 = mark2.findAverageGate(mark2);
        }
        double lat1 = mark1.getCompoundMarks().get(0).latitude;
        double lat2 = mark2.getCompoundMarks().get(0).latitude;
        double long1 = mark1.getCompoundMarks().get(0).longitude;
        double long2 = mark2.getCompoundMarks().get(0).longitude;

        return distance(lat1, long1, lat2, long2, "K");
    }

    /**
     * Calculates the distance between two Compound Marks.
     * @param lat1
     * @param lon1
     * @param lat2
     * @param lon2
     * @param unit
     * @return
     */
    private static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (unit.equals("K")) {
            dist = dist * 1.609344;
        } else if (unit.equals("N")) {
            dist = dist * 0.8684;
        }
        return (dist);
    }

    /**
     * Converts degrees to radians.
     * @param deg
     * @return degrees in their equivalent radians.
     */
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    /**
     * Converts radians to degrees.
     * @param rad
     * @return
     */
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

    /**
     * Generates a heading based on the the new direction that the boat is heading.
     * @param start
     * @param dest
     * @return
     */
    private double findHeading(CompoundMark start, CompoundMark dest) {
        double bearing = 0;
        if (start.getCompoundMarks().size() > 1) {
            start = start.findAverageGate(start);
        }
        if (dest.getCompoundMarks().size() > 1) {
            dest = dest.findAverageGate(start);
        }
        double lat1 = start.getCompoundMarks().get(0).latitude;
        double lat2 = dest.getCompoundMarks().get(0).latitude;
        double long1 = start.getCompoundMarks().get(0).longitude;
        double long2 = dest.getCompoundMarks().get(0).longitude;
        double varx = Math.cos(lat2) * Math.sin(long2 - long1);
        double vary = Math.cos(lat1) * Math.sin(lat2) - Math.sin(lat1) * Math.cos(lat2) * Math.cos(long2-long1);

        return Math.atan2(varx, vary);
    }

    /**
     * Iterates through the entire course to find the total length of the race.
     * @param courseCompoundMarks
     * @param courseOrder
     * @return
     */
    public float generateTotalCourseLength(ArrayList<CompoundMark> courseCompoundMarks, ArrayList<Integer> courseOrder){
        float cumulativeCourseLengthDistance = 0;

        for (int i = 1; i < courseOrder.size()-1; i++) {
            cumulativeCourseLengthDistance += findDistBetweenCompoundMarks(courseCompoundMarks.get(i - 1), courseCompoundMarks.get(i));
        }
        return cumulativeCourseLengthDistance;
    }

    public String getCourseName() {
        return courseName;
    }

    public ArrayList<CompoundMark> getCourseCompoundMarks() {
        return courseCompoundMarks;
    }

    public void setCanvasAspectZoom(){
        double minLat;
        double minLong;
        double maxLat;
        double maxLong;

        maxLat = courseCompoundMarks.get(0).getCompoundMarks().get(0).getLatitude();
        minLat = courseCompoundMarks.get(0).getCompoundMarks().get(0).getLatitude();
        maxLong = courseCompoundMarks.get(0).getCompoundMarks().get(0).getLongitude();
        minLong = courseCompoundMarks.get(0).getCompoundMarks().get(0).getLongitude();

        for(CompoundMark courseMark : courseCompoundMarks) {


            for (int i = 0; i < courseMark.getCompoundMarks().size(); i++) {

                if (courseMark.getCompoundMarks().get(i).getLatitude() > maxLat) {
                    maxLat = courseMark.getCompoundMarks().get(i).getLatitude();
                }
                if (courseMark.getCompoundMarks().get(i).getLatitude() < minLat){
                    minLat = courseMark.getCompoundMarks().get(i).getLatitude();
                }
                if (courseMark.getCompoundMarks().get(i).getLongitude() > maxLong) {
                    maxLong = courseMark.getCompoundMarks().get(i).getLongitude();
                }
                if (courseMark.getCompoundMarks().get(i).getLongitude() < minLong) {
                    System.out.println(minLong);
                    minLong = courseMark.getCompoundMarks().get(i).getLongitude();
                }
            }
        }
        System.out.printf("minLat: %f, maxLat: %f, minLong: %f, maxLong: %f.", minLat, maxLat, minLong, maxLong);
    }

}
