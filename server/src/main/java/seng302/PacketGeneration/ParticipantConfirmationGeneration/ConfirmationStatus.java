package seng302.PacketGeneration.ParticipantConfirmationGeneration;

/**
 * Created by sha162 on 14/08/17.
 */
public enum ConfirmationStatus {

    SPECTATING(0),
    PLAYING(1),
    TUTORIAL_MODE(2),
    GHOSTING(3),
    GENERAL_FAILURE(10),
    RACE_FULL_FAILURE(11);  // slots in the race are exceeded

    private int status;

    ConfirmationStatus(int status){
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
