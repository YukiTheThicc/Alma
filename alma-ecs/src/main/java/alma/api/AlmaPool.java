package alma.api;

/**
 * API for a component pool.
 */
public interface AlmaPool {

    /**
     * Creates an entity from the component list passed.
     * @param components Array of components that make the new entity
     * @return The ID of the new entity
     */
    public int createEntity(AlmaComponent[] components);

    public int deleteEntity(int entity);

    public int addModifier(AlmaModifier modifier);
}
