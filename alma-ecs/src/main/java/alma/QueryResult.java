package alma;

import alma.api.AlmaComponent;
import alma.compositions.Composition;
import alma.compositions.CompositionHash;

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
    public QueryResult forEach(Consumer<Result> function) {
        for (Composition c : queriedCompositions.values()) {

        }

        return this;
    }

    // UTILITIES

    public record Result(AlmaComponent[] components, int entity) {
    }
}
