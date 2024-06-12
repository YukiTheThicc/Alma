package alma;

import alma.api.AlmaComponent;

/**
 * Entity
 *
 * @author Santiago Barreiro
 */
public class Entity {

    // ATTRIBUTES
    public final int id;
    public final AlmaComponent[] components;

    // CONSTRUCTORS
    public Entity(int id, AlmaComponent[] components) {
        this.id = id;
        this.components = components;
    }
}
