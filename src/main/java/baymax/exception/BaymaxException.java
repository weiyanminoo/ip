package baymax.exception;

/**
 * Represents an exception specific to the Baymax application.
 * This exception is thrown when an error occurs due to invalid user input
 * or unexpected issues in task processing.
 */
public class BaymaxException extends Exception {
    /**
     * Constructs a BaymaxException with the specified error message.
     *
     * @param message The error message describing the exception.
     */
    public BaymaxException(String message) {
        super(message);
    }
}
