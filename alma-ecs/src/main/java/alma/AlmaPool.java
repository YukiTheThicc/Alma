package alma;

import alma.api.AlmaComponent;
import alma.compositions.Composition;
import alma.compositions.CompositionHash;
import alma.compositions.CompositionManager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * AlmaCore
 *
 * @author Santiago Barreiro
 */
public final class AlmaPool {

    // ATTRIBUTES
    private IdHandler idHandler;
    private CompositionManager cm;
    private Map<CompositionHash, Partition> partitions;
    private int partitionIndex = -1;

    // CONSTRUCTORS
    private AlmaPool() {
        this.idHandler = new IdHandler();
        this.cm = new CompositionManager();
        this.partitions = new ConcurrentHashMap<>();
    }

    // METHODS
    public int createEntity(AlmaComponent[] composition) {
        CompositionHash targetHash = cm.getCompositionHash(composition);
        // Lazily create the partition for this composition
        if (!partitions.containsKey(targetHash)) {
            Composition targetComposition = cm.getComposition(composition);
            Partition newPartition = new Partition(++partitionIndex, idHandler, targetComposition.getSize());
            partitions.put(targetHash, newPartition);
            targetComposition.setPartition(newPartition);
        }
        Partition targetPartition = partitions.get(targetHash);
        return targetPartition.addEntity(composition);
    }

    public QueryResult queryEntitiesInnerJoin(Class<?>[] componentQuery) {
        Map<CompositionHash, Composition> compositions = cm.queryCompositionsInnerJoin(componentQuery);
        QueryResult result = new QueryResult(componentQuery, compositions);
        return result;
    }

    public static class Factory {
        public static AlmaPool create() {
            return new AlmaPool();
        }
    }
}
