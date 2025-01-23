public class BillyFieldErrorException extends Exception {
    public BillyFieldErrorException(String command) {
        super("\nIncorrect fields for " + command + " function...\n\nBilly is confused!!!\n");
    }
    
}
