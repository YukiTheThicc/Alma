package alma.structures;

import alma.Entity;
import alma.api.AlmaComponent;
import alma.utils.AlmaList;

/**
 * A partition is a linked data structure that holds the data from a specific entity composition
 *
 * @author Santiago Barreiro
 */
public final class Partition {

    // ATTRIBUTES
    private int id;                                 // ID of the partition
    private AlmaList<Entity> entities;              // Entity storage
    private AlmaList<AlmaComponent> components;     // Component storage
    private Composition compositions;               // Composition stored within this partition
    private Partition next;                         // Next partition
    private Partition previous;                     // Previous partition

    // CONSTRUCTORS
    public Partition() {

    }

    // GETTERS & SETTERS

    // METHODS
}
