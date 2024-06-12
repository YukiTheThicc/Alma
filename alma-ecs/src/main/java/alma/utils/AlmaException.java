package alma.utils;

/**
 * AlmaException
 *
 * @author Santiago Barreiro
 */
public class AlmaException extends RuntimeException{

    public String message;

    public AlmaException(String message) {
        System.err.println(message);
        this.message = message;
    }
}
