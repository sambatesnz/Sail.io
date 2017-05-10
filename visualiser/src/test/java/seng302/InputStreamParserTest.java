//package seng302;
//
//import jdk.internal.util.xml.impl.Input;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.util.Arrays;
//
//public class InputStreamParserTest {
//    InputStreamParser testInput = new InputStreamParser(new Race());
//    @Before
//    public void setup(){
//        byte[] testMessage = {
//                (byte)0x01, (byte)0x68, (byte)0x1b, (byte)0x3a, // 1:MVN, : Time
//                (byte)0x79, (byte)0x4f, (byte)0x01, (byte)0x8c, // Time : SourceID data
//                (byte)0x00, (byte)0x00, (byte)0x00, (byte)0xc5, // SourceID date : Sequence Number
//                (byte)0x3c, (byte)0x00, (byte)0x00, (byte)0x0c, // Sequence Number : Device Type
//                (byte)0xc7, (byte)0xdc, (byte)0x02, (byte)0x29, // Test latitude data
//                (byte)0xba, (byte)0xea, (byte)0x6c, (byte)0x08, // Test longitude data
//                (byte)0x89, (byte)0x28, (byte)0x00, (byte)0x00, // Altitude data
//                (byte)0x0b, (byte)0xcf, (byte)0xa2, (byte)0x00, // Heading : Pitch
//                (byte)0x57, (byte)0xff, (byte)0x00, (byte)0x00, // Roll : BoatSpeed
//                (byte)0x96, (byte)0xb4, (byte)0xb8, (byte)0x06, // COG : SOG
//                (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, // AWS : AWA
//                (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, // TWS : TWD
//                (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, // TWA : CurrentDrift
//                (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, // Current Set : Rudder Angle
//                (byte)0x36, (byte)0x16, (byte)0x3f, (byte)0x4c,
//                (byte)0x47, (byte)0x83, (byte)0x25, (byte)0x59,
//                (byte)0x73, (byte)0x5b, (byte)0x1c, (byte)0x53,
//                (byte)0x01, (byte)0xca, (byte)0x00, (byte)0x00,
//                (byte)0x00, (byte)0x38, (byte)0x00, (byte)0x01,
//                (byte)0x65, (byte)0x1b, (byte)0x3a, (byte)0x79,
//                (byte)0x4f, (byte)0x01, (byte)0x69, (byte)0x00,
//                (byte)0x00, (byte)0x00, (byte)0x47, (byte)0xbc,
//                (byte)0x00, (byte)0x00, (byte)0x01, (byte)0x17,
//                (byte)0xba, (byte)0x01, (byte)0x29, (byte)0xa8,
//                (byte)0x64, (byte)0x69, (byte)0x08, (byte)0x32,
//                (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x49,
//                (byte)0x86, (byte)0x9b, (byte)0xff, (byte)0xb6,
//                (byte)0x07, (byte)0x00, (byte)0x00, (byte)0x1a,
//                (byte)0x83, (byte)0xfe, (byte)0x15, (byte)0x00,
//                (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00,
//                (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00,
//                (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00,
//                (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x5a,
//                (byte)0x9e, (byte)0x2e, (byte)0xf4};
//        testInput.parseInput(37, testMessage);
//    }
//
//    @Test
//    public void messageVersionTest() {
//        Assert.assertEquals(testInput.getMessageVersionNumber(), (byte)0x01);
//    }
//
//    @Test
//    public void timeTest() {
//        Assert.assertArrayEquals(testInput.getTime(), new byte[] {(byte)0x68, (byte)0x1b, (byte)0x3a, (byte)0x79,
//                (byte)0x4f, (byte)0x01});
//    }
//
//    @Test
//    public void sourceIDTest() {
//        Assert.assertArrayEquals(testInput.getSourceID(), new byte[] {(byte)0x8c, (byte)0x00, (byte)0x00, (byte)0x00});
//    }
//
//    @Test
//    public void sequenceNumTest() {
//        Assert.assertArrayEquals(testInput.getSeqNumber(), new byte[] {(byte)0xc5, (byte)0x3c, (byte)0x00, (byte)0x00});
//    }
//
//    @Test
//    public void deviceTypeTest() {
//        Assert.assertEquals(testInput.getDeviceType(), (byte)0x0c);
//    }
//
//    @Test
//    public void longitudeTest(){
//        Assert.assertArrayEquals(testInput.getLongitude(), new byte[] {(byte)0xba, (byte)0xea, (byte)0x6c, (byte)0x08});
//    }
//
//    @Test
//    public void latitudeTest(){
//        Assert.assertArrayEquals(testInput.getLatitude(), new byte[] {(byte)0xc7, (byte)0xdc, (byte)0x02, (byte)0x29});
//    }
//
//    @Test
//    public void AltitudeTest() {
//        Assert.assertArrayEquals(testInput.getAltitude(), new byte[] {(byte)0x89, (byte)0x28, (byte)0x00, (byte)0x00});
//    }
//
//    @Test
//    public void headingTest() {
//        Assert.assertArrayEquals(testInput.getHeading(), new byte[] {(byte)0x0b, (byte)0xcf});
//    }
//
//    @Test
//    public void pitchTest() {
//        Assert.assertArrayEquals(testInput.getPitch(), new byte[] {(byte)0xa2, (byte)0x00});
//    }
//
//    @Test
//    public void rollTest() {
//        Assert.assertArrayEquals(testInput.getRoll(), new byte[] {(byte)0x57, (byte)0xff});
//    }
//
//    @Test
//    public void boatSpeedTest() {
//        Assert.assertArrayEquals(testInput.getBoatSpeed(), new byte[] {(byte)0x00, (byte)0x00});
//    }
//
//    @Test
//    public void cogTest() {
//        Assert.assertArrayEquals(testInput.getCourseOverGround(), new byte[] {(byte)0x96, (byte)0xb4});
//    }
//
//    @Test
//    public void sogTest() {
//        Assert.assertArrayEquals(testInput.getSpeedOverGround(), new byte[] {(byte)0xb8, (byte)0x06});
//    }
//
//    @Test
//    public void appWindSpeedTest() {
//        Assert.assertArrayEquals(testInput.getApparentWindSpeed(), new byte[] {(byte)0x00, (byte)0x00});
//    }
//
//    @Test
//    public void appWindAngleTest() {
//        Assert.assertArrayEquals(testInput.getApparentWindAngle(), new byte[] {(byte)0x00, (byte)0x00});
//    }
//
//    @Test
//    public void trueWindSpeedTest() {
//        Assert.assertArrayEquals(testInput.getTrueWindSpeed(), new byte[] {(byte)0x00, (byte)0x00});
//    }
//
//    @Test
//    public void trueWindDirectionTest() {
//        Assert.assertArrayEquals(testInput.getTrueWindDirection(), new byte[] {(byte)0x00, (byte)0x00});
//    }
//
//    @Test
//    public void trueWindAngleTest() {
//        Assert.assertArrayEquals(testInput.getTrueWindAngle(), new byte[] {(byte)0x00, (byte)0x00});
//    }
//
//    @Test
//    public void currentDriftTest() {
//        Assert.assertArrayEquals(testInput.getCurrentDrift(), new byte[] {(byte)0x00, (byte)0x00});
//    }
//
//    @Test
//    public void currentSetTest() {
//        Assert.assertArrayEquals(testInput.getCurrentSet(), new byte[] {(byte)0x00, (byte)0x00});
//    }
//
//    @Test
//    public void rudderAngleTest() {
//        Assert.assertArrayEquals(testInput.getRudderAngle(), new byte[] {(byte)0x00, (byte)0x00});
//    }
//
//
//}