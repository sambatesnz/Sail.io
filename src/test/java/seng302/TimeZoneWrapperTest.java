package seng302;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalTime;


/**
 * Created by Stefan! on 13/04/2017.xdf
 */
public class TimeZoneWrapperTest {

    @Test
    public void raceTimeZoneConversion() {
        String NZDT = "UTC+12:00";
        TimeZoneWrapper timeZoneWrapper = new TimeZoneWrapper("Pacific/Auckland");
        String actualTimeZone = timeZoneWrapper.getRaceTimeZoneString();
        Assert.assertEquals(NZDT, actualTimeZone);
    }
}