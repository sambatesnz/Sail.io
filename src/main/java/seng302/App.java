package seng302;

import java.util.InputMismatchException;
import java.util.Scanner;

public class App
{
    public static void main( String[] args )
    {
        int lengthOfRace = -1;  // In minutes
        Scanner input = new Scanner(System.in);
        System.out.println("What duration do you want the race to be in minutes?");
        while (lengthOfRace < 0){
            try {
                lengthOfRace = input.nextInt();
            }catch (InputMismatchException ex){
                System.out.println("Input must be a whole number.");
                input.next();
            }
            if(lengthOfRace < 0){
                System.out.println("Please ensure that the number is positive.");
            }
        }

        // Creates the Regatta, and (in this case) makes the Regatta an AC35 instance.
        Regatta americasCup = new Regatta();
        americasCup.isAC35();

        // Doing the important stuff
        Race americasCupRace = new Race();
        americasCupRace.addRacingBoats(2, americasCup.getCompetitors());
        americasCupRace.addEvents(americasCup.getEventList());
        americasCupRace.displayStarters();
        americasCupRace.reportEventPositions();
    }
}
