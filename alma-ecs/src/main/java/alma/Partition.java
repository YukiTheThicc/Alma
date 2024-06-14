package alma;

import alma.api.IClassIndex;
import alma.api.IComponent;
import alma.utils.AlmaException;
import alma.utils.IntStack;

import java.util.Arrays;
import java.util.Iterator;

/**
 * A partition is a linked data structure that holds the data from a specific entity composition.
 *
 * @author Santiago Barreiro
 */
public final class Partition {

    // ATTRIBUTES - MAIN
    private final IdHandler idHandler;                  // IdHandler for the partition
    private final IClassIndex classIndex;                // IdHandler for the partition
    private final IntStack idStack;                     // IdStack of reusable IDs for the partition
    private final PartitionChunk[] chunksSlots;         // Slots for the partitions chunks
    private final int[] componentLayout;                // Represents the order of the components inside the chunks
    private final int stride;                           // Stride of the composition stored in the partition
    private final int iid;                              // Internal ID of the partition
    private int usedChunks;                             // Current amount of used chunks
    private int size;                                   // Current amount of stored entities

    public Partition(int iid, IdHandler idHandler, IClassIndex classIndex, int stride, int[] componentLayout) {
        this.idHandler = idHandler;
        this.classIndex = classIndex;
        this.idStack = new IntStack(idHandler.invalidValue);
        this.chunksSlots = new PartitionChunk[1 << idHandler.partitionBitShift >> idHandler.partitionChunkCapacityBits];
        this.componentLayout = componentLayout;
        this.stride = stride;
        this.iid = iid;
        this.usedChunks = 0;
        this.size = 0;
    }

    // METHODS

    private IComponent[] alignComponents(IComponent[] components) {
        if (components.length != stride)
            throw new AlmaException("Tried to insert wrong amount of components in partition");
        IComponent[] alignedComponents = new IComponent[components.length];
        for (IComponent component : components) {
            int pos = componentLayout[classIndex.get(component.getClass())];
            if (pos == -1) throw new AlmaException("Tried to insert a component that doesn't belong in this partition");
            alignedComponents[pos] = component;
        }
        return alignedComponents;
    }

    /**
     * UNSAFE. Adds the passed list of components as an entity for this partition. Assumes that the components are aligned
     * with the component layout for the partition
     *
     * @param components Array of component instances
     * @return ID of the new entity
     */
    public int addEntityUnsafe(IComponent[] components) {
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
     * Adds the passed list of components as an entity for this partition. Orders the components to match the partition
     * layout.
     *
     * @param components Array of component instances
     * @return ID of the new entity
     */
    public int addEntitySafe(IComponent[] components) {

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
        chunk.setEntity(chunkPos, id, stride, alignComponents(components));
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
    public IComponent[] fetchEntityComponents(int entity) {
        if (idHandler.getPartitionId(entity) != iid)
            throw new AlmaException("Tried to retrieve components for an entity of a different composition");
        if (idHandler.getItemId(entity) == idHandler.invalidValue)
            throw new AlmaException("Tried to fetch components of a non-existing entity");
        IComponent[] entityComponents = new IComponent[stride];
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
    public IComponent[] fetchEntityComponents(int entity, int[] componentIndex) {
        if (idHandler.getPartitionId(entity) != iid)
            throw new AlmaException("Tried to retrieve components for an entity of a different composition");
        if (idHandler.getItemId(entity) == idHandler.invalidValue)
            throw new AlmaException("Tried to fetch components of a non-existing entity");

        IComponent[] entityComponents = new IComponent[componentIndex.length];
        int chunkId = idHandler.getPartitionChunk(entity);
        int first = idHandler.getPartitionChunkPos(entity) * stride;
        for (int i = 0; i < componentIndex.length; i++) {
            entityComponents[i] = chunksSlots[chunkId].componentsSlots[first + this.componentLayout[componentIndex[i]]];
        }
        return entityComponents;
    }

    /**
     * Creates an iterator for the Partition that filters which component types are fetched within the partition. Does not
     * check for invalid component indexes or if the components themselves are stored in this composition.
     *
     * @param relevantComponents Array of he indexes of the components to be fetched
     * @return An iterator that will walk through all available entities in the partition and return them with just the desired component types
     */
    public Iterator<Entity> filteredIterator(int[] relevantComponents) {
        return new PartitionIterator(this, relevantComponents);
    }

    /**
     * Static private class that models a single partition chunk. Each chunk is of a fixed power-of-two size as to make
     * fast bitwise operations as to fetch the chunk id and the entity chunk position
     */
    private static class PartitionChunk {

        // ATTRIBUTES
        private final int[] entitySlots;                    // Array of entity IDs handled by this chunk
        private final IComponent[] componentsSlots;         // List of components handled by the partition chunk

        // CONSTRUCTORS
        private PartitionChunk(int chunkSize, int stride, int invalidValue) {
            this.entitySlots = new int[chunkSize];
            Arrays.fill(this.entitySlots, invalidValue);
            this.componentsSlots = new IComponent[chunkSize * stride];
        }

        // METHODS

        /**
         * Gets the entity int value at the desired position. Does not check if the position is valid
         *
         * @param pos Position to fetch the int value of the entity
         * @return Int value (internal ID) of the entity within the desired position
         */
        private int getEntityAt(int pos) {
            return entitySlots[pos];
        }

        /**
         * Sets the entity in the desired position to the desired internal ID and its corresponding components to the passed
         * component array.
         *
         * @param pos        Position of the entity within the chunk
         * @param entity     Internal ID of the entity
         * @param stride     Total offset or size of the component array
         * @param components Array of components corresponding to the new entity
         */
        private void setEntity(int pos, int entity, int stride, IComponent[] components) {
            for (int i = 0; i < stride; i++) {
                int c_pos = pos * stride + i;
                if (componentsSlots[c_pos] != null) componentsSlots[c_pos].copy(components[i]);
                else componentsSlots[c_pos] = components[i];
            }
            entitySlots[pos] = entity;
        }
    }

    /**
     * An iterator class for partitions. By design, it will walk through every instanced chunk of the Partition and return
     * an entity only when the internal ID of the entity is valid. The fetched components for each entity can be selected
     * by using a white list type filter, that will set the index values of the component classes to be fetched.
     */
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

        /**
         * Resets the iterator so it can be reused
         */
        public void reset() {
            this.nextEntityIndex = 0;
            this.chunkIndex = 0;
            this.iteratedEntities = 0;
        }

        /**
         * Checks if there are more entities in the partition
         *
         * @return True if the number of iterated entities is less than the size of the partition
         */
        @Override
        public boolean hasNext() {
            return iteratedEntities < origin.size;
        }

        /**
         * Fetches the next valid entity within the partition. It loops through all the invalid elements in each chunk until
         * it finds one with a valid ID.
         *
         * @return An entity instance with the filtered components
         */
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
