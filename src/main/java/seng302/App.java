package seng302;

public class App 
{
    public static void main( String[] args )
    {
        Boat b1 = new Boat("Team Tim");
        Boat b2 = new Boat("Team Ollie");
        Race americasCup = new Race();
        americasCup.addBoat(b1);
        americasCup.addBoat(b2);
        americasCup.displayNames();
    }
}
