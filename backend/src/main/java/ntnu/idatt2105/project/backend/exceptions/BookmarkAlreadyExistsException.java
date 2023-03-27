package ntnu.idatt2105.project.backend.exceptions;


/**
 * Custom exception which holds a string.
 */
public class BookmarkAlreadyExistsException extends Exception {
    public BookmarkAlreadyExistsException(String message) { super(message);}
}
