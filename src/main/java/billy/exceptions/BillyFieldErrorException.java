package billy.exceptions;

/**
 * The BillyFieldErrorException class represents an exception when the fields are incorrect.
 */
public class BillyFieldErrorException extends BillyException {

    /**
     * Constructs a BillyFieldErrorException object.
     *
     * @param command The command that has incorrect fields.
     */
    public BillyFieldErrorException(String command) {
        super("Incorrect fields for " + command + " function...\n\nBilly is confused!!!");
    }
}
