package alma.structures;

import alma.Entity;
import alma.api.AlmaComponent;
import alma.utils.AlmaList;

/**
 * A partition is a linked data structure that holds the data from a specific entity composition.
 *
 * @author Santiago Barreiro
 */
public final class Partition {

    // ATTRIBUTES
    private AlmaList<Entity> entities;              // Entity storage
    private AlmaList<AlmaComponent> components;     // Component storage
    private IdManager IIDManager;                   // ID stack for the composition
    private Composition composition;                // Composition stored within this partition
    private Partition next;                         // Next partition
    private Partition previous;                     // Previous partition
    private boolean packed;                         // Packed partition flag

    // CONSTRUCTORS
    public Partition(Composition composition, Partition previous, Partition next) {
        this.entities = new AlmaList<>();
        this.components = new AlmaList<>();
        this.composition = composition;
        this.next = next;
        this.previous = previous;
        this.packed = false;
    }

    public Partition(Composition composition, Partition previous, Partition next, boolean packed) {
        this.entities = new AlmaList<>();
        this.components = new AlmaList<>();
        this.composition = composition;
        this.next = next;
        this.previous = previous;
        this.packed = packed;
    }

    // GETTERS & SETTERS
    public AlmaList<Entity> getEntities() {
        return entities;
    }

    public AlmaList<AlmaComponent> getComponents() {
        return components;
    }

    public Composition getComposition() {
        return composition;
    }

    public Partition getNext() {
        return next;
    }

    public Partition getPrevious() {
        return previous;
    }

    public boolean isPacked() {
        return packed;
    }

    // METHODS


}
