package seng302;

import java.util.*;

/**
 * Created by tjg73 on 2/03/17.
 */
public class Race {

    private ArrayList<Boat> racingBoats = new ArrayList<>();
    private ArrayList<Event> raceEvents = new ArrayList<>();
    private ArrayList<String> finishingOrder = new ArrayList<>();
    private int racePlaybackDuration = -1;
    private float playbackSpeedMultiplier = 0;
    private float slowestBoatSpeed = Integer.MAX_VALUE;

    /**
     * Display the current Boats names that are in the race to std output
     */
    public void displayStarters() {
        if (racingBoats.size() > 0) {
            System.out.println("#############################################" + "\n" + "\n" + "Starting boats in the race:");
            for (Boat boat : racingBoats) {
                System.out.println(boat.getBoatName() + ", with a speed of: " + boat.getBoatSpeed() + " kmph.");
            }
        }else {
            System.out.println("No boats in race");
        }
    }

    /**
     * Display the the finishing order of boats in the current race to std output
     */
    public void displayFinishers() {
        if (racingBoats.size() > 0) {
            System.out.println();
            System.out.println("#############################################" + "\n" + "\n" + "Finishing Order:");
            for (int i = 0; i < finishingOrder.size(); i++) {
                System.out.printf("%d. %s\n", i+1, finishingOrder.get(i));
            }
            System.out.println("\n#############################################");
        }
    }

    /**
     * Adds the boats in the current race from the regatta
     * @param numberOfBoats Number of boats to be added from the current regatta to the race
     * @param competitors List of current boats in the regatta
     */
    public void addRacingBoats(int numberOfBoats, ArrayList<Boat> competitors){
        Random random = new Random();
        if (numberOfBoats <= competitors.size()){
            for (int i = 0; i < numberOfBoats; i++){
                int nextBoatToAdd = random.nextInt(competitors.size());
                racingBoats.add(competitors.get(nextBoatToAdd));
                if (competitors.get(nextBoatToAdd).getBoatSpeed() < slowestBoatSpeed){
                    slowestBoatSpeed = competitors.get(nextBoatToAdd).getBoatSpeed();
                }
                competitors.remove(nextBoatToAdd);
            }
        }
    }

    /**
     * Randomly selects a race winner, from the boats competing in the race.
     */
    public void reportEventPositions() {
        System.out.println("\n" + "#############################################" + "\n");

        // ArrayList of events in sorted form.
        ArrayList<EventStorage> orderedRaceEvents = generateEventQueue();
        float prevEventTime = 0;

        for (EventStorage event : orderedRaceEvents){

            // Delay the requisite time.
            if (event.eventTime > 0){
                float delayLength = event.eventTime - prevEventTime;

                float delay = 60000 * playbackSpeedMultiplier * delayLength * 60;

                try {
                    Thread.sleep((long) delay);
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
            }
            prevEventTime = event.eventTime;

            // Report the event.
            if (event.eventName == "Finish"){
                System.out.println(event.boatName + " has crossed the " + event.eventName + " Line!");
                finishingOrder.add(event.boatName);
            } else if (event.eventName == "Start") {
                System.out.println(event.boatName + " has crossed the " + event.eventName + " Line! The boat has a heading of: " +
                        event.nextHeading + "degrees.");
            } else {
                System.out.println(event.boatName + " has rounded " + event.eventName + ". The boat has a heading of: " +
                        event.nextHeading + "degrees.");
            }
            System.out.println();
        }
    }

    /**
     * Gets a playback duration from std input,
     * must be either 0, 1 or 5 minutes.
     */
    public void setRacePlaybackDuration(){
        HashSet<Integer> validRaceLength = new HashSet<>(Arrays.asList(0, 1, 5));
        Scanner input = new Scanner(System.in);
        System.out.println("What duration do you want the race to be in minutes?");
        while (!(validRaceLength.contains(racePlaybackDuration))){
            try {
                racePlaybackDuration = input.nextInt();
            }catch (InputMismatchException ex){
                System.out.println("Input must be a whole number.");
                input.next();
            }
            if(!(validRaceLength.contains(racePlaybackDuration))){
                System.out.println("Please ensure that the number is a valid race duration (0, 1 or 5 minutes long).");
            }
        }
        playbackSpeedMultiplier = racePlaybackDuration / (60*(Regatta.totalRaceDistance / slowestBoatSpeed));
    }

    public void addEvents(ArrayList<Event> events) {
        this.raceEvents = events;
    }

    private ArrayList<EventStorage> generateEventQueue(){

        // Need to create an arraylist that can take the Event.name, boat.name, time, and event.nextHeading.
        ArrayList<EventStorage> events = new ArrayList<>();

        float cumulativeRaceDist = 0;
        for (Event event : raceEvents) {
            cumulativeRaceDist += event.distToPrevEvent;
            for (Boat boat: racingBoats) {
                float eventTime = cumulativeRaceDist/boat.getBoatSpeed();
                events.add(new EventStorage(boat.getBoatName(), event.getEventName(), eventTime, event.nextHeading));
            }
        }
        Collections.sort(events, (e1, e2) -> String.valueOf(e1.getEventTime()).compareTo(String.valueOf(e2.getEventTime())));
        return events;
    }

    public float getSlowestBoatSpeed() {
        return slowestBoatSpeed;
    }

    public ArrayList<Boat> getRacingBoats() {
        return racingBoats;
    }
}
