package seng302.UserInput;

/**
 * Practise Message
 */
public enum PracticeMessageMeaning {
    START(0),
    END(1);

    private int meaning;
    PracticeMessageMeaning(int meaning){
        this.meaning = meaning;
    }

    public int getValue() {
        return meaning;
    }

    public static PracticeMessageMeaning getMeaning(int meaning){
        PracticeMessageMeaning messageMeaning = START;
        for (PracticeMessageMeaning currentMeaning: PracticeMessageMeaning.values()){
            if (currentMeaning.getValue() == meaning){
                messageMeaning = currentMeaning;
            }
        }
        return messageMeaning;
    }
}
