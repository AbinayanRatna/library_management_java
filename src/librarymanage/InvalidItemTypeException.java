package librarymanage;

/**
 * Custom exception class thrown when an invalid item type is provided.
 * For example, this exception is thrown when attempting to add an item
 * that is not of the allowed types, such as "book" or "dvd".
 */
public class InvalidItemTypeException extends Exception {

    /**
     * Constructor for the InvalidItemTypeException.
     * It takes a custom error message as a parameter and passes it
     * to the parent Exception class.
     *
     * @param message A string that describes the reason the exception was thrown.
     */
    public InvalidItemTypeException(String message) {
        super(message); // Call the parent class constructor with the custom message
    }
}