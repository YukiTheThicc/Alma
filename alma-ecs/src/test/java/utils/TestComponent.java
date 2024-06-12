package utils;

import alma.api.AlmaComponent;

import java.util.Objects;

/**
 * TestComponent
 *
 * @author Santiago Barreiro
 */
public class TestComponent extends AlmaComponent {

    public int value;

    public TestComponent(int value) {
        this.value = value;
    }

    public TestComponent() {
        this.value = -1;
    }

    @Override
    public void copy(AlmaComponent target) {
        if (target instanceof TestComponent) {
            this.value = ((TestComponent) target).value;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestComponent that = (TestComponent) o;
        return value == that.value;
    }

    @Override
    public String toString() {
        return "[ " + this.getClass().getSimpleName() + " | " + this.value + " ]";
    }
}