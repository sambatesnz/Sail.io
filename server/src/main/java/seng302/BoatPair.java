package seng302;

import seng302.RaceObjects.Boat;

import java.util.Objects;

public final class BoatPair {
    private final Boat boat1;
    private final Boat boat2;
    private final int id1;
    private final int id2;
    private Boat winner;
    private Boat loser;

    public BoatPair(Boat boat1, Boat boat2) {
        this.boat1 = Objects.requireNonNull(boat1);
        this.boat2 = Objects.requireNonNull(boat2);
        this.id1 = (boat1.getSourceId() < boat2.getSourceId()) ? boat1.getSourceId() : boat2.getSourceId();
        this.id2 = (boat1.getSourceId() > boat2.getSourceId()) ? boat1.getSourceId() : boat2.getSourceId();

        //Up to implementer to decided who a winner and loser is
        //Atm its just first boat parsed in
        this.winner = boat1;
        this.loser = boat2;
    }

    public Boat getWinner() {
        return winner;
    }

    public Boat getLoser() {
        return loser;
    }

    public Boat getBoat1() { return boat1; }
    public Boat getBoat2() { return boat2; }

    /**
     * Checks if the objects are  equal.
     * @param obj the object to be compared against
     * @return
     */
    @Override public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        BoatPair other = (BoatPair) obj;
        return (Objects.equals(id1, other.id1) && Objects.equals(id2, other.id2)) || (Objects.equals(id1, other.id2) && Objects.equals(id2, other.id1));
    }

    /**
     * Gets the hashcode for the object
     * @return the hashcode
     */
    @Override public int hashCode() { return Objects.hash(id1, id2); }
}