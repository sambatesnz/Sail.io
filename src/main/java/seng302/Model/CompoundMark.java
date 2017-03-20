package seng302.Model;

import java.util.ArrayList;

/**
 * Created by osr13 on 17/03/17.
 */
public class CompoundMark {

    private ArrayList<Point> compoundMarks = new ArrayList<>();
    private String name;
    int id;


    public class Point{
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

}
