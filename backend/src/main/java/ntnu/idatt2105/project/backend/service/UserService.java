package ntnu.idatt2105.project.backend.service;

import ntnu.idatt2105.project.backend.model.User;
import ntnu.idatt2105.project.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


/**
 * The UserService class provides access to user data stored in the UserRepository.
 * It provides methods for finding and manipulating user data, including finding a user by ID or email,
 * saving a new user, and deleting a user.
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    /**
     * Finds a user by ID.
     *
     * @param id The ID of the user to find.
     * @return An Optional containing the user with the specified ID, or an empty Optional if no such user exists.
     */
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }


    /**
     * Finds a user by email.
     *
     * @param email The email address of the user to find.
     * @return The user with the specified email address.
     * @throws UsernameNotFoundException If no user is found with the specified email address.
     */
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }


    /**
     * Saves a new user.
     *
     * @param user The user to save.
     * @return The saved user.
     */
    public User save(User user) {
        return userRepository.save(user);
    }


    /**
     * Deletes a user by ID.
     *
     * @param id The ID of the user to delete.
     */
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}