package seng302.Client.Messages;

/**
 * Enum used for selecting clients registration type.
 */
public enum RaceRegistrationType {

    DEFAULT(-1),
    VIEW(0),
    PARTICIPATE(1);

    private int registrationType;

    RaceRegistrationType(int registrationType) {
        this.registrationType = registrationType;
    }

    public int getRegistrationType() {
        return this.registrationType;
    }

    public static RaceRegistrationType getType(int messageVal){
        RaceRegistrationType messageType = DEFAULT;
        for (RaceRegistrationType type: RaceRegistrationType.values()){
            if (type.getRegistrationType() == messageVal) {
                messageType = type;
            }
        }
        return messageType;
    }
}
