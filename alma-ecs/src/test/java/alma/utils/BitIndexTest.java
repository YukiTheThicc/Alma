package alma.utils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.TestUtils;

import static org.junit.jupiter.api.Assertions.*;

class BitIndexTest {

    BitIndex sut;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void close() {
        TestUtils.printTestEnd();
    }

    @Test
    void testGet() {

        TestUtils.printTestHeader("testGet");
        sut = new BitIndex(new int[]{3, 14, 31, 44}, 44);
        boolean expectedPos3 = true;
        boolean expectedPos10 = false;
        boolean expectedPos31 = true;
        assertEquals(expectedPos3, sut.get(3));
        assertEquals(expectedPos10, sut.get(10));
        assertEquals(expectedPos31, sut.get(31));
        TestUtils.printTestIteration("Pos 3", expectedPos3, sut.get(3));
        TestUtils.printTestIteration("Pos 10", expectedPos10, sut.get(10));
        TestUtils.printTestIteration("Pos 31", expectedPos31, sut.get(31));
    }

    @Test
    void testEquals() {

        TestUtils.printTestHeader("testEquals");
        sut = new BitIndex(new int[]{3, 14, 31, 44}, 44);
        BitIndex sut1 = new BitIndex(new int[]{14, 44, 3, 31}, 44);
        BitIndex sut2 = new BitIndex(new int[]{14, 44, 2, 31}, 44);
        System.out.println(sut);
        System.out.println(sut1);
        System.out.println(sut2);
        assertEquals(sut, sut1);
        assertNotEquals(sut, sut2);
    }

    @Test
    void testHash() {

        TestUtils.printTestHeader("testHash");
        sut = new BitIndex(new int[]{2}, 2);
        BitIndex sut1 = new BitIndex(new int[]{2, 4}, 4);
        BitIndex sut2 = new BitIndex(new int[]{2, 4, 9}, 9);
        int expected = 35;
        int expected1 = 51;
        int expected2 = 563;
        System.out.println(sut);
        System.out.println(sut1);
        System.out.println(sut2);
        assertEquals(expected, sut.hashCode());
        assertEquals(expected1, sut1.hashCode());
        assertEquals(expected2, sut2.hashCode());
    }
}