package alma;

import alma.api.IComponent;
import alma.archetypes.ClassIndex;
import alma.archetypes.Archetype;
import alma.archetypes.ArchetypeHash;
import alma.archetypes.ArchetypeMap;

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
    private final ArchetypeMap cm;
    private final Map<ArchetypeHash, Partition> partitions;
    private int partitionIndex = -1;

    // CONSTRUCTORS
    private AlmaPool() {
        this.idHandler = new IdHandler();
        this.classIndex = new ClassIndex();
        this.cm = new ArchetypeMap();
        this.partitions = new ConcurrentHashMap<>();
    }

    // METHODS
    private void createPartition(ArchetypeHash hash, Archetype composition) {
        int[] componentIndex = classIndex.getIndexArray(composition.getComponentTypes());
        Partition newPartition = new Partition(++partitionIndex, idHandler, classIndex, composition.getSize(), componentIndex);
        partitions.put(hash, newPartition);
        composition.setPartition(newPartition);
    }

    public int createEntity(IComponent[] composition) {
        ArchetypeHash targetHash = classIndex.getCompositionHash(composition);
        // Lazily create the partition for this composition
        if (!partitions.containsKey(targetHash)) createPartition(targetHash, cm.getArchetype(composition));
        Partition targetPartition = partitions.get(targetHash);
        return targetPartition.addEntitySafe(composition);
    }

    public QueryResult queryEntitiesWith(Class<?>[] componentQuery) {
        Map<ArchetypeHash, Archetype> compositions = cm.queryCompositionsWith(componentQuery);
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
