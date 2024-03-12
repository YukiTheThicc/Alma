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
    private Map<CompositionHash, Partition> partitions;

    // CONSTRUCTORS
    private AlmaCore() {
        this.cm = new CompositionManager();
        this.partitions = new ConcurrentHashMap<>();
    }

    // GETTERS & SETTERS


    // METHODS
    public void createEntity(AlmaComponent[] composition) {
        Composition target = cm.getComposition(composition);
    }

    public QueryResult queryEntitiesFullJoin(AlmaComponent[] components) {
        cm.queryCompositionsFullJoin(components);
        return null;
    }

    public QueryResult queryEntitiesInnerJoin(AlmaComponent[] components) {
        return null;
    }

    public static class Factory {
        public static AlmaCore create() {
            return new AlmaCore();
        }
    }
}
