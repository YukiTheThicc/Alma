package alma;

import alma.api.AlmaComponent;
import alma.compositions.Composition;
import alma.compositions.CompositionHash;
import alma.utils.AlmaList;

import java.util.Iterator;
import java.util.Map;

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

    public record Result(AlmaComponent[] components, int entity) {
    }

    public static class ResultsIterator implements Iterator<Result> {

        private Partition currentPartition;
        private int next;
        private Result current;

        public ResultsIterator(Partition firstPartition) {
            currentPartition = firstPartition;
        }

        @Override
        public boolean hasNext() {
            currentPartition.;
        }

        @Override
        public Result next() {
            return null;
        }
    }
}
