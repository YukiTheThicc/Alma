package alma.structures;

/**
 * A partition is a linked data structure that holds the data from a specific entity composition
 *
 * @author santiago.barreiro
 */
public final class Partition {

    // ATTRIBUTES
    private int id;
    private Object[] components;                    // Component storage
    private Composition compositions;               // Composition stored within this partition
    private Partition next;                         // Next partition
    private Partition previous;                     // Previous partition

    // CONSTRUCTORS
    public Partition() {

    }

    // GETTERS & SETTERS

    // METHODS
}
