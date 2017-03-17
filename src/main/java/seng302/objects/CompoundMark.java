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

    ArrayList<Point> getPoints(){
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


}
