package alma;

import alma.api.AlmaComponent;
import alma.utils.AlmaException;
import alma.utils.IntStack;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Iterator;

/**
 * A partition is a linked data structure that holds the data from a specific entity composition.
 *
 * @author Santiago Barreiro
 */
public final class Partition implements Iterable<Entity> {

    // ATTRIBUTES - MAIN
    private final IdHandler idHandler;                  // IdHandler for the partition
    private final IntStack idStack;                     // IdStack of reusable IDs for the partition
    private final PartitionChunk[] chunksSlots;         // Slots for the partitions chunks
    private final int stride;                           // Stride of the composition stored in the partition
    private final int iid;                              // Internal ID of the partition
    private int usedChunks;                             // Current amount of used chunks
    private int size;                                   // Current amount of stored entities

    public Partition(int iid, IdHandler idHandler) {
        this(iid, idHandler, 1, new Class<?>[0], new byte[]{1});
    }

    public Partition(int iid, IdHandler idHandler, int stride, Class<?>[] componentTypes, byte[] componentIndex) {
        this.idHandler = idHandler;
        this.idStack = new IntStack(idHandler.invalidValue);
        this.chunksSlots = new PartitionChunk[1 << idHandler.partitionBitShift >> idHandler.partitionChunkCapacityBits];
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
            chunk = new PartitionChunk(idHandler.partitionChunkCapacity, stride, idHandler.invalidValue);
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

    /**
     * Returns the current amount of entities stored within this partition
     *
     * @return Amount of entities stored within this partition
     */
    public int size() {
        return size;
    }

    /**
     * Returns the amount of chunks currently in use by this partition
     *
     * @return Amount of chunks currently in use by this partition
     */
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
        int first = idHandler.getPartitionChunkPos(entity) * stride;
        java.lang.System.arraycopy(chunksSlots[chunkId].componentsSlots, first, entityComponents, 0, stride);
        return entityComponents;
    }

    /**
     * Fetches the components from an entity of the specified types and in that order.
     *
     * @param entity ID of the entity to recover the components from
     * @return The target components of the entity
     */
    public AlmaComponent[] fetchEntityComponents(int entity, int[] componentIndex) {
        if (idHandler.getPartitionId(entity) != iid)
            throw new AlmaException("Tried to retrieve components for an entity of a different composition");
        if (idHandler.getItemId(entity) == idHandler.invalidValue)
            throw new AlmaException("Tried to fetch components of a non-existing entity");
        AlmaComponent[] entityComponents = new AlmaComponent[componentIndex.length];
        int chunkId = idHandler.getPartitionChunk(entity);
        int first = idHandler.getPartitionChunkPos(entity) * stride;
        for (int i = 0; i < componentIndex.length; i++) {
            entityComponents[i] = chunksSlots[chunkId].componentsSlots[first + componentIndex[i]];
        }
        return entityComponents;
    }

    public Iterator<Entity> filteredIterator(int[] relevantComponents) {
        return new PartitionIterator(this, relevantComponents);
    }

    @NotNull
    @Override
    public Iterator<Entity> iterator() {
        return new PartitionIterator(this);
    }

    /**
     * Static private class that models a single partition chunk. Each chunk is of a fixed power-of-two size as to make
     * fast bitwise operations and fetch the chunk id and the entity chunk position
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

    public static class PartitionIterator implements Iterator<Entity> {

        // ATTRIBUTES
        private final Partition origin;
        private final int maxChunkCapacity;
        private final int[] filter;
        private int currentEntity;
        private int nextEntityIndex;
        private int chunkIndex;
        private int iteratedEntities;

        // CONSTRUCTORS
        PartitionIterator(Partition origin) {
            this.origin = origin;
            this.nextEntityIndex = 0;
            this.filter = null;
            this.chunkIndex = 0;
            this.currentEntity = origin.idHandler.invalidValue;
            this.iteratedEntities = 0;
            this.maxChunkCapacity = origin.idHandler.partitionChunkCapacity;
        }

        PartitionIterator(Partition origin, int[] filter) {
            this.origin = origin;
            this.nextEntityIndex = 0;
            this.filter = filter;
            this.chunkIndex = 0;
            this.currentEntity = origin.idHandler.invalidValue;
            this.iteratedEntities = 0;
            this.maxChunkCapacity = origin.idHandler.partitionChunkCapacity;
        }

        // METHODS
        public void reset() {
            this.nextEntityIndex = 0;
            this.chunkIndex = 0;
            this.iteratedEntities = 0;
        }

        @Override
        public boolean hasNext() {
            return iteratedEntities < origin.size;
        }

        @Override
        public Entity next() {
            if (origin.usedChunks == 0 || chunkIndex >= origin.usedChunks)
                throw new AlmaException("Tried to iterate onto a non-used chunk slot. ¿Have you forgotten to check with hasNext()?");
            boolean foundNext = false;
            Entity e = null;
            do {
                currentEntity = origin.chunksSlots[chunkIndex].getEntityAt(nextEntityIndex);
                if (currentEntity != origin.idHandler.invalidValue) {
                    foundNext = true;
                    iteratedEntities++;
                    e = filter == null ?
                            new Entity(currentEntity, origin.fetchEntityComponents(currentEntity)) :
                            new Entity(currentEntity, origin.fetchEntityComponents(currentEntity, filter));
                }

                // Handle next entity and chunk index
                nextEntityIndex++;
                if (nextEntityIndex == maxChunkCapacity) {
                    // When the next entity index is equal to the max chunk capacity, the next chunk is selected
                    chunkIndex++;
                    nextEntityIndex = 0;
                }
            } while (!foundNext);
            return e;
        }
    }
}
