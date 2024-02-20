package alma;

import alma.api.AlmaCore;
import alma.api.AlmaComponent;
import alma.api.AlmaListener;
import alma.events.AlmaEvent;
import alma.events.AlmaEventType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Alma is the entry point to the ECS. It implements the interface AlmaCore that defines all the actions that Alma must be able to
 * perform.
 *
 * @author Santiago Barreiro
 */
public final class Alma implements AlmaCore {

    // ATTRIBUTES
    private final CompositionManager compositionManager;
    private final ConcurrentHashMap<CompositionHash, Partition> partitions;     // Partition map
    private final IdHandler idHandler;                                          // Manager of IDs for the pool and partitions
    private int pCount;                                                         // Amount of partitions currently stored
    private final Map<AlmaEventType, AlmaList<AlmaListener>> listeners;
    private final AlmaList<AlmaEvent> events;

    // CONSTRUCTORS
    public Alma() {
        this.compositionManager = new CompositionManager();
        this.partitions = new ConcurrentHashMap<>();
        this.idHandler = new IdHandler();
        this.listeners = new ConcurrentHashMap<>();
        this.events = new AlmaList<>();
    }

    // METHODS
    @Override
    public long createEntity() {
        return 1L;
    }

    @Override
    public long createEntity(AlmaComponent[] c) {
        return 0;
    }

    @Override
    public void addComponent(long e, AlmaComponent[] c) {

    }

    @Override
    public void removeComponent(long e, AlmaComponent[] c) {

    }

    @Override
    public void removeEntity(long e) {

    }

    @Override
    public void addListener(AlmaListener listener) {

    }
}
