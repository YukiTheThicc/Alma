package alma;

import alma.api.AlmaComponent;

/**
 * Entity
 *
 * @param id ATTRIBUTES
 * @author Santiago Barreiro
 */
public record Entity(int id, AlmaComponent[] components) {
}
