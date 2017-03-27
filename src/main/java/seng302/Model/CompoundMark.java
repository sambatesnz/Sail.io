package seng302.Model;

import java.util.ArrayList;

/**
 * Created by osr13 on 17/03/17.
 */
public class CompoundMark {

    private ArrayList<Point> compoundMarks = new ArrayList<>();
    private String name;
    private int id;

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public CompoundMark(String name, ArrayList<Point> points, Integer id){
        this.name = name;
        this.id = id;
        if (this.compoundMarks.size() > 2){
            throw new Error("cannot construct A compound mark with more than 2 points");
        } else {
            this.compoundMarks = points;
        }
    }

    /**
     * Points, which should possibly be named Coordinates, are the point at which a Compounds Marks is found
     * on a race course. Compound Marks can have one or two Points.
     */
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

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }
    }

    /**
     * Adds a new point to a Compound Mark.
     * @param latitude
     * @param longitude
     */
    public void addMark(double latitude, double longitude){
        if (this.compoundMarks.size() > 2){
            throw new Error("cannot add a compound mark as there is already 2 points");
        } else {
            Point point = new Point(latitude, longitude);
            this.compoundMarks.add(point);
        }
    }

    /**
     * Because Gates have a mark at either end, we average these points to provide a single target location for
     * Boats to get to.
     * @param gate
     * @return
     */
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
            outputGate = new CompoundMark(gate.name, averagePoint, gate.id);
        }
        return outputGate;
    }


    public ArrayList<Point> getCompoundMarks() {
        return compoundMarks;
    }

    /**
     * Lighter Constructor, for creating compound marks on the fly and adding their points later.
     * @param name
     * @param id
     */
    public CompoundMark(String name, int id){
        this.name = name;
        this.id = id;
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

    public void convertLatLongtoXY(Point point) {

    }
}
