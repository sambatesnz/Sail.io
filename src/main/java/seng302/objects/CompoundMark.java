package seng302.objects;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by osr13 on 17/03/17.
 */
public class CompoundMark {

    private ArrayList<Point> compoundMarks = new ArrayList<>();
    private String name;

    public String getName() {
        return name;
    }

    class Point{
        double latitude;
        double longitude;

        Point(double latitude, double longitude){
            this.latitude = latitude;
            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public double getLongitude() {
            return longitude;
        }
    }


    private CompoundMark(String name){
        this.name = name;
    }

    private CompoundMark(String name, ArrayList<Point> points){
        this.name = name;
        if (this.compoundMarks.size() > 2){
            throw new Error("cannot construct A compound mark with more than 2 points");
        } else {
            this.compoundMarks = points;
        }
    }


    public void addMark(double latitude, double longitude){
        if (this.compoundMarks.size() > 2){
            throw new Error("cannot add a compound mark as there is already 2 points");
        } else {
            Point point = new Point(latitude, longitude);
            this.compoundMarks.add(point);
        }
    }

    private CompoundMark findAverageGate(CompoundMark gate){
        CompoundMark outputGate = gate;
        if (gate.compoundMarks.size() == 2) {
            Point point1 = gate.compoundMarks.get(0);
            Point point2 = gate.compoundMarks.get(1);
            double meanLat = (point1.latitude + point2.latitude)/2;
            double meanLong = (point1.longitude + point2.longitude)/2;
            Point meanPoint = new Point(meanLat, meanLong);
            ArrayList<Point> averagePoint = new ArrayList<>();
            averagePoint.add(meanPoint);
            outputGate = new CompoundMark(gate.name, averagePoint);
        }
        return outputGate;
    }

    private double findDistBetweenCompoundMarks(CompoundMark mark1, CompoundMark mark2) {
        if (mark1.compoundMarks.size() == 2) {
            mark1 = findAverageGate(mark1);
        }
        if (mark2.compoundMarks.size() == 2) {
            mark2 = findAverageGate(mark2);
        }
        double lat1 = mark1.compoundMarks.get(0).latitude;
        double lat2 = mark2.compoundMarks.get(0).latitude;
        double long1 = mark1.compoundMarks.get(0).longitude;
        double long2 = mark2.compoundMarks.get(0).longitude;

        double distance = distance(lat1, long1, lat2, long2, "K");

        return distance;
    }

    private static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (unit == "K") {
            dist = dist * 1.609344;
        } else if (unit == "N") {
            dist = dist * 0.8684;
        }
        return (dist);
    }

    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }


}
