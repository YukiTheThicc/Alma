package alma.utils;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class IdStackTest {

    @Test
    void testPop() {
        int expected = 1;
        IdStack sut = new IdStack(4);
        sut.push(4);
        sut.push(2);
        sut.push(3);
        sut.push(1);
        assertEquals(expected, sut.pop());
    }

    @Test
    void testPush() {
        int[] expected = {4, 2, 3, 1};
        IdStack sut = new IdStack(4);
        sut.push(4);
        sut.push(2);
        sut.push(3);
        sut.push(1);
        assertArrayEquals(expected, sut.getIds());
    }

    @Test
    void testGrow() {
        int[] expected = {4, 2, 3, 1, 5};
        expected = Arrays.copyOf(expected, 8);
        IdStack sut = new IdStack(4);
        sut.push(4);
        sut.push(2);
        sut.push(3);
        sut.push(1);
        sut.push(5);
        assertArrayEquals(expected, sut.getIds());
    }

    @Test
    void testEmpty() {
        int expected = -1;
        IdStack sut = new IdStack(4);
        sut.push(4);
        sut.push(2);
        sut.push(3);
        sut.push(1);
        sut.pop();
        sut.pop();
        sut.pop();
        sut.pop();
        assertEquals(expected, sut.pop());
    }
}