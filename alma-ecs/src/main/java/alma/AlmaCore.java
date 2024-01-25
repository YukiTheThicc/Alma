package alma;

import alma.api.Alma;

public class AlmaCore implements Alma {

    // ATTRIBUTES
    private AlmaConfig config;
    private boolean isConfigured = false;

    // CONSTRUCTORS
    public AlmaCore() {
    }

    // GETTERS & SETTERS
    public AlmaConfig getConfig() {
        return config;
    }

    public boolean isConfigured() {
        return isConfigured;
    }

    // METHODS
    public AlmaCore with(AlmaConfig config) {
        this.config = config;
        isConfigured = true;
        return this;
    }
}
