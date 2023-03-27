package ntnu.idatt2105.project.backend.exceptions;
/**
 * Custom exception which holds a string.
 */
public class UserNotFoundException extends Exception{
    public UserNotFoundException(String message) {
        super(message);
    }
}
