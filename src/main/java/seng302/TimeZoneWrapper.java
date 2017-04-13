package seng302;

import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * A wrapper for the race time Zone
 * Holds the UTC Offset and the current time in the time zone
 */
public class TimeZoneWrapper {
    private ZoneId raceTimeZoneId;
    private DateTimeFormatter raceTimeStringFormat;
    private String utcOffset;

    /**
     * Constructor for TimeZone Wrapper Class
     * @param raceTimeZone String of the timeZone you are setting
     */
    public TimeZoneWrapper(String raceTimeZone) {
        this.raceTimeZoneId = ZoneId.of(raceTimeZone);
        this.raceTimeStringFormat = DateTimeFormatter.ofPattern("hh:mm:ss");
        this.utcOffset = calculateOffset();
    }

    public String getLocalTimeString(){
        return getLocalTime().format(raceTimeStringFormat);
    }

    public String getRaceTimeZoneString(){
        return this.utcOffset;
    }

    /**
     * Calculates the offset in hours from the UTC time standard
     * @return A String of the offset with UTC at the front : UTC-03:00
     */
    private String calculateOffset(){
        ZonedDateTime zdt = LocalDateTime.now().atZone(this.raceTimeZoneId);
        ZoneOffset offset = zdt.getOffset();
        return "UTC" + offset;
    }

    private LocalTime getLocalTime(){
        return LocalTime.now(this.raceTimeZoneId);
    }



}
