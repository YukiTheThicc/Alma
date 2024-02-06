package alma;

import java.lang.System;

import alma.api.AlmaComponent;
import alma.structures.CompositionManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TesteruTest {

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

    private record C9() implements AlmaComponent {
    }

    private record C10() implements AlmaComponent {
    }

    private record C11() implements AlmaComponent {
    }

    private record C12() implements AlmaComponent {
    }

    private record C13() implements AlmaComponent {
    }

    private record C14() implements AlmaComponent {
    }

    private record C15() implements AlmaComponent {
    }

    private record C16() implements AlmaComponent {
    }

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void closeTest() {
        TestUtils.printTestEnd();
    }

    @Test
    void test() {
        TestUtils.printTestHeader("test");
        System.out.println(Integer.MAX_VALUE);
        System.out.println(Integer.toString(Integer.MAX_VALUE, 2));
        System.out.println(Integer.toString(Short.MAX_VALUE, 2));
        System.out.println(Integer.toString(1, 2));
        System.out.println(Integer.toString(1 << 1, 2));
        System.out.println(Integer.toString(1 << 2, 2));
        System.out.println(Integer.toString(1 >>> 32, 2));

        CompositionManager cm = new CompositionManager();
        System.out.println(cm.getCompositionHash(new Class[]{C1.class}));
        System.out.println(cm.getCompositionHash(new Class[]{C2.class, C3.class}));
        System.out.println(cm.getCompositionHash(new Class[]{C1.class, C2.class, C3.class, C4.class, C5.class, C6.class, C7.class, C8.class}));
        System.out.println(cm.getCompositionHash(new Class[]{C1.class, C2.class, C3.class, C4.class, C5.class, C6.class, C7.class, C8.class,
                C9.class, C10.class, C11.class, C12.class, C13.class, C14.class, C15.class, C16.class}));

        assertThrows(IllegalArgumentException.class, () -> cm.getCompositionHash(new Class[]{C1.class, C2.class, C2.class}));
    }
}