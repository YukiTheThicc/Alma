package alma.structures;

import alma.TestUtils;
import alma.api.AlmaComponent;
import alma.utils.AlmaList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CompositionManagerTest {

    private record C1() implements AlmaComponent {
    }

    private record C2() implements AlmaComponent {
    }

    private record C3() implements AlmaComponent {
    }

    private record C4() implements AlmaComponent {
    }

    private record C5() implements AlmaComponent {
    }

    private record C6() implements AlmaComponent {
    }

    private record C7() implements AlmaComponent {
    }

    private record C8() implements AlmaComponent {
    }

    @BeforeEach
    public void setup() {

    }

    @AfterEach
    public void closeTest() {
        TestUtils.printTestEnd();
    }

    @Test
    public void getIndexTest() {

        TestUtils.printTestHeader("getIndexTest");
        int expectedEmpty = 1;          // Index starts at 1
        int expectedBasic = 4;          // Expected to add 3, so index should be 1 + 3

        CompositionManager cm = new CompositionManager();
        int actualEmpty = cm.getIndex();

        cm.getComposition(new Class[]{C1.class});
        cm.getComposition(new Class[]{C2.class});
        cm.getComposition(new Class[]{C3.class});
        int actualBasic = cm.getIndex();

        TestUtils.printTestIteration("Empty CM", expectedEmpty, actualEmpty);
        TestUtils.printTestIteration("Partially filled CM", expectedBasic, actualBasic);
        assertAll(
                () -> assertEquals(expectedEmpty, actualEmpty),
                () -> assertEquals(expectedBasic, actualBasic)
        );
    }

    @Test
    public void getCompositionClassTest() {

        TestUtils.printTestHeader("getCompositionClassTest");
        // Test if manually created composition has same components as composition handled by the manager
        CompositionManager cm = new CompositionManager();
        Composition expectedComposition = new Composition(new Class[]{C3.class, C4.class});
        Composition actualComposition = cm.getComposition(new Class[]{C3.class, C4.class});
        Composition secondCompositionC3 = cm.getComposition(new Class[]{C3.class, C2.class});
        AlmaList<Composition> expectedClassCompositions = new AlmaList<>();
        expectedClassCompositions.add(expectedComposition);
        expectedClassCompositions.add(secondCompositionC3);
        expectedClassCompositions.add(new Composition(new Class[]{C1.class, C2.class}));
        AlmaList<Composition> actualClassCompositions = cm.getCompositionsWithClass(C3.class);
        TestUtils.printTestIteration("Composition", expectedComposition, actualComposition);
        TestUtils.printTestIteration("Class compositions", 2, actualClassCompositions.size());
        assertEquals(actualComposition.getComponentTypes(), actualComposition.getComponentTypes());
        assertEquals(2, actualClassCompositions.size());
    }

    @Test
    public void getCompositionObjectTest() {

        TestUtils.printTestHeader("getCompositionObjectTest");
        // Test if manually created composition has same components as composition handled by the manager
        CompositionManager cm = new CompositionManager();
        Composition expectedEquals = new Composition(new Class[]{C3.class, C4.class});
        Composition actualEquals = cm.getComposition(new AlmaComponent[]{new C3(), new C4()});
        TestUtils.printTestIteration("Composition", expectedEquals, actualEquals);
        assertArrayEquals(expectedEquals.getComponentTypes(), actualEquals.getComponentTypes());
    }
}