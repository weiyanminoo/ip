package baymax.exception;

// For centralised error handling
public class BaymaxException extends Exception {
    public BaymaxException(String message) {
        super(message);
    }
}
