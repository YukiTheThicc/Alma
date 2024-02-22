package alma;

import alma.Composition;
import alma.IdHandler;
import alma.Partition;
import alma.api.AlmaComponent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PartitionTest {

    private Partition sut;

    private record C1() implements AlmaComponent {
    }

    private record C2() implements AlmaComponent {
    }

    private record C3() implements AlmaComponent {
    }

    @BeforeEach
    void setUp() {
        sut = new Partition(1, new IdHandler(), new Composition(new Class[]{C1.class, C2.class}));
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testAddEntity() {

    }
}