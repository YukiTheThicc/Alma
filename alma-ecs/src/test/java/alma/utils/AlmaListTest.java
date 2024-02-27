package alma.utils;

import alma.utils.AlmaList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.TestUtils;

import static org.junit.jupiter.api.Assertions.*;

class AlmaListTest {

    private static final int DEFAULT_LIST_SIZE = 16;
    private TestEntity[] entities;

    private static class TestEntity {
        public int value;

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
    void testAdd() {
        /*
         * Test if the size is correct.
         */
        TestUtils.printTestHeader("testAdd");

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

        int expected = 128;
        int expectedDefault = 64;
        AlmaList<TestEntity> test = new AlmaList<>(128);
        AlmaList<TestEntity> testDefault = new AlmaList<>();
        TestUtils.printTestIteration("128", expected, test.maxSize());
        TestUtils.printTestIteration("Default", expectedDefault, testDefault.maxSize());
        assertEquals(expected, test.maxSize());
        assertEquals(expectedDefault, testDefault.maxSize());
    }

    @Test
    void testIsEmpty() {
        TestUtils.printTestHeader("testIsEmpty");
        AlmaList<TestEntity> testEmpty = new AlmaList<>();
        AlmaList<TestEntity> testFilled = new AlmaList<>();
        testFilled.add(new TestEntity(0));
        TestUtils.printTestIteration("128", true, testEmpty.isEmpty());
        TestUtils.printTestIteration("Default", false, testFilled.isEmpty());
        assertTrue(testEmpty.isEmpty());
        assertFalse(testFilled.isEmpty());
    }

    @Test
    void testAddList() {
        TestUtils.printTestHeader("testAddList");
        int expectedSize = 5;
        TestEntity expectedPos4 = entities[4];
        AlmaList<TestEntity> test = new AlmaList<>();
        test.add(entities[0]);
        test.add(entities[1]);
        AlmaList<TestEntity> toAdd = new AlmaList<>();
        toAdd.add(entities[2]);
        toAdd.add(entities[3]);
        toAdd.add(entities[4]);
        test.addList(toAdd);
        TestUtils.printTestIteration("Size", expectedSize, test.size());
        TestUtils.printTestIteration("Element 5", expectedPos4, test.get(4));
        assertEquals(expectedSize, test.size());
        assertEquals(expectedPos4, test.get(4));
    }

    @Test
    void testRemove() {
        TestUtils.printTestHeader("testRemove");
        int expectedSize = 3;
        TestEntity expectedRemoved = entities[4];
        AlmaList<TestEntity> test = new AlmaList<>(TestEntity.class);
        test.add(entities[0]);
        test.add(entities[1]);
        test.add(entities[2]);
        test.add(entities[3]);
        test.add(entities[4]);
        TestEntity actualRemovedIndex = test.remove(4);
        boolean actualRemovedInstance = test.remove(entities[1]);
        TestUtils.printTestIteration("Size", expectedSize, test.size());
        TestUtils.printTestIteration("Removed by index", expectedRemoved, actualRemovedIndex);
        TestUtils.printTestIteration("Removed instance", true, actualRemovedInstance);
        assertEquals(expectedSize, test.size());
        assertEquals(expectedRemoved, actualRemovedIndex);
        assertTrue(actualRemovedInstance);
    }

    @Test
    void testGet() {
        TestUtils.printTestHeader("testGet");
        int expectedNewSize = (int) (20 * 1.5f);
        TestEntity expectedPos1 = entities[1];
        AlmaList<TestEntity> test = new AlmaList<>(16);
        test.add(entities[0]);
        test.add(entities[1]);
        TestUtils.printTestIteration("Element 2", expectedPos1, test.get(1));
        TestUtils.printTestIteration("Element 20", null, test.get(20));
        TestUtils.printTestIteration("Size after get > maxSize", expectedNewSize, test.maxSize());
        assertEquals(expectedPos1, test.get(1));
        assertEquals(expectedNewSize, test.maxSize());
        assertNull(test.get(20));
    }

    @Test
    void testSet() {
        TestUtils.printTestHeader("testSet");
        int expectedNewSize = (int) (20 * 1.5f);
        TestEntity expectedPos1 = entities[1];
        TestEntity expectedPos20 = entities[20];
        AlmaList<TestEntity> test = new AlmaList<>(16);
        test.add(entities[0]);
        test.add(entities[1]);
        test.set(20, entities[20]);
        TestUtils.printTestIteration("Element 2", expectedPos1, test.get(1));
        TestUtils.printTestIteration("Element 20", expectedPos20, test.get(20));
        TestUtils.printTestIteration("Size after get > maxSize", expectedNewSize, test.maxSize());
        assertEquals(expectedPos1, test.get(1));
        assertEquals(expectedPos20, test.get(20));
        assertEquals(expectedNewSize, test.maxSize());
    }

    @Test
    void testContains() {
        TestUtils.printTestHeader("testContains");
        AlmaList<TestEntity> test = new AlmaList<>(16);
        test.add(entities[0]);
        test.add(entities[1]);
        TestUtils.printTestIteration("Contains", true, test.maxSize());
        assertTrue(test.contains(entities[1]));
    }
}