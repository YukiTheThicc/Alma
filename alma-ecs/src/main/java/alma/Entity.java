package alma;

import alma.api.IComponent;

/**
 * Entity
 *
 * @param id ATTRIBUTES
 * @author Santiago Barreiro
 */
public record Entity(int id, IComponent[] components) {
}
