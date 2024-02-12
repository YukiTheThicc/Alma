package alma.structures;

import alma.TestUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CompositionHashTest {

    @BeforeEach
    void setup() {

    }

    @AfterEach
    void close() {
        TestUtils.printTestEnd();
    }

    @Test
    void hashCreation() {
        TestUtils.printTestHeader("hashCreation");
        long expectedHashE = 1;
        long expectedHashPF = 1;
        long expectedHashO = 1;
        int[] aux = new int[] {1, 2, 3};
        for (int i : aux) expectedHashPF = expectedHashPF * CompositionHash.HASH_FACTOR + i;
        aux = new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        for (int i : aux) expectedHashO = expectedHashO * CompositionHash.HASH_FACTOR + i;
        int finalExpectedHashPF = (int) (expectedHashPF ^ (expectedHashPF >>> 32));
        int finalExpectedHashO = (int) (expectedHashO ^ (expectedHashO >>> 32));

        CompositionHash actualE = new CompositionHash(new int[] {});
        CompositionHash actualPF = new CompositionHash(new int[] {1, 2, 3});
        CompositionHash actualO = new CompositionHash(new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10});
        TestUtils.printTestIteration("Empty hash", expectedHashE, actualE.hashCode());
        TestUtils.printTestIteration("Partially filled", finalExpectedHashPF, actualPF.hashCode());
        TestUtils.printTestIteration("Overflowed", finalExpectedHashO, actualO.hashCode());
        assertAll(
                () -> assertEquals(expectedHashE, actualE.hashCode()),
                () -> assertEquals(finalExpectedHashPF, actualPF.hashCode()),
                () -> assertEquals(finalExpectedHashO, actualO.hashCode())
        );
    }

    @Test
    void testTestEquals() {
        TestUtils.printTestHeader("testTestEquals");
        long expectedHashEquals = 1;
        long expectedHashNotEquals = 1;
        long expectedHashEqualsUnordered = 1;
        int[] aux = new int[] {1, 2, 3};
        for (int i : aux) expectedHashEquals = expectedHashEquals * CompositionHash.HASH_FACTOR + i;
        aux = new int[] {1, 2};
        for (int i : aux) expectedHashNotEquals = expectedHashNotEquals * CompositionHash.HASH_FACTOR + i;
        aux = new int[] {1, 2, 3, 4, 5};
        for (int i : aux) expectedHashEqualsUnordered = expectedHashEqualsUnordered * CompositionHash.HASH_FACTOR + i;
        int finalExpectedHashPF = (int) (expectedHashEquals ^ (expectedHashEquals >>> 32));
        int finalExpectedHashO = (int) (expectedHashNotEquals ^ (expectedHashNotEquals >>> 32));
        int finalExpectedHashU = (int) (expectedHashEqualsUnordered ^ (expectedHashEqualsUnordered >>> 32));

        CompositionHash actualEquals = new CompositionHash(new int[] {1, 2, 3});
        CompositionHash actualNotEquals = new CompositionHash(new int[] {1, 2, 3, 4, 5});
        CompositionHash actualEqualsUnordered = new CompositionHash(new int[] {4, 2, 5, 1, 3});
        TestUtils.printTestIteration("Empty hash", expectedHashEquals, actualEquals.hashCode());
        TestUtils.printTestIteration("Partially filled", finalExpectedHashO, actualNotEquals.hashCode());
        TestUtils.printTestIteration("Different order", finalExpectedHashU, actualEqualsUnordered.hashCode());
        assertAll(
                () -> assertEquals(finalExpectedHashPF, actualEquals.hashCode()),
                () -> assertNotEquals(finalExpectedHashO, actualNotEquals.hashCode()),
                () -> assertNotEquals(finalExpectedHashU, actualEqualsUnordered.hashCode())
        );
    }
}