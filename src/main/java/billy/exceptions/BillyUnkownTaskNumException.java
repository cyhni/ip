package billy.exceptions;

/**
 * The BillyUnkownTaskNumException class represents an exception when the task number is not found.
 */
public class BillyUnkownTaskNumException extends BillyException {

    /**
     * Constructs a BillyUnkownTaskNumException object.
     *
     * @param index The task number that is not found.
     */
    public BillyUnkownTaskNumException(String index) {
        super("Billy does not have task number " + index + "...");
    }
}
