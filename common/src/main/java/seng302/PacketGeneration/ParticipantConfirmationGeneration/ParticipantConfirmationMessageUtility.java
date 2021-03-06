package seng302.PacketGeneration.ParticipantConfirmationGeneration;

/**
 * Enum for storing size and indexes of elements in ParticipantConfirmationMessage body
 */
public enum ParticipantConfirmationMessageUtility {
    SOURCE_ID(0, 4),
    CONFIRMATION_STATUS(4, 1);

    private int index;
    private int size;

    ParticipantConfirmationMessageUtility(int index, int size){
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
