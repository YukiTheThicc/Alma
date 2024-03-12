package alma;

import alma.api.AlmaComponent;
import alma.utils.AlmaException;
import alma.utils.IntStack;

import java.util.Arrays;

/**
 * A partition is a linked data structure that holds the data from a specific entity composition.
 *
 * @author Santiago Barreiro
 */
public final class Partition {

    // ATTRIBUTES - MAIN
    private final IdHandler idHandler;                  // IdHandler for the partition
    private final IntStack idStack;                     // IdStack of reusable IDs for the partition
    private final PartitionChunk[] chunksSlots;         // Slots for the partitions chunks
    private final int stride;                           // Stride of the composition stored in the partition
    private final int iid;                              // Internal ID of the partition
    private int usedChunks;                             // Current amount of used partitions
    private int size;                                   // Current amount of stored entities

    public Partition(int iid, IdHandler idHandler) {
        this(iid, idHandler, 1);
    }

    public Partition(int iid, IdHandler idHandler, int stride) {
        this.idHandler = idHandler;
        this.idStack = new IntStack(idHandler.invalidValue);
        this.chunksSlots = new PartitionChunk[1 << idHandler.partitionBitShift >> idHandler.partitionLinkCapacityBits];
        this.stride = stride;
        this.iid = iid;
        this.usedChunks = 0;
        this.size = 0;
    }

    // METHODS

    /**
     * UNSAFE. Adds the passed list of components as an entity for this partition. Only checks size of the array
     *
     * @param components Array of component instances
     * @return ID of the new entity
     */
    public int addEntity(AlmaComponent[] components) {
        if (components.length != stride)
            throw new AlmaException("Tried to insert wrong amount of components in partition");

        int id = idStack.pop();
        if (id == idHandler.invalidValue) id = idHandler.generateIID(iid, size);
        int chunkId = idHandler.getPartitionChunk(id);
        int chunkPos = idHandler.getPartitionChunkPos(id);

        PartitionChunk chunk = chunksSlots[chunkId];
        // Lazily create target chunk if it was null before addition
        if (chunk == null) {
            chunk = new PartitionChunk(idHandler.partitionLinkCapacity, stride, idHandler.invalidValue);
            chunksSlots[chunkId] = chunk;
            usedChunks++;
        }
        chunk.setEntity(chunkPos, id, stride, components);
        size++;
        return id;
    }

    /**
     * Immediately sets the specified entity to an invalid value, so it can be reused later.
     *
     * @param entity ID of the entity to remove
     */
    public void removeEntity(int entity) {
        if (idHandler.getPartitionId(entity) != iid)
            throw new AlmaException("Tried to remove an entity with a different partition id");
        int chunkId = idHandler.getPartitionChunk(entity);
        int chunkOPos = idHandler.getPartitionChunkPos(entity);
        chunksSlots[chunkId].entitySlots[chunkOPos] = idHandler.invalidValue;
        idStack.push(entity);
        size--;
    }

    public int size() {
        return size;
    }

    public int usedChunks() {
        return usedChunks;
    }

    /**
     * Fetches the components from an entity. Throws exception if entity is not from this partition.
     *
     * @param entity ID of the entity to recover the components from
     * @return Array of components from the passed entity
     */
    public AlmaComponent[] fetchEntityComponents(int entity) {
        if (idHandler.getPartitionId(entity) != iid)
            throw new AlmaException("Tried to retrieve components for an entity of a different composition");
        if (idHandler.getItemId(entity) == idHandler.invalidValue)
            throw new AlmaException("Tried to fetch components of a non-existing entity");
        AlmaComponent[] entityComponents = new AlmaComponent[stride];
        int chunkId = idHandler.getPartitionChunk(entity);
        int first = idHandler.getPartitionChunkPos(entity);
        java.lang.System.arraycopy(chunksSlots[chunkId].componentsSlots, first * stride, entityComponents, 0, stride);
        return entityComponents;
    }

    /**
     * Static private class that models a single partition link. Partition links are used to create a
     */
    private static class PartitionChunk {

        // ATTRIBUTES
        private final int[] entitySlots;                    // List of entities handled by the partition chunk
        private final AlmaComponent[] componentsSlots;      // List of components handled by the partition chunk

        // CONSTRUCTORS
        private PartitionChunk(int chunkSize, int stride, int invalidValue) {
            this.entitySlots = new int[chunkSize];
            Arrays.fill(this.entitySlots, invalidValue);
            this.componentsSlots = new AlmaComponent[chunkSize * stride];
        }

        // METHODS
        private int getEntityAt(int pos) {
            return entitySlots[pos];
        }

        private void setEntity(int pos, int entity, int stride, AlmaComponent[] components) {
            for (int i = 0; i < stride; i++) {
                int c_pos = pos * stride + i;
                if (componentsSlots[c_pos] != null) componentsSlots[c_pos].copy(components[i]);
                else componentsSlots[c_pos] = components[i];
            }
            entitySlots[pos] = entity;
        }
    }
}
