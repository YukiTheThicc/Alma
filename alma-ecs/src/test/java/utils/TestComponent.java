package utils;

import alma.api.AlmaComponent;

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
}