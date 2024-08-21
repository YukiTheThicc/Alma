package alma.utils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.TestUtils;

import static org.junit.jupiter.api.Assertions.*;

class BitFlagTest {

    BitFlag sut;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void close() {
        TestUtils.printTestEnd();
    }

    @Test
    void testGetFlag() {

        TestUtils.printTestHeader("testGetFlag");
        sut = new BitFlag(new int[]{3, 14, 31, 44});
        boolean expectedPos3 = true;
        boolean expectedPos10 = false;
        boolean expectedPos31 = true;
        assertEquals(expectedPos3, sut.getFlag(3));
        assertEquals(expectedPos10, sut.getFlag(10));
        assertEquals(expectedPos31, sut.getFlag(31));
        TestUtils.printTestIteration("Pos 3", expectedPos3, sut.getFlag(3));
        TestUtils.printTestIteration("Pos 10", expectedPos10, sut.getFlag(10));
        TestUtils.printTestIteration("Pos 31", expectedPos31, sut.getFlag(31));
        TestUtils.printTestIteration("BitsSet", 4, sut.getBitsSet());
    }

    @Test
    void testSetFlag() {

        TestUtils.printTestHeader("testSetFlag");
        sut = new BitFlag(new int[]{3});
        sut.setFlag(3, false);
        sut.setFlag(33, true);
        sut.setFlag(65, true);
        boolean expectedPos3 = false;
        boolean expectedPos33 = true;
        boolean expectedPos65 = true;
        assertEquals(expectedPos3, sut.getFlag(3));
        assertEquals(expectedPos33, sut.getFlag(33));
        assertEquals(expectedPos65, sut.getFlag(65));
        TestUtils.printTestIteration("Pos 3", expectedPos3, sut.getFlag(3));
        TestUtils.printTestIteration("Pos 33", expectedPos33, sut.getFlag(33));
        TestUtils.printTestIteration("Pos 65", expectedPos65, sut.getFlag(65));
        TestUtils.printTestIteration("BitsSet", 2, sut.getBitsSet());
    }

    @Test
    void testFlipFlag() {

        TestUtils.printTestHeader("testFlipFlag");
        sut = new BitFlag(new int[]{3, 65});
        sut.flipFlag(3);
        sut.flipFlag(33);
        boolean expectedPos3 = false;
        boolean expectedPos33 = true;
        boolean expectedPos65 = true;
        assertEquals(expectedPos3, sut.getFlag(3));
        assertEquals(expectedPos33, sut.getFlag(33));
        assertEquals(expectedPos65, sut.getFlag(65));
        TestUtils.printTestIteration("Pos 3", expectedPos3, sut.getFlag(3));
        TestUtils.printTestIteration("Pos 33", expectedPos33, sut.getFlag(33));
        TestUtils.printTestIteration("Pos 65", expectedPos65, sut.getFlag(65));
        TestUtils.printTestIteration("BitsSet", 2, sut.getBitsSet());
    }

    @Test
    void getNext() {
        TestUtils.printTestHeader("testGetNext");
        int[] expected = new int[]{3, 18, 65, 125};
        int[] actual = new int[4];
        sut = new BitFlag(expected);
        final int expectedIterations = 8;
        int iterations = 0;

/*
        int i = 0;
        int next = 0;
        int segment = sut.getSegment(i);
        int previous = 0;
        while (i < sut.getSegmentCount()) {
            next = Integer.numberOfLeadingZeros(segment);
            if (next == 32) {
                i++;
                previous = 0;
                if (i < sut.getSegmentCount()) segment = sut.getSegment(i);
            } else {
                segment = segment << (next + 1);
                System.out.println(i * 32 + next + previous);
                previous += next + 1;
            }
            iterations++;
        }*/

        int i = 0;
        int next = 0;
        int segment = sut.getSegment(i);
        int previous = 0;
        while (i < sut.getSegmentCount()) {
            next = Integer.numberOfLeadingZeros(segment);
            if (next == 32) {
                i++;
                previous = 0;
                if (i < sut.getSegmentCount()) segment = sut.getSegment(i);
            } else {
                segment = segment << (next + 1);
                System.out.println(i * 32 + next + previous);
                previous += next + 1;
            }
            iterations++;
        }

        assertEquals(expectedIterations, iterations);
        TestUtils.printTestIteration("Iterations", expectedIterations, iterations);
        TestUtils.printTestIteration("Found", expectedIterations, iterations);
    }

    @Test
    void testClearSegment() {
        TestUtils.printTestHeader("testClearSegment");
        sut = new BitFlag(new int[]{33, 45, 55});
        sut.clearSegment(1);
        int actual = sut.getSegment(1);
        assertEquals(0, actual);
        TestUtils.printTestIteration("Cleared segment", TestUtils.intToBinaryString(actual), TestUtils.intToBinaryString(0));
    }
}