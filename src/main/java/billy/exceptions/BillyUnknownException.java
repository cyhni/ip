package billy.exceptions;

/**
 * The BillyUnknownException class represents an exception when Billy does not know what the user is talking about.
 */
public class BillyUnknownException extends BillyException {

    /**
     * Constructs a BillyUnknownException object.
     */
    public BillyUnknownException() {
        super("Billy does not know what you are talking about...");
    }
}
