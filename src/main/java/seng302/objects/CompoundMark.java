package seng302.objects;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by osr13 on 17/03/17.
 */
public class CompoundMark {

    private ArrayList<Point> compoundMarks = new ArrayList<>();
    private String name;
    int id;


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

        @Override
        public String toString() {
            return "(" + latitude + ", " + longitude + ")";
        }
    }


    public ArrayList<Point> getCompoundMarks() {
        return compoundMarks;
    }

    CompoundMark(String name, int id){
        this.name = name;
        this.id = id;

    }

    private CompoundMark(String name, ArrayList<Point> points){
        this.name = name;
        if (this.compoundMarks.size() > 2){
            throw new Error("cannot construct A compound mark with more than 2 points");
        } else {
            this.compoundMarks = points;
        }
    }

    public ArrayList<Point> getPoints(){
        return this.compoundMarks;
    }


    public void addMark(double latitude, double longitude){
        if (this.compoundMarks.size() > 2){
            throw new Error("cannot add a compound mark as there is already 2 points");
        } else {
            Point point = new Point(latitude, longitude);
            this.compoundMarks.add(point);
        }
    }

    public String toString(){
        String out = "";
        out += "Name: " + this.name + ", ID: " + this.id + " Points: ";
        for (int i=0; i<this.compoundMarks.size(); i++ ){
            Point currentPoint = this.compoundMarks.get(i);
            out += "(" + currentPoint.latitude + ", " + currentPoint.longitude + ")";
        }
        return out;
    }

    public CompoundMark findAverageGate(CompoundMark gate){
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

        return distance(lat1, long1, lat2, long2, "K");
    }

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

    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

    private double findHeading(CompoundMark start, CompoundMark dest) {
        double bearing = 0;
        if (start.compoundMarks.size() > 1) {
            start = findAverageGate(start);
        }
        if (dest.compoundMarks.size() > 1) {
            dest = findAverageGate(start);
        }
        double lat1 = start.compoundMarks.get(0).latitude;
        double lat2 = dest.compoundMarks.get(0).latitude;
        double long1 = start.compoundMarks.get(0).longitude;
        double long2 = dest.compoundMarks.get(0).longitude;
        double varx = Math.cos(lat2) * Math.sin(long2 - long1);
        double vary = Math.cos(lat1) * Math.sin(lat2) - Math.sin(lat1) * Math.cos(lat2) * Math.cos(long2-long1);

        return Math.atan2(varx, vary);
    }

}
