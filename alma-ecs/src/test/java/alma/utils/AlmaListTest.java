package alma.utils;

import alma.Entity;
import alma.TestUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AlmaListTest {

    private static final int NUM_ITERATIONS_TESTS = 5;

    private class TestEntity extends Entity {
        public int value;

        TestEntity() {
            value = (int) (1 + (Math.random() * 1000));
        }

        TestEntity(int value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof TestEntity) {
                return this.value == ((TestEntity) obj).value;
            }
            return false;
        }

        @Override
        public String toString() {
            return "TestEntity [value=" + value +"]";
        }
    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testSize() {

        /*
         * Create a random number of entities from 1 to 1000, add them to the list and check if the size is equal to the random number
         */
        int expected = 0;
        int actual = 0;

        TestUtils.printTestHeader("testSize");
        for (int i = 0; i < NUM_ITERATIONS_TESTS; i++) {
            expected = (int) (1 + (Math.random() * 1000));
            AlmaList<TestEntity> testList = new AlmaList<TestEntity>();
            for (int j = 0; j < expected; j++) {
                testList.add(new TestEntity());
            }
            actual = testList.size();
            TestUtils.printTestIteration(i, expected, actual);
            assertEquals(expected, actual);
        }
    }

    @Test
    void testMaxSize() {

        /*
         * Creates list with a length power of 2 and fills it with a random amount of elements (lower than the size of
         * the list). Asserts that the max size doesn't change.
         */
        int expected = 64;
        int actual = 0;

        TestUtils.printTestHeader("testMaxSize");
        for (int i = 0; i < NUM_ITERATIONS_TESTS; i++) {
            AlmaList<TestEntity> testList = new AlmaList<TestEntity>(expected);
            int items = (int) (1 + (Math.random() * expected));
            for (int j = 0; j < items; j++) {
                testList.add(new TestEntity());
            }
            actual = testList.maxSize();
            TestUtils.printTestIteration(i, expected, actual);
            assertEquals(expected, actual);
            expected *= 2;
        }
    }

    @Test
    void testIsEmpty() {

        /*
         * If the number of the current iteration is even adds an element to the lists. Asserts that the list is not
         * empty on even iterations, and that it is empty on odd iterations
         */
        boolean expected = true;
        boolean actual = true;

        TestUtils.printTestHeader("testIsEmpty");
        for (int i = 0; i < NUM_ITERATIONS_TESTS; i++) {
            AlmaList<TestEntity> testList = new AlmaList<TestEntity>();

            if (i % 2 == 0) {
                int items = (int) (1 + (Math.random() * 64));
                for (int j = 0; j < items; j++) {
                    testList.add(new TestEntity());
                }
                expected = false;
            } else {
                expected = true;
            }
            actual = testList.isEmpty();
            TestUtils.printTestIteration(i, expected, actual);
            assertEquals(expected, actual);
        }
    }

    @Test
    void testAdd() {

        /*
         * Adds elements to a list and tests if the items are added grows predicatively
         */
        TestEntity expected;
        TestEntity actual;

        TestUtils.printTestHeader("testAdd");
        for (int i = 0; i < NUM_ITERATIONS_TESTS; i++) {

            expected = new TestEntity();
            AlmaList<TestEntity> testList = new AlmaList<TestEntity>();

            // Add random amount of elements to the list
            int items = (int) (1 + (Math.random() * 64));
            for (int j = 0; j < items; j++) {
                testList.add(new TestEntity());
            }

            testList.add(expected);
            actual = testList.get(testList.size() - 1);
            TestUtils.printTestIteration(i, expected, actual);
            assertEquals(expected, actual);
        }
    }

    @Test
    void testAddList() {

        /*
         * Adds elements to a list and tests if the items are added predicatively
         */
        AlmaList<TestEntity> expected;
        AlmaList<TestEntity> actual;

        TestUtils.printTestHeader("testAddList");
        for (int i = 0; i < NUM_ITERATIONS_TESTS; i++) {

            expected = new AlmaList<TestEntity>();

            // Add random amount of elements to the list
            items = (int) (1 + (Math.random() * 64));
            for (int j = 0; j < items; j++) {
                expected.add(new TestEntity());
            }

            testList.add(expected);
            actual = testList.get(testList.size() - 1);
            TestUtils.printTestIteration(i, expected, actual);
            assertEquals(expected, actual);
        }
    }

    @Test
    void testRemove() {
    }

    @Test
    void testGet() {
    }

    @Test
    void testSet() {
    }

    @Test
    void testContains() {
    }

    @Test
    void testGrow() {
    }
}