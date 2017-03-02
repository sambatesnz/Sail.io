package seng302;

public class App 
{
    public static void main( String[] args )
    {
        Boat b1 = new Boat("Team Tim");
        Boat b2 = new Boat("Team Ollie");
        Boat b3 = new Boat("Team A");
        Boat b4 = new Boat("Team B");
        Boat b5 = new Boat("Team C");
        Race americasCup = new Race();
        americasCup.addBoat(b1);
        americasCup.addBoat(b2);
        americasCup.addBoat(b3);
        americasCup.addBoat(b4);
        americasCup.addBoat(b5);
        americasCup.displayNames();
        americasCup.returnFinishOrder();
    }
}
