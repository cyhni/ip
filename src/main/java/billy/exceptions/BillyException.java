package billy.exceptions;

/**
 * The BillyException class represents an exception in the Billy application.
 */
public class BillyException extends Exception {

    /**
     * Constructs a BillyException object.
     *
     * @param message The message of the exception.
     */
    public BillyException(String message) {
        super(message);
    }
}
