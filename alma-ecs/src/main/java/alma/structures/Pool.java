package alma.structures;

import alma.api.AlmaCore;
import alma.api.AlmaPool;

import java.util.concurrent.ConcurrentHashMap;

/**
 * AlmaPool is the core of the Alma data structure. Holds a set of linked partitions and its users. The creation of
 * partitions should be handled by this class, meaning that the creation of entities has to go through this.
 *
 * @author Santiago Barreiro
 */
public final class Pool implements AlmaPool {

    // ATTRIBUTES
    private final AlmaCore core;                                                // Alma core that owns this pool
    private final ConcurrentHashMap<CompositionHash, Partition> partitions;     // Partition map
    private final CompositionManager cm;                                        // Composition manager
    private final IdHandler idHandler;                                          // Manager of IDs for the pool and partitions
    private int pCount;                                                         // Amount of partitions currently stored

    // CONSTRUCTORS
    public Pool(AlmaCore core, CompositionManager compositionManager) {
        this.core = core;
        this.partitions = new ConcurrentHashMap<>();
        this.cm = compositionManager;
        this.idHandler = new IdHandler();
        this.pCount = 0;
    }

    // GETTERS & SETTERS
    public AlmaCore getCore() {
        return core;
    }

    public int getPartitionCount() {
        return pCount;
    }

    public IdHandler getIdManager() {
        return idHandler;
    }

    // METHODS

}
