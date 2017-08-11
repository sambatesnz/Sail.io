package seng302.UserInputController;

/**
 * Sections of BoatActionMessage
 */
public enum BoatActionMessageUtility{

    BOAT_ACTION(0, 1),
    BOAT_SOURCE_ID(1, 4);

    private int index;
    private int size;

    BoatActionMessageUtility(int index, int size){
        this.index= index;
        this.size = size;
    }

    public int getIndex(){
        return index;
    }

    public int getSize() {
        return size;
    }

}
