package seng302;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;


public class TimeZoneWrapper {
    private ZoneId raceTimeZoneId;
    private DateTimeFormatter raceTimeStringFormat;
    private String raceTimeZone;

    private TimeZone localTimeZone;
    private String utcOffset;

    public TimeZoneWrapper(String raceTimeZone) {
        this.raceTimeZone = raceTimeZone;
        this.raceTimeZoneId = ZoneId.of(raceTimeZone);
        this.raceTimeStringFormat = DateTimeFormatter.ofPattern("hh:mm:ss");

        Calendar calendar = new GregorianCalendar();
        this.localTimeZone = calendar.getTimeZone();
        this.utcOffset = calculateOffset();
    }

    private String calculateOffset(){
        ZonedDateTime zdt = LocalDateTime.now().atZone(this.raceTimeZoneId);
        ZoneOffset offset = zdt.getOffset();
        return "UTC" + offset;
    }

    private LocalTime getLocalTime(){
        return LocalTime.now(this.raceTimeZoneId);
    }

    public String getLocalTimeString(){
        return getLocalTime().format(raceTimeStringFormat);
    }

    public String getRaceTimeZoneString(){
        return this.utcOffset;
    }

}
