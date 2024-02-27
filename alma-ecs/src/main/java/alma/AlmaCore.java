package alma;

import alma.api.AlmaComponent;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * AlmaCore
 *
 * @author Santiago Barreiro
 */
public class AlmaCore {

    // ATTRIBUTES
    private CompositionManager cm;
    private Map<CompositionHash, Partition> partitionMap;

    // CONSTRUCTORS
    private AlmaCore() {
        this.cm = new CompositionManager();
        this.partitionMap = new ConcurrentHashMap<>();
    }

    // GETTERS & SETTERS


    // METHODS
    public void createEntity(AlmaComponent[] composition) {

    }

    public static class Factory {
        public static AlmaCore create() {
            return new AlmaCore();
        }
    }
}
