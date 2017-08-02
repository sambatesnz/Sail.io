package seng302.Client.Messages;

/**
 * Enum used for selecting clients registration type.
 */
public enum RaceRegistrationType {

    VIEW(0),
    PARTICIPATE(1);

    private int registrationType;

    RaceRegistrationType(int registrationType) {
        this.registrationType = registrationType;
    }

    public int getRegistrationType() {
        return this.registrationType;
    }
}
