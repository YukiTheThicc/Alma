package alma.structures;

import alma.Entity;
import alma.api.AlmaComponent;
import alma.utils.IdStack;

import java.util.Arrays;

/**
 * A partition is a linked data structure that holds the data from a specific entity composition.
 *
 * @author Santiago Barreiro
 */
public final class Partition {

    // CONSTANTS
    public static final int DEFAULT_PARTITION_SIZE = 1024;
    private static final int GROWTH_FACTOR = 2;

    // ATTRIBUTES - MAIN
    private final IdHandler idHandler;
    private final IdStack idStack;
    private Entity[] entities;
    private AlmaComponent[] components;
    private final int stride;

    // ATTRIBUTES - AUXILIARY
    private Entity[] toRemove;

    // CONSTRUCTORS
    public Partition() {
        this(DEFAULT_PARTITION_SIZE);
    }

    public Partition(int size) {
        this(DEFAULT_PARTITION_SIZE, 1);
    }

    public Partition(int size, int stride) {
        this.idHandler = new IdHandler();
        this.idStack = new IdStack(idHandler.invalidValue);
        this.entities = new Entity[size];
        this.components = new AlmaComponent[size * stride];
        this.stride = stride;
    }

    // METHODS
    public void addEntity() {

    }

    /**
     * Grows the partition by the growth factor.
     */
    private void grow(int newSize) {
        entities = Arrays.copyOf(entities, newSize);
        components = Arrays.copyOf(components, newSize * stride);
    }
}
