package alma;

import alma.api.AlmaCore;
import alma.api.AlmaComponent;
import alma.api.AlmaListener;
import alma.events.AlmaEvent;
import alma.events.AlmaEventType;
import alma.structures.Pool;
import alma.structures.CompositionManager;
import alma.utils.AlmaList;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Alma is the entry point to the ECS. It implements the interface AlmaCore that defines all the actions that Alma must be able to
 * perform.
 *
 * @author Santiago Barreiro
 */
public class Alma implements AlmaCore {

    // ATTRIBUTES
    private final Pool pool;
    private final CompositionManager compositionManager;
    private final Map<AlmaEventType, AlmaList<AlmaListener>> listeners;
    private final AlmaList<AlmaEvent> events;

    // CONSTRUCTORS
    public Alma() {
        this.compositionManager = new CompositionManager();
        this.pool = new Pool(this, compositionManager);
        this.listeners = new ConcurrentHashMap<>();
        this.events = new AlmaList<>();
    }

    // GETTERS & SETTERS
    public CompositionManager getCompositionManager() {
        return compositionManager;
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
