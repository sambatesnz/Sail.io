package seng302;

import org.junit.Assert;
import org.junit.Test;
import seng302.Controllers.TimeZoneWrapper;

/**
 * Created by sha162 on 22/05/17.
 */
public class TimeZoneWrapperTest {
    @Test
    public void raceTimeZoneConversion() {
        String NZDT = "UTC+12:00";
        TimeZoneWrapper timeZoneWrapper = new TimeZoneWrapper(12);
        String actualTimeZone = timeZoneWrapper.getRaceTimeZoneString();
        Assert.assertEquals(NZDT, actualTimeZone);
    }
}
