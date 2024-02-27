package alma.utils;

import java.util.Arrays;

/**
 * Simple ID stack to store reusable IDs
 */
public class IdStack {

    // CONSTANTS
    public static final int DEFAULT_SIZE = 256;

    // ATTRIBUTES
    private int[] ids;
    private int index = -1;
    private final int invalidInt;

    // CONSTRUCTORS
    public IdStack(int invalidInt) {
        this(DEFAULT_SIZE, invalidInt);
    }

    public IdStack(int size, int invalidInt) {
        this.ids = new int[size];
        this.invalidInt = invalidInt;
    }

    // GETTERS
    public int[] getIds() {
        return ids;
    }

    public int getIndex() {
        return index;
    }

    // METHODS

    /**
     * Pops the last ID from the stack. Returns invalid int value for ID if the stack is empty
     *
     * @return Popped ID
     */
    public int pop() {
        if (index == -1) return invalidInt;
        int popped = ids[index];
        index--;
        return popped;
    }

    /**
     * Pushes the ID into the stack
     *
     * @param id ID to push
     */
    public void push(int id) {
        if (index + 1 == ids.length) grow();
        index++;
        ids[index] = id;
    }

    /**
     * Grows the stack by a power of 2
     */
    private void grow() {
        ids = Arrays.copyOf(ids, ids.length << 1);
    }
}
