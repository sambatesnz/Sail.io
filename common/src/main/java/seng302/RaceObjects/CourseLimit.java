package seng302.RaceObjects;

/**
 * Created by osr13 on 12/05/17.
 */
public class CourseLimit {
    private int SeqId;
    private double lat;
    private double lon;

    public CourseLimit(int seqId, double lat, double lon) {
        SeqId = seqId;
        this.lat = lat;
        this.lon = lon;
    }

    public int getSeqId() {
        return SeqId;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }
}
