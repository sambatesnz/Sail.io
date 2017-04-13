package seng302;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.time.LocalTime;


public class TimeZoneWrapper {
    private ZoneId raceTimeZoneId;
    private DateTimeFormatter raceTimeStringFormat;
    private String raceTimeZone;

    public TimeZoneWrapper(String raceTimeZone) {
        this.raceTimeZone = raceTimeZone;
        this.raceTimeZoneId = ZoneId.of(raceTimeZone);
        this.raceTimeStringFormat = DateTimeFormatter.ofPattern("hh:mm:ss");
    }

    private LocalTime getLocalTime(){
        return LocalTime.now(this.raceTimeZoneId);
    }

    public String getLocalTimeString(){
        return getLocalTime().format(raceTimeStringFormat);
    }

    public String getRaceTimeZoneString(){
        return "ABCD";
    }

}
