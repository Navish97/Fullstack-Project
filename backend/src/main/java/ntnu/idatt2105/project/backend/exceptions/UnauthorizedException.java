package ntnu.idatt2105.project.backend.exceptions;
/**
 * Custom exception which holds a string.
 */
public class UnauthorizedException extends Exception {
    public UnauthorizedException(String message) {
        super(message);
    }
}
