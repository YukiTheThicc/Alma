package alma;

import alma.api.AlmaComponent;
import alma.compositions.ClassIndex;
import alma.compositions.Composition;
import alma.utils.CompositionHash;
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
    private final IdHandler idHandler;
    private final ClassIndex classIndex;
    private final CompositionManager cm;
    private final Map<CompositionHash, Partition> partitions;
    private int partitionIndex = -1;

    // CONSTRUCTORS
    private AlmaPool() {
        this.idHandler = new IdHandler();
        this.classIndex = new ClassIndex();
        this.cm = new CompositionManager();
        this.partitions = new ConcurrentHashMap<>();
    }

    // METHODS
    private void createPartition(CompositionHash hash, Composition composition) {
        int[] componentIndex = classIndex.getIndexArray(composition.getComponentTypes());
        Partition newPartition = new Partition(++partitionIndex, idHandler, composition.getSize(), componentIndex);
        partitions.put(hash, newPartition);
        composition.setPartition(newPartition);
    }

    public int createEntity(AlmaComponent[] composition) {
        CompositionHash targetHash = classIndex.getCompositionHash(composition);
        // Lazily create the partition for this composition
        if (!partitions.containsKey(targetHash)) createPartition(targetHash, cm.getComposition(composition));
        Partition targetPartition = partitions.get(targetHash);
        return targetPartition.addEntitySafe(composition, classIndex);
    }

    public QueryResult queryEntitiesWith(Class<?>[] componentQuery) {
        Map<CompositionHash, Composition> compositions = cm.queryCompositionsWith(componentQuery);
        int[] componentsIndex = new int[componentQuery.length];
        for (int i = 0; i < componentQuery.length; i++) {
            componentsIndex[i] = classIndex.get(componentQuery[i]);
        }
        return new QueryResult(componentsIndex, compositions);
    }

    public static class Factory {
        public static AlmaPool create() {
            return new AlmaPool();
        }
    }
}
