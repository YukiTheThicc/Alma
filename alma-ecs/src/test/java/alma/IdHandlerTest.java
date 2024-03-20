package alma;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.TestUtils;

import static org.junit.jupiter.api.Assertions.*;

class IdHandlerTest {

    public IdHandler IDm;

    @BeforeEach
    void setUp() {
        IDm = new IdHandler();
    }

    @AfterEach
    void tearDown() {
        TestUtils.printTestEnd();
    }

    @Test
    void basicTest() {
        TestUtils.printTestHeader("basicTest");
        int expectedPartitionBits = 12;
        int expectedLinkCapacityBits = 12;
        int expectedMaxPartitions = 4095;
        int expectedMaxItems = 524287;
        String expectedPartitionMask =      "01111111111110000000000000000000";
        String expectedItemMask =           "00000000000001111111111111111111";
        String expectedPartitionLinkMask =  "00000000000000000000111111111111";
        String expectedInvalidValue =       "10000000000000000000000000000000";
        TestUtils.printTestIteration("Partition bits", expectedPartitionBits, IDm.partitionBits);
        TestUtils.printTestIteration("Partition link capacity bits", expectedLinkCapacityBits, IDm.partitionChunkCapacityBits);
        TestUtils.printTestIteration("Max partitions", expectedMaxPartitions, IDm.maxPartitions);
        TestUtils.printTestIteration("Max items", expectedMaxItems, IDm.itemsPerPartition);
        TestUtils.printTestIteration("Partition mask", expectedPartitionMask, TestUtils.intToBinaryString(IDm.partitionMask));
        TestUtils.printTestIteration("Item mask", expectedItemMask, TestUtils.intToBinaryString(IDm.itemMask));
        TestUtils.printTestIteration("Partition link mask", expectedPartitionLinkMask, TestUtils.intToBinaryString(IDm.partitionChunkMask));
        TestUtils.printTestIteration("Invalid value", expectedInvalidValue, TestUtils.intToBinaryString(IDm.invalidValue));
        assertAll(
                () -> assertEquals(expectedPartitionBits, IDm.partitionBits),
                () -> assertEquals(expectedLinkCapacityBits, IDm.partitionChunkCapacityBits),
                () -> assertEquals(expectedMaxPartitions, IDm.maxPartitions),
                () -> assertEquals(expectedMaxItems, IDm.itemsPerPartition),
                () -> assertEquals(expectedPartitionMask, TestUtils.intToBinaryString(IDm.partitionMask)),
                () -> assertEquals(expectedItemMask, TestUtils.intToBinaryString(IDm.itemMask)),
                () -> assertEquals(expectedPartitionLinkMask, TestUtils.intToBinaryString(IDm.partitionChunkMask)),
                () -> assertEquals(expectedInvalidValue, TestUtils.intToBinaryString(IDm.invalidValue))
        );
    }

    @Test
    void createIdTest() {
        TestUtils.printTestHeader("createIdTest");
        int expected1 = Integer.parseInt("00000000000010000000000000000001", 2);
        int expected2 = Integer.parseInt("00000000000010000000000000000010", 2);
        int expected3 = Integer.parseInt("00000000000100000000000000000001", 2);
        int expected4 = Integer.parseInt("00000000000100000000000000000010", 2);
        int expected5 = Integer.parseInt("01111111111111111111111111111111", 2);
        int actual1 = IDm.generateIID(1, 1);
        int actual2 = IDm.generateIID(1, 2);
        int actual3 = IDm.generateIID(2, 1);
        int actual4 = IDm.generateIID(2, 2);
        int actual5 = IDm.generateIID(4095, 524287);
        TestUtils.printTestIteration("Partition: 1, Item: 1", TestUtils.intToBinaryString(expected1), TestUtils.intToBinaryString(actual1));
        TestUtils.printTestIteration("Partition: 1, Item: 2", TestUtils.intToBinaryString(expected2), TestUtils.intToBinaryString(actual2));
        TestUtils.printTestIteration("Partition: 2, Item: 1", TestUtils.intToBinaryString(expected3), TestUtils.intToBinaryString(actual3));
        TestUtils.printTestIteration("Partition: 2, Item: 2", TestUtils.intToBinaryString(expected4), TestUtils.intToBinaryString(actual4));
        TestUtils.printTestIteration("Partition: max, Item: max", TestUtils.intToBinaryString(expected5), TestUtils.intToBinaryString(actual5));
        assertAll(
                () -> assertEquals(expected1, actual1),
                () -> assertEquals(expected2, actual2),
                () -> assertEquals(expected3, actual3),
                () -> assertEquals(expected4, actual4),
                () -> assertEquals(expected5, actual5)
        );
    }

    @Test
    void getPartitionId() {
        TestUtils.printTestHeader("getPartitionId");
        int expected1 = Integer.parseInt("1", 2);
        int expectedMax = Integer.parseInt("111111111111", 2);
        int expectedMax2 = Integer.parseInt("111111111111", 2);
        int id1 = IDm.generateIID(1, 1);
        int idMax = IDm.generateIID(4095, 1);
        int idMax2 = IDm.generateIID(4095, 524287);
        int actual1 = IDm.getPartitionId(id1);
        int actualMax = IDm.getPartitionId(idMax);
        int actualMax2 = IDm.getPartitionId(idMax2);
        TestUtils.printTestIteration("Partition: 1, Item: 1", TestUtils.intToBinaryString(expected1), TestUtils.intToBinaryString(actual1));
        TestUtils.printTestIteration("Partition: 4095, Item: 1", TestUtils.intToBinaryString(expectedMax), TestUtils.intToBinaryString(actualMax));
        TestUtils.printTestIteration("Partition: 4095, Item: 524287", TestUtils.intToBinaryString(expectedMax2), TestUtils.intToBinaryString(actualMax2));
        assertAll(
                () -> assertEquals(expected1, actual1),
                () -> assertEquals(expectedMax, actualMax),
                () -> assertEquals(expectedMax2, actualMax2)
        );
    }

    @Test
    void getItemId() {
        TestUtils.printTestHeader("getItemId");
        int expected1 = Integer.parseInt("1", 2);
        int expectedMax = Integer.parseInt("1111111111111111111", 2);
        int expectedMax2 = Integer.parseInt("1111111111111111111", 2);
        int id1 = IDm.generateIID(1, 1);
        int idMax = IDm.generateIID(1, 524287);
        int idMax2 = IDm.generateIID(4095, 524287);
        int actual1 = IDm.getItemId(id1);
        int actualMax = IDm.getItemId(idMax);
        int actualMax2 = IDm.getItemId(idMax2);
        TestUtils.printTestIteration("Partition: 1, Item: 1", TestUtils.intToBinaryString(expected1), TestUtils.intToBinaryString(actual1));
        TestUtils.printTestIteration("Partition: 1, Item: 524287", TestUtils.intToBinaryString(expectedMax), TestUtils.intToBinaryString(actualMax));
        TestUtils.printTestIteration("Partition: 4095, Item: 524287", TestUtils.intToBinaryString(expectedMax2), TestUtils.intToBinaryString(actualMax2));
        assertAll(
                () -> assertEquals(expected1, actual1),
                () -> assertEquals(expectedMax, actualMax),
                () -> assertEquals(expectedMax2, actualMax2)
        );
    }
}