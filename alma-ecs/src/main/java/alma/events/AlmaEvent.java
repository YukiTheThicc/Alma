package alma.events;

public record AlmaEvent(AlmaEventType type, long entity) {

    public AlmaEvent(AlmaEventType type, long entity) {
        this.type = type;
        this.entity = entity;
    }
}
