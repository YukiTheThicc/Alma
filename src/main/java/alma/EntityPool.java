package alma;

import java.util.HashMap;

public class EntityPool implements DiaEntityPool {

    // ATTRIBUTES
    private Entity[] entities;
    private HashMap<String, DiaEntity> registeredEntities;

    // CONSTRUCTORS
    public EntityPool(Entity[] entities) {
        this.entities = entities;
    }

    // GETTERS & SETTERS

    // METHODS
    @Override
    public void update(float dt) {
        for (Entity entity : entities) {
            entity.update(dt);
        }
    }

    @Override
    public DiaEntity createEntity() {
        return null;
    }

    @Override
    public DiaEntity copyEntity() {
        return null;
    }

    @Override
    public DiaEntity removeEntity() {
        return null;
    }
}
