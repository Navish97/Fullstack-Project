package ntnu.idatt2105.project.backend.exceptions;
/**
 * Custom exception which holds a string.
 */
public class UserAlreadyExistsException extends Exception {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
