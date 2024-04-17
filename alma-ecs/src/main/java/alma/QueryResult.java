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
    private final int[] componentIndex;
    private final Map<CompositionHash, Composition> queriedCompositions;

    // CONSTRUCTORS
    public QueryResult(int[] query, Map<CompositionHash, Composition> queriedCompositions) {
        this.componentIndex = query;
        this.queriedCompositions = queriedCompositions;
    }

    // METHODS
    public QueryResult forEachEntity(Consumer<Entity> function) {
        for (Composition c : queriedCompositions.values()) {
            Iterator<Entity> filteredIterator = c.getPartition().filteredIterator(componentIndex);
            while (filteredIterator.hasNext()) {
                function.accept(filteredIterator.next());
            }
        }
        return this;
    }
}
