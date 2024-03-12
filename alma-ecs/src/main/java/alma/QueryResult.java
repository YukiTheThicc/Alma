package alma;

import alma.utils.AlmaList;

import java.util.Iterator;

/**
 * QueryResult
 *
 * @author Santiago Barreiro
 */
public class QueryResult implements Iterable<>{

    // ATTRIBUTES
    private AlmaList<Partition>[] partitions;
    private Class<?>[] query;

    // CONSTRUCTORS
    public QueryResult() {

    }



    // GETTERS & SETTERS


    // METHODS
    @Override
    public Iterator iterator() {
        return null;
    }
}
