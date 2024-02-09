package alma.utils;

import alma.Entity;
import alma.TestUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AlmaListTest {

    private static final int DEFAULT_LIST_SIZE = 16;
    private TestEntity[] entities;

    private class TestEntity extends Entity {
        public int value;

        TestEntity(int value) {
            super(value);
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
        // Create entity data to perform the tests with
        entities = new TestEntity[24];
        entities[0] = new TestEntity(1000000000);
        entities[1] = new TestEntity(1000000001);
        entities[2] = new TestEntity(1000000002);
        entities[3] = new TestEntity(1000000003);
        entities[4] = new TestEntity(1000000004);
        entities[5] = new TestEntity(1000000005);
        entities[6] = new TestEntity(1000000006);
        entities[7] = new TestEntity(1000000007);
        entities[8] = new TestEntity(1000000008);
        entities[9] = new TestEntity(1000000009);
        entities[10] = new TestEntity(1000000010);
        entities[11] = new TestEntity(1000000011);
        entities[12] = new TestEntity(1000000012);
        entities[13] = new TestEntity(1000000013);
        entities[14] = new TestEntity(1000000014);
        entities[15] = new TestEntity(1000000015);
        entities[16] = new TestEntity(1000000016);
        entities[17] = new TestEntity(1000000017);
        entities[18] = new TestEntity(1000000018);
        entities[19] = new TestEntity(1000000019);
        entities[20] = new TestEntity(1000000020);
        entities[21] = new TestEntity(1000000021);
        entities[22] = new TestEntity(1000000022);
        entities[23] = new TestEntity(1000000023);
    }

    @AfterEach
    void closeTest() {
        TestUtils.printTestEnd();
    }

    @Test
    void testSize() {
        /*
         * Test if the size is correct.
         */
        TestUtils.printTestHeader("testSize");

        int expectedSizeEmpty = 0;
        int expectedSizeMinimum = 1;
        int expectedSizeMaximum = 16;
        AlmaList<TestEntity> list = new AlmaList<>(DEFAULT_LIST_SIZE);

        // Test for when list is empty
        int actualSizeEmpty = list.size();

        // Test for when size is 1
        list.add(entities[0]);
        int actualSizeMinimum = list.size();

        // Test for when size is equal to max (15)
        for (int i = 1; i < DEFAULT_LIST_SIZE; i++) {
            list.add(entities[i]);
        }
        int actualSizeMaximum = list.size();

        TestUtils.printTestIteration("Empty", expectedSizeEmpty, actualSizeEmpty);
        TestUtils.printTestIteration("One element", expectedSizeMinimum, actualSizeMinimum);
        TestUtils.printTestIteration("Full", expectedSizeMaximum, actualSizeMaximum);
        assertAll(
                () -> assertEquals(expectedSizeEmpty, actualSizeEmpty),
                () -> assertEquals(expectedSizeMinimum, expectedSizeMinimum),
                () -> assertEquals(expectedSizeMaximum, actualSizeMaximum)
        );
    }

    @Test
    void testMaxSize() {
        TestUtils.printTestHeader("testMaxSize");
    }

    @Test
    void testIsEmpty() {
        TestUtils.printTestHeader("testIsEmpty");
    }

    @Test
    void testAdd() {
        TestUtils.printTestHeader("testAdd");
    }

    @Test
    void testAddList() {
        TestUtils.printTestHeader("testAddList");
    }

    @Test
    void testRemove() {
        TestUtils.printTestHeader("testRemove");
    }

    @Test
    void testGet() {
        TestUtils.printTestHeader("testGet");
    }

    @Test
    void testSet() {
        TestUtils.printTestHeader("testSet");
    }

    @Test
    void testContains() {
        TestUtils.printTestHeader("testContains");
    }

    @Test
    void testGrow() {
        /*
         * Test for the list growing as expected. List should grow when an item is added when full, when getting an item
         * out of bounds and when setting an item out of bounds. It should grow by a factor of 1.5 of either the max size
         * or  the index it is trying to access (get and set), whichever is highest.
         *
         * This functionality of the list is critical, so this method should test thoroughly.
         */

        TestUtils.printTestHeader("testGrow");
    }


}