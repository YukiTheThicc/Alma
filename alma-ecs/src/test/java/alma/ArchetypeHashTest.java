package alma;

import alma.utils.CompositionHash;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.TestUtils;

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
        long expectedHashPF = 30817;
        long expectedHashO = 986147;

        CompositionHash actualE = new CompositionHash(new boolean[] {false, false}, 1 ,1, 0);
        CompositionHash actualPF = new CompositionHash(new boolean[] {false, true, true, true}, 1 ,3, 3);
        CompositionHash actualO = new CompositionHash(new boolean[] {false, false, true, true, false, true, true}, 2 ,6, 4);
        TestUtils.printTestIteration("Empty hash", expectedHashE, actualE.hashCode());
        TestUtils.printTestIteration("Starts on 2", expectedHashPF, actualPF.hashCode());
        TestUtils.printTestIteration("Has 'holes'", expectedHashO, actualO.hashCode());
        assertAll(
                () -> assertEquals(expectedHashE, actualE.hashCode()),
                () -> assertEquals(expectedHashPF, actualPF.hashCode()),
                () -> assertEquals(expectedHashO, actualO.hashCode())
        );
    }

    @Test
    void testTestEquals() {
        TestUtils.printTestHeader("testTestEquals");
        long expectedHashEquals = 994;

        CompositionHash actualEquals = new CompositionHash(new boolean[] {false, true, true}, 1 ,2, 2);
        CompositionHash actualNotEquals =  new CompositionHash(new boolean[] {false}, 0 ,0, 0);
        TestUtils.printTestIteration("Equals", expectedHashEquals, actualEquals.hashCode());
        TestUtils.printTestIteration("Not equals", actualEquals.hashCode(), actualNotEquals.hashCode());
        assertAll(
                () -> assertEquals(expectedHashEquals, actualEquals.hashCode()),
                () -> assertNotEquals(actualEquals.hashCode(), actualNotEquals.hashCode())
        );
    }
}