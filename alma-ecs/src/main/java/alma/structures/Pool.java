package alma.structures;

import alma.api.AlmaCore;
import alma.api.AlmaPool;
import alma.utils.AlmaList;

/**
 * AlmaPool is the core of the Alma data structure. Holds a set of linked partitions and its users. The creation of
 * partitions should be handled by this class, meaning that the creation of entities has to go through this.
 *
 * @author Santiago Barreiro
 */
public final class Pool implements AlmaPool {

    // ATTRIBUTES
    private AlmaCore core;                              // Alma core that owns this pool
    private AlmaList<Partition> partitions;             // List of partitions
    private CompositionManager compositionManager;      // Composition manager
    private int partitionCount;                         // Amount of partitions currently stored

    // CONSTRUCTORS
    public Pool(AlmaCore core, CompositionManager compositionManager) {
        this.core = core;
        this.compositionManager = compositionManager;
        this.partitionCount = 0;
        this.partitions = new AlmaList<>();
    }

    // GETTERS & SETTERS
    public AlmaCore getCore() {
        return core;
    }

    public int getPartitionCount() {
        return partitionCount;
    }

    // METHODS
    public long createEntity() {
        return 1L;
    }
}
