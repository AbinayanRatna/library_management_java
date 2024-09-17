package librarymanage;

/**
 * Custom exception class that is thrown when an item is unavailable.
 * This can occur if the item is already borrowed or not found in the library system.
 * It extends the built-in Exception class and provides a custom error message.
 */
public class ItemUnavailableException extends Exception {

    /**
     * Constructor for the ItemUnavailableException.
     * It takes a custom error message as a parameter and passes it to the parent Exception class.
     * 
     * @param message A string that describes the reason the exception was thrown.
     */
    public ItemUnavailableException(String message) {
        super(message); // Call the parent class constructor with the custom message
    }
}