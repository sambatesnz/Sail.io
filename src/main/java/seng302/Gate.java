package seng302;

/**
 * A class that represents a gate in a boat race. Specifies where the gate starts and ends.
 */
public class Gate{
    private Landmark Head;
    private Landmark Tail;

    /**
     * Constructor for the gate class
     */
    Gate() {}

    /**
     * Constructor for the gate class
     * @param landmark1 a landmark object, the first part of the gate
     * @param landmark2 a landmark object, the second part of the gate
     */
    public Gate(Landmark landmark1, Landmark landmark2){
        this.Head = landmark1;
        this.Tail = landmark2;
    }

    /**
     * Set the head landmark of the gate
     * @param head the head landmark
     */
    void setHead(Landmark head) {
        Head = head;
    }

    /**
     * Set the tail of the gate
     * @param tail the tail of the gate
     */
    void setTail(Landmark tail) {
        Tail = tail;
    }

    /**
     * Get the head landmark of the gate
     * @return the head landmark
     */
    public Landmark getHead() {
        return Head;
    }

    /**
     * Get the tail landmark of the gate
     * @return the tail landmark
     */
    public Landmark getTail() {
        return Tail;
    }
}
