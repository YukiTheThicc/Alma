package alma.utils;

import alma.Entity;
import alma.TestUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AlmaListTest {

    private static final int NUM_ITERATIONS_TESTS = 10;

    private class TestEntity extends Entity {
        public int value;

        TestEntity() {
            value = (int) (1 + (Math.random() * Integer.MAX_VALUE));
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
            return "TestEntity [value=" + value + "]";
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
            assertEquals(expected, actual);
            TestUtils.printTestIteration(i, expected, actual);
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
            assertEquals(expected, actual);
            TestUtils.printTestIteration(i, expected, actual);
            expected += 64;
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
            assertEquals(expected, actual);
            TestUtils.printTestIteration(i, expected, actual);
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
            assertEquals(expected, actual);
            TestUtils.printTestIteration(i, expected, actual);
        }
    }

    @Test
    void testAddList() {

        /*
         * Adds elements to a list and tests if the items are added predicatively
         */
        AlmaList<TestEntity> expected;
        AlmaList<TestEntity> actual;
        AlmaList<TestEntity> toAdd;
        int initialListSize = 16;

        TestUtils.printTestHeader("testAddList");
        for (int i = 0; i < NUM_ITERATIONS_TESTS; i++) {

            toAdd = new AlmaList<TestEntity>(initialListSize);
            actual = new AlmaList<TestEntity>(initialListSize);
            expected = new AlmaList<TestEntity>(initialListSize);

            // Add random amount of elements to the list to add
            int items = (int) (1 + (Math.random() * initialListSize));
            for (int j = 0; j < items; j++) {
                TestEntity newEntity = new TestEntity();
                toAdd.add(newEntity);
                expected.add(newEntity);
            }

            // Add random amount of elements to the list to the actual list
            items = (int) (1 + (Math.random() * initialListSize));
            for (int j = 0; j < items; j++) {
                TestEntity newEntity = new TestEntity();
                actual.add(newEntity);
                expected.add(newEntity);
            }

            // The list to add is added to actual
            actual.addList(toAdd);

            // Check if expected and actual have the same elements
            boolean hasAllElements = true;
            for (int j = 0; j < expected.size(); j++) {
                TestEntity aux = expected.get(j);
                boolean exists = false;
                for (int k = 0; k < actual.size(); k++) {
                    if (aux.equals(actual.get(k))) {
                        exists = true;
                        break;
                    }
                }
                if (!exists) {
                    hasAllElements = false;
                    break;
                }
            }

            final int expectedSize = expected.size();
            final int actualSize = actual.size();
            final boolean finalHasAllElements = hasAllElements;
            assertAll(
                    () -> assertEquals(expectedSize, actualSize),
                    () -> assertTrue(finalHasAllElements)
            );

            TestUtils.printTestIteration(i, expected, actual);
        }
    }

    @Test
    void testRemoveByElement() {

        /*
         * Removes a known element from the list.
         */
        TestUtils.printTestHeader("testRemoveByElement");
        for (int i = 0; i < NUM_ITERATIONS_TESTS; i++) {

            // SETUP TO REMOVE BY ELEMENT
            TestEntity expectedEntityRemoved = new TestEntity();
            AlmaList<TestEntity> testList = new AlmaList<>();

            // Add random amount of elements to the list and add the expected entity on known random position
            int items = (int) (1 + (Math.random() * 64));
            int knownIndex = (int) (0 + (Math.random() * (items - 1)));
            for (int j = 0; j < items; j++) {
                if (knownIndex == j) {
                    testList.add(expectedEntityRemoved);
                } else {
                    testList.add(new TestEntity());
                }
            }

            boolean actualEntityRemovedBoolean = testList.remove(expectedEntityRemoved);
            boolean expectedEntityRemovedBoolean = true;
            assertEquals(expectedEntityRemovedBoolean, actualEntityRemovedBoolean);
            TestUtils.printTestIteration(i, expectedEntityRemovedBoolean, actualEntityRemovedBoolean);
        }
    }

    @Test
    void testRemoveByElementNotExists() {

        /*
         * Removes a known element from the list.
         */
        TestUtils.printTestHeader("testRemoveByElementNotExists");
        for (int i = 0; i < NUM_ITERATIONS_TESTS; i++) {

            // SETUP TO REMOVE BY ELEMENT
            TestEntity expectedEntityRemoved = new TestEntity();
            AlmaList<TestEntity> testList = new AlmaList<>();

            // Add random amount of elements to the list and add the expected entity on known random position
            int items = (int) (1 + (Math.random() * 64));
            for (int j = 0; j < items; j++) {
                testList.add(new TestEntity());
            }

            boolean actualEntityRemovedBoolean = testList.remove(expectedEntityRemoved);
            boolean expectedEntityRemovedBoolean = false;
            assertEquals(expectedEntityRemovedBoolean, actualEntityRemovedBoolean);
            TestUtils.printTestIteration(i, expectedEntityRemovedBoolean, actualEntityRemovedBoolean);
        }
    }

    @Test
    void testRemoveByIndex() {

        /*
         * Removes a known index from the list.
         */
        TestUtils.printTestHeader("testRemoveByIndex");
        for (int i = 0; i < NUM_ITERATIONS_TESTS; i++) {

            // SETUP TO REMOVE BY INDEX
            TestEntity expectedEntityRemoved = new TestEntity();
            AlmaList<TestEntity> testList = new AlmaList<>();

            // Add random amount of elements to the list and add the expected entity on known random position
            int items = (int) (1 + (Math.random() * 64));
            int knownIndex = (int) (0 + (Math.random() * (items - 1)));
            for (int j = 0; j < items; j++) {
                if (knownIndex == j) {
                    testList.add(expectedEntityRemoved);
                } else {
                    testList.add(new TestEntity());
                }
            }

            System.out.println(knownIndex);
            System.out.println(testList.size);
            TestEntity actualEntityRemoved = testList.remove(knownIndex);
            assertEquals(expectedEntityRemoved, actualEntityRemoved);
            TestUtils.printTestIteration(i, expectedEntityRemoved, actualEntityRemoved);
        }
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