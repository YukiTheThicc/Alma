package alma;

import alma.api.AlmaComponent;
import alma.api.AlmaListener;
import alma.events.AlmaEvent;
import alma.events.AlmaEventType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The Director is the entry point to the ECS. It implements the interface AlmaCore that defines all the actions that Alma must be able to
 * perform. The name is provisional for now as it may be subdivided or new classes may take a place on the workflow.
 *
 * @author Santiago Barreiro
 */
public final class PartitionManager {

    // ATTRIBUTES
    private final CompositionManager cm;
    private final ConcurrentHashMap<CompositionHash, Partition> partitions;     // Partition map
    private final IdHandler idHandler;                                          // Manager of IDs for the pool and partitions
    private int pCount;                                                         // Amount of partitions currently stored
    private final Map<AlmaEventType, AlmaList<AlmaListener>> listeners;
    private final AlmaList<AlmaEvent> events;

    // CONSTRUCTORS
    public PartitionManager() {
        this.cm = new CompositionManager();
        this.partitions = new ConcurrentHashMap<>();
        this.idHandler = new IdHandler();
        this.listeners = new ConcurrentHashMap<>();
        this.events = new AlmaList<>();
    }

    // METHODS
    public void createEntity(Class<?>[] composition) {

    }

    public AlmaList<Partition> fetchPartitionsFullJoin(Class<?>[] components) {
        AlmaList<Partition> results = new AlmaList<>();
        cm.queryCompositionsFullJoin(components);
        return results;
    }
}
