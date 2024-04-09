package alma;

import alma.api.AlmaComponent;
import alma.compositions.Composition;
import alma.utils.CompositionHash;

import java.util.*;
import java.util.function.Consumer;

/**
 * QueryResult
 *
 * @author Santiago Barreiro
 */
public final class QueryResult {

    // ATTRIBUTES
    private final Class<?>[] query;
    private final Map<CompositionHash, Composition> queriedCompositions;

    // CONSTRUCTORS
    public QueryResult(Class<?>[] query, Map<CompositionHash, Composition> queriedCompositions) {
        this.query = query;
        this.queriedCompositions = queriedCompositions;
        /*for () {

        }*/
    }

    // METHODS
    public QueryResult forEachEntity(Consumer<Entity> function) {
        for (Composition c : queriedCompositions.values()) {
            Iterator<Entity> filteredIterator = c.getPartition().filteredIterator(query);
            for (Entity entity : c.getPartition()) {
                function.accept(entity);
            }
        }
        return this;
    }
}
