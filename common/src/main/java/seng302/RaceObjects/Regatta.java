package seng302.RaceObjects;

/**
 * Created by asv15 on 17/05/17.
 */
public class Regatta {
    private int regattaId;
    private String regattaName;
    private String courseName;
    private int utcOffset;

    public Regatta(int regattaId, String regattaName, String courseName, int utcOffset) {

        this.regattaId = regattaId;
        this.regattaName = regattaName;
        this.courseName = courseName;
        this.utcOffset = utcOffset;
    }

    public int getRegattaId() {
        return regattaId;
    }

    public void setRegattaId(int regattaId) {
        this.regattaId = regattaId;
    }

    public String getRegattaName() {
        return regattaName;
    }

    public void setRegattaName(String regattaName) {
        this.regattaName = regattaName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getUtcOffset() {
        return utcOffset;
    }

    public void setUtcOffset(int utcOffset) {
        this.utcOffset = utcOffset;
    }
}
