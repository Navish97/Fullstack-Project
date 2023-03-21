package ntnu.idatt2105.project.backend.exceptions;

/**
 * @author Navid Muradi
 * @project backend
 */
public class UnauthorizedException extends Exception {
    public UnauthorizedException(String message) {
        super(message);
    }
}
