package seng302;

import java.util.Arrays;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Scanner;

public class App
{
    public static void main( String[] args )
    {
        int racePlaybackDuration = -1;  // In minutes
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

        // Creates the Regatta, and (in this case) makes the Regatta an AC35 instance.
        Regatta americasCup = new Regatta();
        americasCup.isAC35();

        // Doing the important stuff.
        Race americasCupRace = new Race();
        americasCupRace.addRacingBoats(2, americasCup.getCompetitors());
        americasCupRace.addEvents(americasCup.getEventList());
        americasCupRace.displayStarters();
        americasCupRace.reportEventPositions();
    }
}
