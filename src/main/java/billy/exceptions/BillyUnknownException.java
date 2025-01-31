package billy.exceptions;

public class BillyUnknownException extends BillyException {
    public BillyUnknownException() {
        super("Billy does not know what you are talking about...");
    }
}
