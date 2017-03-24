package seng302.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Holds all the points that Boats must visit on a course, and the order in which they must do so. Also contains many
 * Math-based functions
 */
public class Course {
    private String courseName;
    private ArrayList<CompoundMark> courseCompoundMarks = new ArrayList<>();
    private ArrayList<Integer> courseOrder;
    private CourseCreator courseCreator;

    /**
     * Constructor.
     * @param courseName
     */
    public Course(String courseName) {
        this.courseName = courseName;
        //this.courseCompoundMarks = courseCompoundMarks;
        //this.courseOrder = courseOrder;
        this.courseCreator = loadCoarseCreator();
        this.courseCompoundMarks = courseCreator.getCompoundMarks();
        this.courseOrder = courseCreator.getGateOrderForRace();
    }

    public Course(String courseName, String fileLocation){
        this.courseName = courseName;
        this.courseCreator = loadCoarseCreator(fileLocation);
        this.courseCompoundMarks = courseCreator.getCompoundMarks();
        this.courseOrder = courseCreator.getGateOrderForRace();
    }

    private CourseCreator loadCoarseCreator() {
        AppConfig appConfig = new AppConfig();
        String fileLocation = appConfig.getProperty(AppConfig.COURSE_FILE_LOCATION);
        return new CourseCreator(fileLocation);
    }

    private CourseCreator loadCoarseCreator(String fileLocation){
        return new CourseCreator(fileLocation);
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
     * Gets a compound mark by its id
     * @param id the id you want
     * @return The compound mark which has your expected id
     * @throws Error if it doesn't find a mark with your id
     */
    private CompoundMark getCompoundMarkById(int id){
        for (CompoundMark mark: courseCompoundMarks){
            if (mark.getId() == id ){
                return mark;
            }
        }
        throw new Error("Could not find id for mark, Something is probably wrong in the course file");
    }

    /**
     * Iterates through the entire course to find the total length of the race.
     * @return float the course length
     */
    double generateTotalCourseLength(){
        double cumulativeCourseLengthDistance = 0;


        if (courseOrder.size() == 2 ){
            int firstMarkId = courseOrder.get(1);
            int secondMarkId = courseOrder.get(0);
            CompoundMark firstMark = getCompoundMarkById(firstMarkId);
            CompoundMark secondMark = getCompoundMarkById(secondMarkId);
            cumulativeCourseLengthDistance = findDistBetweenCompoundMarks(firstMark, secondMark);
        } else{
            for (int i=1; i<courseOrder.size()-1; i++){
                int firstMarkId = courseOrder.get(i-1);
                int secondMarkId = courseOrder.get(i);
                CompoundMark firstMark = getCompoundMarkById(firstMarkId);
                CompoundMark secondMark = getCompoundMarkById(secondMarkId);
                cumulativeCourseLengthDistance += findDistBetweenCompoundMarks(firstMark, secondMark);
             }
        }
        return cumulativeCourseLengthDistance;
    }

    public String getCourseName() {
        return courseName;
    }

    public ArrayList<CompoundMark> getCourseCompoundMarks() {
        return courseCompoundMarks;
    }

    public ArrayList<Double> findMaxMinLatLong(){

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
                    minLong = courseMark.getCompoundMarks().get(i).getLongitude();
                }
            }
        }
        ArrayList<Double> maxCoords = new ArrayList<>();
        maxCoords.add(minLat);
        maxCoords.add(maxLat);
        maxCoords.add(minLong);
        maxCoords.add(maxLong);
        return maxCoords;
    }

    public void overloadCourseOrder(ArrayList<Integer> newCourseOrder) {
        for (int i=0; i< newCourseOrder.size(); i++) {
            //This will throw an error if the course has a gate with an id that doesnt exists
            getCompoundMarkById(newCourseOrder.get(i));
        }
        this.courseOrder = newCourseOrder;

    }

}
