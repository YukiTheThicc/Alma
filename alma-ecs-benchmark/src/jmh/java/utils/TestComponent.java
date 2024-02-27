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

    @Override
    public void copy(AlmaComponent target) {
        if (target instanceof TestComponent) {
            ((TestComponent) target).value = this.value;
        }
    }
}
