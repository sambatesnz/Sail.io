package seng302;

public class App 
{
    public static void main( String[] args )
    {
        // Creates the Regatta, and (in this case) makes the Regatta an AC35 instance.
        Regatta americasCup = new Regatta();
        americasCup.isAC35();

        //
        Race americasCupRace = new Race();
        americasCupRace.addRacingBoats(2, americasCup.getCompetitors());
        americasCupRace.displayStarters();
        americasCupRace.returnFinishOrder();
    }
}
