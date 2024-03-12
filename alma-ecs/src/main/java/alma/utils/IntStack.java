package alma.utils;

import java.util.Arrays;

/**
 * Simple ID stack to store reusable IDs
 */
public class IntStack {

    // CONSTANTS
    public static final int DEFAULT_SIZE = 256;

    // ATTRIBUTES
    private int[] data;
    private int index = -1;
    private final int invalidInt;

    // CONSTRUCTORS
    public IntStack(int invalidInt) {
        this(DEFAULT_SIZE, invalidInt);
    }

    public IntStack(int size, int invalidInt) {
        this.data = new int[size];
        this.invalidInt = invalidInt;
    }

    // GETTERS
    public int[] getData() {
        return data;
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
        int popped = data[index];
        index--;
        return popped;
    }

    /**
     * Pushes the ID into the stack
     *
     * @param data ID to push
     */
    public void push(int data) {
        if (index + 1 == this.data.length) grow();
        index++;
        this.data[index] = data;
    }

    /**
     * Grows the stack by a power of 2
     */
    private void grow() {
        data = Arrays.copyOf(data, data.length << 1);
    }
}
