package seng302;

import javafx.scene.paint.Color;
import org.junit.Test;
import seng302.Modes.Race;
import seng302.RaceObjects.Boat;
import seng302.RaceObjects.CompoundMark;
import seng302.RaceObjects.CourseLimit;
import seng302.RaceObjects.Mark;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by msi52 on 11/09/17.
 */
public class CollisionDetectorTest {
    private Race race = new Race();
    private CollisionDetector detector = new CollisionDetector(race);

    @Test
    public void testBoatCollisions() {
        Double lat = 57.80501528;
        Double lon = 65.36521452;
        Mark checkMark = new Mark(lat, lon);
        Mark other = new Mark(25.256412, 36.325522);

        ArrayList<Mark> marks = new ArrayList<>(Arrays.asList(checkMark, other));
        try {
            race.addBoat(101);
            race.addBoat(102);
            race.addBoat(103);

            for (int i = 0; i < race.getBoats().size(); i++) {
                Boat boat = race.getBoats().get(i);
                boat.setMark(marks.get(i % 2));
            }

            assertEquals(true, null != detector.checkBoatCollision(race.getBoats().get(0)));
            assertEquals(false, null != detector.checkBoatCollision(race.getBoats().get(1)));
            assertEquals(true, null != detector.checkBoatCollision(race.getBoats().get(2)));

        } catch (Exception e) {
            System.out.println("Whoops");
        }
    }

    @Test
    public void testMarkCollisions() {
        Boat checkBoat = new Boat("boat1", "bt1", 101, "NZ");

        Double lat = 57.80501528;
        Double lon = 65.36521452;
        Mark checkMark = new Mark(lat, lon);
        Mark mark = new Mark(25.256412, 36.325522);

        checkBoat.setMark(checkMark);

        List<CompoundMark> marks = new ArrayList<>(Arrays.asList(new CompoundMark("RandomCompundMarkName", new ArrayList<Mark>(Arrays.asList(checkMark, mark)), Color.INDIANRED, 0, "Gate")));

        assertEquals(true, detector.checkMarkCollisions(checkBoat, marks));
    }

    @Test
    public void testBoundaryCollisions() {
        Boat checkBoat = new Boat("boat1", "bt1", 101, "NZ");
        Boat secondCheckBoat = new Boat("boat2", "bt2", 102, "NZ");

        Mark checkMark = new Mark(57.80501528, 65.36521452);
        Mark secondCheckMark = new Mark(25.652354, 36.412534);

        checkBoat.setMark(checkMark);
        secondCheckBoat.setMark(secondCheckMark);

        List<CourseLimit> courseLimits = new ArrayList<>(
            Arrays.asList(
                new CourseLimit(0, 25.256412, 36.325522),
                new CourseLimit(1, 25.965825, 36.521452),
                new CourseLimit(2, 25.751451, 36.365412)));


        assertEquals(false, detector.checkWithinBoundary(checkBoat,courseLimits));
        assertEquals(true, detector.checkWithinBoundary(secondCheckBoat,courseLimits));


    }

}
