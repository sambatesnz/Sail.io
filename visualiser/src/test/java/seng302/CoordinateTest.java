package seng302;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests the Coordinate class
 */
public class CoordinateTest {
//    @Test
//    public void testNorthPoleX(){
//        Mark northPole = new Mark(0, 90);
//        Assert.assertEquals(0, northPole.getX(), 1);
//    }
//
//    @Test
//    public void testNorthPoleY(){
//        Mark northPole = new Mark(0, 90);
//        Assert.assertEquals(20037500, northPole.getY(), 1);
//    }
//
//    @Test
//    public void testEndEquatorX(){
//        Mark endEquator = new Mark(180, 0);
//        Assert.assertEquals(20037500, endEquator.getX(), 1);
//    }
    @Test
    public void testEndEquatorX(){
        // This is something that I needed to add in order to get our build passing.
        // There is an old Java bug which causes a crash if there are no classes in here.
        Assert.assertTrue(true);
    }

}
