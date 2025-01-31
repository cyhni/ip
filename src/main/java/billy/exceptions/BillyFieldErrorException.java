package billy.exceptions;

public class BillyFieldErrorException extends BillyException {
    public BillyFieldErrorException(String command) {
        super("Incorrect fields for " + command + " function...\n\nBilly is confused!!!");
    }
}
