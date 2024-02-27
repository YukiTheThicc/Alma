package alma;

import alma.api.AlmaComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.TestComponent;

import static org.junit.jupiter.api.Assertions.*;

class AlmaCoreTest {

    static class C1 extends TestComponent {
        public C1(int value) {
            super(value);
        }

        public C1() {
            super();
        }
    }

    static class C2 extends TestComponent {
        public C2(int value) {
            super(value);
        }

        public C2() {
            super();
        }
    }

    static class C3 extends TestComponent {
        public C3(int value) {
            super(value);
        }

        public C3() {
            super();
        }
    }

    AlmaCore sut;

    @BeforeEach
    void setUp() {
        sut = AlmaCore.Factory.create();
    }

    @Test
    void testCreateEntity() {
        AlmaComponent[] composition = new AlmaComponent[]{new C1(), new C2(), new C3()};
        sut.createEntity(composition);
    }
}