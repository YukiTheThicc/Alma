package alma;

/**
 * An entity is the representation of an item within Alma. Entities are identifiable externally through an UUID, though
 * for performance reasons they are internally identified with integers. Entities don't store any in-game data nor
 * implement any in-game behaviour.
 *
 * @author santiago.barreiro
 */
public class Entity {

    // ATTRIBUTES
    private final int iid;

    // CONSTRUCTORS
    public Entity() {
        this.iid = 0;
    }

    // GETTERS & SETTERS
    public int getIid() {
        return iid;
    }
}