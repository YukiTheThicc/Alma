package alma.api;

/**
 * API for the core instance of Alma and it implements the entry point to interact with the architecture.
 *
 * @author Santiago Barreiro
 */
public interface AlmaCore {

    /**
     * Creates an entity with no components
     * @return the ID of the new entity
     */
    public long createEntity();

    /**
     * Create an entity with the specified components
     * @param c List of components to create the entity with
     * @return The ID of the new entity
     */
    public long createEntity(AlmaComponent[] c);

    /**
     * Adds a set of components to an existing entity
     * @param e ID of the entity to add the components to
     * @param c List of components to add
     */
    public void addComponent(long e, AlmaComponent[] c);

    /**
     * Removes a set of components to an existing entity
     * @param e ID of the entity to remove the components from
     * @param c List of components to remove
     */
    public void removeComponent(long e, AlmaComponent[] c);

    /**
     * Removes an entity from the architecture
     * @param e ID of the entity to be removed
     */
    public void removeEntity(long e);
}
