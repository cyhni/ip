public class BillyUnkownTaskNumException extends Exception {
    public BillyUnkownTaskNumException(String index) {
        super("\nBilly does not have task number " + index + "...\n");
    }
    
}
