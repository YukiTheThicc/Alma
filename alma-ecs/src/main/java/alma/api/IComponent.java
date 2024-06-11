package alma.api;

/**
 *
 */
public interface IComponent {
    /*
     * Components should always just be data structures, systems are the ones to implement functionality and process the
     * data stored in components. Components should only implement the copy() method to allow the pooled component
     * behaviour.
     */

    void copy(IComponent target);
}
