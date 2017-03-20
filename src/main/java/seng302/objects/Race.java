package seng302.objects;

import java.util.*;

/**
 * Created by tjg73 on 2/03/17.
 */
public class Race {

    private ArrayList<Boat> racingBoats = new ArrayList<>();
    private ArrayList<CompoundMark> raceCompoundMarks = new ArrayList<>();
    private ArrayList<String> finishingOrder = new ArrayList<>();
    private int racePlaybackDuration = -1;
    private float playbackSpeedMultiplier = -1;
    private float slowestBoatSpeed = Integer.MAX_VALUE;
    private float totalRaceDistance;

    public Race (ArrayList<CompoundMark> compoundMarks) {
        this.raceCompoundMarks =  compoundMarks;
        GenerateBoats(6);
    }

    public void GenerateBoats(int numBoats){
        racingBoats.clear();
        ArrayList<Boat> allBoats = new ArrayList<>();
        allBoats.add(new Boat("ORACLE TEAM USA", 50));
        allBoats.add(new Boat("Artemis Racing", 55));
        allBoats.add(new Boat("Emirates Team New Zealand", 60));
        allBoats.add(new Boat("Groupama Team France", 65));
        allBoats.add(new Boat("Land Rover BAR", 70));
        allBoats.add(new Boat("SoftBank Team Japan", 75));

        slowestBoatSpeed = Integer.MAX_VALUE;

        Collections.shuffle(allBoats);
        for (int i=0; i< numBoats; i++){
            racingBoats.add(allBoats.get(i));
            float boatSpeed = allBoats.get(i).getBoatSpeed();
            if (slowestBoatSpeed > boatSpeed) {
                slowestBoatSpeed = boatSpeed;
            }
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
     * Gets a playback duration from std input,
     * must be either 0, 1 or 5 minutes.
     */
    public void setRacePlaybackDuration(){
        // TODO: REMOVE THIS BEFORE SUBMISSION.
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
        playbackSpeedMultiplier = racePlaybackDuration / (60*(totalRaceDistance / slowestBoatSpeed));
    }

    public ArrayList<CompoundMark> getRaceCompoundMarks() {
        return raceCompoundMarks;
    }


    public ArrayList<Boat> getRacingBoats() {
        return racingBoats;
    }

    public ArrayList<String> getFinishingOrder() {
        return finishingOrder;
    }

    public void setRacePlaybackDuration(int racePlaybackDuration) {
        this.racePlaybackDuration = racePlaybackDuration;
    }
}
