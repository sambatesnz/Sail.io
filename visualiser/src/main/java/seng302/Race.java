package seng302;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seng302.Controllers.Coordinate;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.*;

import static javafx.collections.FXCollections.observableArrayList;

/**
 * Class that simulates the racing of the boats competing in the America's Cup 35
 * This displays a text-based play by play commentary of the race as it happens
 */
public class Race {
    private Regatta regatta;
    private int numFinishers = 0;
    private List<CompoundMark> compoundMarks;
    private List<CompoundMark> gates;
    // Changing list of boats to hashmap. where key is boat SourceID, as retrieved from the xml message
//    private List<Boat> boats;
    public Map<Integer, Boat> boats;
    private List<Boat> finishedBoats;
    private List<Leg> legs;
    private List<Mark> boundaries;
    private List<Integer> courseOrder;
    private List<Integer> paricipants;
    private double windHeading;
    private double windSpeed;
    private ObservableList<Boat> currentOrder;
    private ObservableList<String> MarkStrings;
    private long expectedStartTime;
    private int raceStatus;
    public boolean finished = false;
    private Mark center;
    private boolean raceReady = false;
    private Boat centerOfScreen;
    private Boat boatToFollow;
    private List<Integer> participants;

    public boolean isRaceReady() {
        return raceReady;
    }

    public void setRaceReady(boolean raceReady) {
        this.raceReady = raceReady;
    }

    /**
     * Constructor for the race class.
     */
    public Race() {
//        java.util.Scanner s = new java.util.Scanner(getClass().getClassLoader().getResourceAsStream("ExampleXMLs/Race.xml")).useDelimiter("\\A");
//        String xmlString = s.hasNext() ? s.next() : "";
//        parseRaceXML(xmlString);
////        parseXML("course.xml");
//        setWindHeading(190);
////        boats = getContestants();
//
//        s = new java.util.Scanner(getClass().getClassLoader().getResourceAsStream("Boats.xml")).useDelimiter("\\A");
//        xmlString = s.hasNext() ? s.next() : "";
//        parseBoatsXML(xmlString);
//
//        s = new java.util.Scanner(getClass().getClassLoader().getResourceAsStream("Regatta.xml")).useDelimiter("\\A");
//        xmlString = s.hasNext() ? s.next() : "";
//        parseRegattaXML(xmlString);

//        parseXML("course.xml");
        // Contestants are now retrieved from the xml message
        //boats = getContestants();
        finishedBoats = new ArrayList<>();
        // TODO: Current order needs to be instantiated here. Get the list of boats in the race first. Then use the time to next gate in the race packet to decide race Mark
        MarkStrings = FXCollections.observableArrayList();

//        for (Boat boat : boats.values()) {
//            boat.setHeading(legs.get(boat.getCurrentLegIndex()).getHeading());
//            boat.setX(legs.get(0).getStart().getX());
//            boat.setY(legs.get(0).getStart().getY());
//        }
    }

    public void setViewParams() {
        double minLat = boundaries.stream().min(Comparator.comparingDouble(Mark::getLatitude)).get().getLatitude();
        double minLon = boundaries.stream().min(Comparator.comparingDouble(Mark::getLongitude)).get().getLongitude();
        double maxLat = boundaries.stream().max(Comparator.comparingDouble(Mark::getLatitude)).get().getLatitude();
        double maxLon = boundaries.stream().max(Comparator.comparingDouble(Mark::getLongitude)).get().getLongitude();
        Mark viewMin = new Mark(minLat, minLon);
        Mark viewMax = new Mark(maxLat, maxLon);

        Coordinate.setOffset(new Mark(0, 0));
        Coordinate.setDefaultCourseMin(viewMin);
        Coordinate.setDefaultCourseMax(viewMax);
        Coordinate.setViewMin(viewMin.getCopy());
        Coordinate.setViewMax(viewMax.getCopy());

        center = getCenter(viewMin.getCopy(), viewMax.getCopy());
        centerOfScreen = new Boat(-1);
        centerOfScreen.setMark(center);
        boatToFollow = centerOfScreen;
        Coordinate.setCenter(getCenter(viewMin.getCopy(), viewMax.getCopy()));
        Coordinate.updateViewCoordinates();
    }

    /**
     * Setter for finishedBoat, mainly to allow for testing.
     * @param finishedBoats set the finished list of boats
     */
    public void setFinishedBoats(List<Boat> finishedBoats) {
        this.finishedBoats = finishedBoats;
    }

    public Mark getCenter(Mark min, Mark max) {
        Mark center = new Mark();   // changing from setting lat/long to x/y
        center.setX((max.getX() + min.getX()) / 2);
        center.setY((max.getY() + min.getY()) / 2);
        return center;
}

    public Mark calculateOffset(){
//      TODO: calculate offset based on center and boat (boat - center)
        Mark offset = new Mark();

        offset.setX(boatToFollow.getX() - center.getX());
        offset.setY(boatToFollow.getY() - center.getY());

        return offset;
    }
    /**
     * Setter for current order, mainly to allow for testing.
     * @param currentOrder sets the current order of boats
     */
    public void setCurrentOrder(ObservableList<Boat> currentOrder) {
        this.currentOrder = currentOrder;
    }

    /**
     * Getter for finished boat list.
     * @return finished boat list.
     */
    public List<Boat> getFinishedBoats() { return finishedBoats; }

    public double getWindHeading() {
        return windHeading;
    }

    /**
     * Takes the list of finished boats and creates a list of strings.
     * @return MarkStrings, an OberservableList of strings with in the order of finishers.
     */
    public ObservableList<String> getPositionStrings() {
        if (!MarkStrings.isEmpty()) {
            MarkStrings.clear();
        }
        MarkStrings.add("Race Results");
        int i = 1;
        for (Boat boat : finishedBoats) {
            MarkStrings.add(i + ": " + boat.getName());
            i++;
        }
        return MarkStrings;
    }

    /**
     * Getter for the observableList currentOrder. Gets the current Mark of the boats and adds it to the boat
     * Mark attribute.
     * @return currentOrder as an observable list.
     */
    public ObservableList<Boat> getCurrentOrder() {
        return FXCollections.observableArrayList(boats.values());
    }

    /**
     * Sets the current direction of the wind
     * @param windHeading the direction that the wind is heading
     */
    public void setWindHeading(double windHeading) {
        this.windHeading = windHeading;
    }

    /**
     * Getter for the speed of the wind for the race
     * @return the speed of the wind, in mm/s
     */
    public double getWindSpeed() {
        return windSpeed;
    }

    /**
     * Sets the current speed of the wind
     * @param windSpeed the speed that the wind is travelling
     */
    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    /**
     * Gets the time (in ms since 1 Jan, 1970) that the race is expected to start
     * @return The time that the race is expected to start
     */
    public long getExpectedStartTime() {
        return expectedStartTime;
    }

    /**
     * Sets the time that the race is expected to start (in ms since 1 Jan, 1970)
     * @param expectedStartTime The time that the race is expected to start
     */
    public void setExpectedStartTime(long expectedStartTime) {
        this.expectedStartTime = expectedStartTime;
    }

    /**
     * Gets the current status of the race:
     * 0: Not active
     * 1: Warning (between 3:00 and 1:00 before start)
     * 2: Preparatory (less than 1:00 before start)
     * 3: Started
     * 4: Finished (obsolete)
     * 5: Retired (obsolete)
     * 6: Abandoned
     * 7: Postponed
     * 8: Terminated
     * 9: Race start time not set
     * 10: Prestart (more than 3:00 until start)
     * @return The status of the race
     */
    public int getRaceStatus() {
        return raceStatus;
    }

    /**
     * Sets the current status of the race
     * 0: Not active
     * 1: Warning (between 3:00 and 1:00 before start)
     * 2: Preparatory (less than 1:00 before start)
     * 3: Started
     * 4: Finished (obsolete)
     * 5: Retired (obsolete)
     * 6: Abandoned
     * 7: Postponed
     * 8: Terminated
     * 9: Race start time not set
     * 10: Prestart (more than 3:00 until start)
     * @param raceStatus The status of the race
     */
    public void setRaceStatus(int raceStatus) {
        this.raceStatus = raceStatus;
    }

    /**
     * Getter for the list of compoundMarks
     * @return a list of compoundMarks
     */
    public List<CompoundMark> getCompoundMarks() {
        return compoundMarks;
    }

    /**
     * Get the legs in the race
     * @return the legs in the race
     */
    public List<Leg> getLegs() {
        return legs;
    }

    /**
     * Get the boats competing
     * @return the boats competing
     */
    public List<Boat> getBoats() {
        return new ArrayList<Boat>(boats.values());
    }

    //DEPRECATED

//    public Boat getBoatByID(int id) {
//        for (Boat boat : boats) {
//            if (boat.getSourceID() == id) {
//                return boat;
//            }
//        }
//        return null;
//    }


//    /**
//     * Creates an ArrayList of boats competing in the current race
//     * @return The ArrayList of boats
//     */
//    private ArrayList<Boat> getContestants() {
//        ArrayList<Boat> contestants = new ArrayList<>();
//        contestants.add(new Boat("ORACLE TEAM USA", 5.8, Color.RED, "USA"));
//        contestants.add(new Boat("Artemis Racing", 7.1, Color.BLUE, "SWE"));
//        contestants.add(new Boat("Emirates Team New Zealand", 11.2, Color.BLACK, "NZL"));
//        contestants.add(new Boat("Groupama Team France", 6.7, Color.WHEAT, "FRA"));
//        contestants.add(new Boat("Land Rover BAR", 7.6, Color.AQUAMARINE, "GBR"));
//        contestants.add(new Boat("SoftBank Team Japan", 9.3, Color.DARKSALMON, "JPN"));
//        return contestants;
//    }

//    /**
//     * Reads an XML file to get the attributes of things on the course
//     * @param fileName the filename to parse
//     */
//    private void parseXML(String fileName) {
//
//        try {
//            CourseCreator cc = new CourseCreator(fileName);

//            compoundMarks = cc.getLandmarks();
//            gates = new ArrayList<>();
//            for (CompoundMark mark : compoundMarks) {
//                if (mark.getType().equals("Gate")) {
//                    gates.add(mark);
//                }
//            }
//            boundaries = cc.getBoundaries();
//
//            courseOrder = cc.getGateOrderForRace();


//            legs = new ArrayList<>();
//            for (int i = 1; i < courseOrder.size(); i++) {
//                int startId = courseOrder.get(i-1);
//                int destId = courseOrder.get(i);
//                CompoundMark start = null;
//                CompoundMark dest = null;
//                for (CompoundMark lm : compoundMarks) {
//                    if (lm.getId() == startId) {
//                        start = lm;
//                    } else if (lm.getId() == destId) {
//                        dest = lm;
//                    }
//                }
//                legs.add(new Leg(start, dest));
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public void setCourseOrder(List<Integer> courseOrder) {
        this.courseOrder = courseOrder;
    }

//    /**
//     * Reads an XML file to get the attributes of things on the course
//     * @param xmlString the XML string to parse
//     */
//    private void parseRaceXML(String xmlString) {
//
//        try {
//            XMLParser xmlParser = new XMLParser(xmlString);
//
//            compoundMarks = xmlParser.getCourseLayout();
//            gates = new ArrayList<>();
//            for (CompoundMark mark : compoundMarks) {
//                if (mark.getType().equals("Gate")) {
//                    gates.add(mark);
//                }
//            }
////            boundaries = new ArrayList<>();
////            List<CourseLimit> courseLimits = xmlParser.getCourseLimits();
////            for (CourseLimit cl: courseLimits) {
////                boundaries.add(new Mark(cl.getLat(), cl.getLon()));
////            }
//
////            courseOrder = xmlParser.getCourseOrder();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public void setCompoundMarks(List<CompoundMark> compoundMarks) {
        this.compoundMarks = compoundMarks;
    }

    public void setGates(List<CompoundMark> compoundMarks) {
        gates = new ArrayList<>();
        for (CompoundMark mark : compoundMarks) {
            if (mark.getType().equals("Gate")) {
                gates.add(mark);
            }
        }
    }

    public void setBoundaries(List<CourseLimit> courseLimits) {
        boundaries = new ArrayList<>();
        for (CourseLimit cl: courseLimits) {
            boundaries.add(new Mark(cl.getLat(), cl.getLon()));
        }
    }

//    /**
//     * Reads an XML file to get the boat attributes
//     * @param xmlString the XML string to parse
//     */
//    private void parseBoatsXML(String xmlString) {
//        try {
//            XMLParser xmlParser = new XMLParser(xmlString);
//
//            boats = xmlParser.getBoats();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

//    /**
//     * Reads an XML file to get the regatta attributes
//     * @param xmlString the XML string to parse
//     */
//    private void parseRegattaXML(String xmlString) {
//        try {
//            XMLParser xmlParser = new XMLParser(xmlString);
//
//            regatta = xmlParser.getRegatta();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public void setRegatta(Regatta regatta) {
        this.regatta = regatta;
    }

    public void setBoats(Map<Integer, Boat> boats) {
        Map<Integer, Boat> actualBoats = new HashMap<>();
        for (Integer boat : participants) {
            actualBoats.put(boat, boats.get(boat));
        }
        this.boats = actualBoats;
    }

    /**
     * Get the gates
     * @return the gates
     */
    public List<CompoundMark> getGates() {
        return gates;
    }


//  BELOW IS DEPRECATED. INFORMATION NO LONGER APPLIES AS NOT ALL DATA IS ACCESSIBLE FROM DATA STREAM.

//    /**
//     * Runs a portion of the race, updating boat Marks and leg status
//     */
//    public void updateBoats() {
//        double distanceMultiplier = 1;
//        double movementMultiplier = 1;
//
//        for (Boat boat : boats) {
//            if (!finishedBoats.contains(boat)) {
//                boat.setCurrentLegDistance(boat.getCurrentLegDistance() + boat.getSpeed()*distanceMultiplier);
//
//                //Increments the distance by the speed
//                boat.setX(boat.getX() + boat.getSpeed()*sin(toRadians(boat.getHeading()))*movementMultiplier);
//                boat.setY(boat.getY() + boat.getSpeed()*cos(toRadians(boat.getHeading()))*movementMultiplier);
//
//                if (boat.getCurrentLegDistance() > legs.get(boat.getCurrentLegIndex()).getDistance()) {
//                    String passed = legs.get(boat.getCurrentLegIndex()).getDest().getName();
//                    boat.setCurrentLegDistance(boat.getCurrentLegDistance() - legs.get(boat.getCurrentLegIndex()).getDistance());
//                    boat.setCurrentLegIndex(boat.getCurrentLegIndex() + 1);
//
//                    // Gives each boat it's race time to the last mark.
//                    boat.setRaceTime(currentTimeMillis());
//                    // Sorts the boats in order. Attempts by leg it's doing first, then by time to complete last leg from start.
//                    currentOrder.sort((boat1, boat2) -> {
//                        if (boat1.getCurrentLegIndex() == boat2.getCurrentLegIndex()) {
//                            return Long.compare(boat1.getRaceTime(), boat2.getRaceTime());
//                        } else {
//                            return Integer.compare(boat2.getCurrentLegIndex(),boat1.getCurrentLegIndex());
//                        }
//                    });
//
//                    if (boat.getCurrentLegIndex() == legs.size()) {
////                        System.out.println(boat.getName() + " finished race!");
//                        numFinishers++;
//                        boat.setSpeed(0);
//                        finishedBoats.add(boat);
//                        if (numFinishers == boats.size()) {
//                            finished = true;
//                            return;
//                        }
//                    } else {
//                        boat.setHeading(legs.get(boat.getCurrentLegIndex()).getHeading());
////                        System.out.println(boat.getName() + " passed " + passed + ", now sailing to " +
////                                legs.get(boat.getCurrentLegIndex()).getDest().getName() + " with a heading of " +
////                                String.format("%.2f", legs.get(boat.getCurrentLegIndex()).getHeading()) + "°");
//                    }
//                }
//            }
//        }
//    }

    public List<Mark> getBoundaries() {
        return boundaries;
    }

    public Map<Integer, Boat> getBoatsMap() {
        return boats;
    }


    public void setBoatToFollow(Boat markToFollow) {
        this.boatToFollow = markToFollow;
    }

    public void setParticipants(List<Integer> participants) {
        this.participants = participants;
    }

    //    public void setBoundaries(ArrayList<Mark> boundaries) {
//        this.boundaries = boundaries;
//    }
}
