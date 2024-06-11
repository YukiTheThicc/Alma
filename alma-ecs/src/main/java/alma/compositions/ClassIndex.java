package alma.compositions;

import alma.api.IClassIndex;
import alma.api.IComponent;
import alma.utils.CompositionHash;

import java.util.Arrays;

/**
 * ClassIndex
 *
 * @author Santiago Barreiro
 */
public class ClassIndex implements IClassIndex {

    // CONSTANTS
    public static final int MAX_COMPONENTS = 1 << 8;

    // ATTRIBUTES
    // Starting value for the index
    private int index = 1;
    // Used to map each class to an Integer value
    private final ClassValue<Integer> classIndex = new ClassValue<>() {
        @Override
        protected Integer computeValue(Class<?> type) {
            return index++;
        }
    };

    // METHODS
    /**
     * Retrieves the index of the type.
     *
     * @param type Class to know the int value of
     * @return The int value of the class
     */
    @Override
    public int get(Class<?> type) {
        return classIndex.get(type);
    }

    /**
     * Retrieves the index for each member of a class array. Returns an int array with the values of the indexes for each
     * class, each one corresponding to the class element on the same position as the original array
     *
     * @param types Array of class types
     * @return Array containing the int indexes of the classes
     */
    @Override
    public int[] getIndexArray(Class<?>[] types) {
        int[] index = new int[MAX_COMPONENTS];
        Arrays.fill(index, -1);
        for (int i = 0; i < types.length; i++) {
            index[classIndex.get(types[i])] = i;
        }
        return index;
    }

    /**
     * Gets the classes from a list of component instances.
     *
     * @param components Array of components instances
     * @return Array of component types
     */
    @Override
    public Class<?>[] getComponentClasses(Object[] components) {
        Class<?>[] componentTypes = new Class<?>[components.length];
        for (int i = 0; i < components.length; i++) componentTypes[i] = components[i].getClass();
        return componentTypes;
    }

    /**
     * Generates the optimized composition hash for the specified component composition. Uses component slots based on
     *
     * @param components List of component types to generate the IntHash
     * @return IntHash that identifies the composition matching the component list
     */
    @Override
    public CompositionHash getCompositionHash(Class<?>[] components) {
        int length = components.length;
        boolean[] classSlots = new boolean[index + length + 1];
        int begin = Integer.MAX_VALUE;
        int end = 0;
        for (Class<?> component : components) {
            int value = classIndex.get(component);
            if (classSlots[value]) {
                throw new IllegalArgumentException("Component types can't repeat within one composition is not allowed");
            } else {
                classSlots[value] = true;
            }
            begin = Math.min(value, begin);
            end = Math.max(value, end);
        }
        return new CompositionHash(classSlots, begin, end, length);
    }

    /**
     * Generates the optimized composition hash for the specified component composition
     *
     * @param components List of components to generate the IntHash
     * @return IntHash that identifies the composition matching the component list
     */
    @Override
    public CompositionHash getCompositionHash(Object[] components) {
        return getCompositionHash(getComponentClasses(components));
    }
}
