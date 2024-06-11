package utils;

import alma.api.IComponent;

/**
 * TestComponent
 *
 * @author Santiago Barreiro
 */
public class TestComponent implements IComponent {

    public int value;

    public TestComponent(int value) {
        this.value = value;
    }

    public TestComponent() {
        this.value = -1;
    }

    @Override
    public void copy(IComponent target) {
        if (target instanceof TestComponent) {
            this.value = ((TestComponent) target).value;
        }
    }
}