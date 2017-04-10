package seng302;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Created by Stefan! on 10/04/2017.
 */
public class TimeZoneWrapper {
    Calendar calendar;
    TimeZone localTimeZone;
    TimeZone raceTimeZone;
    int utcOffset;

    public TimeZoneWrapper(String raceTimeZone) {
        this.raceTimeZone = TimeZone.getTimeZone(raceTimeZone);
        this.calendar = new GregorianCalendar();
        this.localTimeZone = this.calendar.getTimeZone();
        this.utcOffset = calculateOffset();
    }

    private int calculateOffset() {
        Calendar cal = GregorianCalendar.getInstance(raceTimeZone);
        int offsetInMillis = raceTimeZone.getOffset(cal.getTimeInMillis());

        String offset = String.format("%02d:%02d", Math.abs((offsetInMillis) / 3600000), Math.abs((offsetInMillis / 60000) % 60));
        offset = (offsetInMillis >= 0 ? "+" : "-") + offset;
        System.out.println(offset);
        return 0;
    }


}
