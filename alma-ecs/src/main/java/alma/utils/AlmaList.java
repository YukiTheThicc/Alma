package alma.utils;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Iterator;

public class AlmaList<T> implements Iterable<T> {

    // ATTRIBUTES
    T[] data;                   // Actual array of data
    protected int size = 0;     // Current amount of elements stored in the list
    private Iterator<T> i;      // Iterator of the pool

    // CONSTRUCTORS
    public AlmaList() {
        this(64);
    }

    @SuppressWarnings("unchecked")
    public AlmaList(int size) {
        data = (T[]) Array.newInstance(Object.class, size);
    }

    public AlmaList(Class<T> type) {
        this(type, 64);
    }

    @SuppressWarnings("unchecked")
    public AlmaList(Class<T> type, int size) {
        data = (T[]) Array.newInstance(type, size);
    }

    // GETTERS & SETTERS
    public int getSize() {
        return size;
    }

    public int getMaxSize() {
        return data.length;
    }

    public boolean isEmpty() {
        return !(size < data.length);
    }

    // METHODS
    /**
     * Adds an element to the list. Grows if the list is full
     * @param e Element to add
     */
    public void add(T e) {
        if (size == data.length) {
            data = Arrays.copyOf(data, (int) (size * 1.5));
        }
        data[size++] = e;
    }

    /**
     * Add all elements from a list into this one
     * @param toCopy List to copy the elements from
     */
    public void addList(AlmaList<T> toCopy) {

    }

    /**
     * Removes an element on the specified index.
     * @param i
     */
    public void remove(int i) {
        // REMOVE ELEMENT ON INDEX
    }

    /**
     * Removes the given entity from the list.
     * @param e Element to remove from the list
     * @return True if the element has been found and removed. False otherwise
     */
    public boolean remove(T e) {
        for (T aux : data) {
            if (aux.equals(e)) {

                return true;
            }
        }
        return false;
    }

    /**
     * Gets the element in the specified index. If the index is out of bounds the pool grows.
     * @param i Index of the element
     * @return Element within the specified index. Null if there is no element in that index
     */
    public T get(int i) {
        if (i <= data.length) {

        }
        return data[i];
    }

    /**
     * Sets the specified index to the passed element. Grows if the index is out of bounds.
     * @param i Index of the element
     * @param e Element to set in the specified index
     */
    public void set(int i, T e) {
        // SET INDEX TO ELEMENT. GROW IF SIZE IS NOT BIG ENOUGH
    }

    /**
     * Tests if element is within the list
     * @param e Element to test
     * @return Ture if element is inside the list, false otherwise
     */
    public boolean contains(T e) {
        for (T d : data) {
            if (d.equals(e)) {
                return true;
            }
        }
        return false;
    }

    pricate void grow() {

    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }

    @Override
    public String toString() {
        StringBuilder m = new StringBuilder("AlmaList(" + data.length + ") : [");
        for (int i = 0; i < size; i++) {
            m.append(data[i]);
            if (i + 1 < size) {
                m.append(", ");
            }
        }
        m.append("]");
        return m.toString();
    }


    private final class ListIterator implements Iterator<T> {

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public T next() {
            return null;
        }

        @Override
        public void remove() {
            Iterator.super.remove();
        }
    }
}


