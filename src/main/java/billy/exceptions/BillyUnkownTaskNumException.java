package billy.exceptions;

public class BillyUnkownTaskNumException extends BillyException {
    public BillyUnkownTaskNumException(String index) {
        super("Billy does not have task number " + index + "...");
    }
}
