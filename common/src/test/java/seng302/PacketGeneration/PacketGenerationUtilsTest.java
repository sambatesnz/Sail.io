package seng302.PacketGeneration;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by osr13 on 28/09/17.
 */
public class PacketGenerationUtilsTest {

    @Test
    public void speedTest() throws Exception {
        for (int i = 1; i < 100000; i++) {
            byte[] a = PacketGenerationUtils.speedToTwoBytes(i);
            System.out.println(i + " - " + Arrays.toString(a));
            assertTrue(a[0] != 0 || a[1] != 0);
        }
    }

}