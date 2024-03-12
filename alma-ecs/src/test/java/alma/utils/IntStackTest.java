package alma.utils;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class IntStackTest {

    public static final int INVALID_INT = 1 << 31;

    @Test
    void testPop() {
        int expected = 1;
        IntStack sut = new IntStack(INVALID_INT);
        sut.push(4);
        sut.push(2);
        sut.push(3);
        sut.push(1);
        assertEquals(expected, sut.pop());
    }

    @Test
    void testPush() {
        int[] expected = {4, 2, 3, 1};
        expected = Arrays.copyOf(expected, 4);
        IntStack sut = new IntStack(4, INVALID_INT);
        sut.push(4);
        sut.push(2);
        sut.push(3);
        sut.push(1);
        assertArrayEquals(expected, sut.getData());
    }

    @Test
    void testGrow() {
        int[] expected = {4, 2, 3, 1, 5};
        expected = Arrays.copyOf(expected, 8);
        IntStack sut = new IntStack(4, INVALID_INT);
        sut.push(4);
        sut.push(2);
        sut.push(3);
        sut.push(1);
        sut.push(5);
        assertArrayEquals(expected, sut.getData());
    }

    @Test
    void testEmpty() {
        IntStack sut = new IntStack(INVALID_INT);
        sut.push(4);
        sut.push(2);
        sut.push(3);
        sut.push(1);
        sut.pop();
        sut.pop();
        sut.pop();
        sut.pop();
        assertEquals(INVALID_INT, sut.pop());
    }

    @Test
    void testFullEmpty() {
        IntStack sut = new IntStack(INVALID_INT);
        assertEquals(INVALID_INT, sut.pop());
    }
}