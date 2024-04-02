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
    private Class<?>[] query;
    private Map<CompositionHash, Composition> queriedCompositions;

    // CONSTRUCTORS
    public QueryResult(Class<?>[] query, Map<CompositionHash, Composition> queriedCompositions) {
        this.query = query;
        this.queriedCompositions = queriedCompositions;
    }

    // METHODS
    public QueryResult forEachEntity(Consumer<Entity> function) {
        for (Composition c : queriedCompositions.values()) {
            Iterator<Entity> i = c.getPartition().iterator();
            while (i.hasNext()) {
                function.accept(i.next());
            }
        }
        return this;
    }

    // UTILITIES

    public record Result(AlmaComponent[] components, int entity) {
    }
}
