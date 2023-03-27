package ntnu.idatt2105.project.backend.service;

import ntnu.idatt2105.project.backend.model.User;
import ntnu.idatt2105.project.backend.repository.UserRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**

 The UserDetailsServiceImpl class implements the Spring Security UserDetailsService interface

 to provide user authentication functionality. It loads user details based on their email address

 from the UserRepository and creates a UserDetails object with the user's credentials and authorities.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    /**

     Constructs a new UserDetailsServiceImpl object with the provided UserRepository.
     @param userRepository the UserRepository to retrieve user data from.
     */
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        UserService userService = new UserService(userRepository);
    }


    /**

     Loads user details based on their email address and creates a UserDetails object

     with the user's credentials and authorities.

     @param email the email address of the user to load details for.

     @return a UserDetails object with the user's credentials and authorities.

     @throws UsernameNotFoundException if the user cannot be found in the UserRepository.
     */
    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = findUserByUsername(email);

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getAuthorities())
                .build();
    }


    /**

     Finds a user in the UserRepository by their email address.
     @param email the email address of the user to find.
     @return the User object with the specified email address.
     @throws UsernameNotFoundException if the user cannot be found in the UserRepository.
     */
    public User findUserByUsername(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }
}