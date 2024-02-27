package alma.utils;

/**
 * AlmaException
 *
 * @author Santiago Barreiro
 */
public class AlmaException extends RuntimeException{

    public String message;

    public AlmaException(String message) {
        this.message = message;
    }
}
