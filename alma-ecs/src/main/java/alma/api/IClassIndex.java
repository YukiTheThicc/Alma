package alma.api;

/**
 * ClassIndex
 *
 * @author Santiago Barreiro
 */
public interface IClassIndex {
    int get(Class<?> type);

    int[] getIndexArray(Class<?>[] types);

    Class<?>[] getComponentClasses(Object[] components);

    IntKey getCompositionHash(Class<?>[] components);

    IntKey getCompositionHash(Object[] components);

    interface IntKey {

    }
}
