package alma;

import alma.api.AlmaCore;
import alma.api.AlmaComponent;
import alma.structures.Pool;
import alma.structures.CompositionManager;

/**
 * Alma is the entry point to the ECS. It implements the interface AlmaCore that defines all the actions that Alma must be able to
 * perform.
 *
 * @author Santiago Barreiro
 */
public class Alma implements AlmaCore {

    // ATTRIBUTES
    private Pool pool;
    private CompositionManager compositionManager;

    // CONSTRUCTORS
    public Alma() {
        this.compositionManager = new CompositionManager();
        this.pool = new Pool(this, compositionManager);
    }

    // GETTERS & SETTERS
    public CompositionManager getCompositionManager() {
        return compositionManager;
    }

    // METHODS
    @Override
    public long createEntity() {
        return this.pool.createEntity();
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
}
