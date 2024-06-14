package alma;

import alma.archetypes.Archetype;
import alma.archetypes.ArchetypeHash;

import java.util.*;
import java.util.function.Consumer;

/**
 * QueryResult
 *
 * @author Santiago Barreiro
 */
public final class QueryResult {

    // ATTRIBUTES
    private final int[] componentIndex;
    private final Map<ArchetypeHash, Archetype> queriedCompositions;

    // CONSTRUCTORS
    public QueryResult(int[] query, Map<ArchetypeHash, Archetype> queriedCompositions) {
        this.componentIndex = query;
        this.queriedCompositions = queriedCompositions;
    }

    // METHODS
    public QueryResult forEachEntity(Consumer<Entity> function) {
        for (Archetype c : queriedCompositions.values()) {
            Iterator<Entity> filteredIterator = c.getPartition().filteredIterator(componentIndex);
            while (filteredIterator.hasNext()) {
                function.accept(filteredIterator.next());
            }
        }
        return this;
    }
}
