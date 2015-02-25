package com.pearson.app.services;

import com.pearson.app.dao.UserRepository;
import com.pearson.app.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.regex.Pattern;

import static com.pearson.app.services.ValidationUtils.*;

/**
 * Business service for User entity related operations
 */
@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private static final Pattern PASSWORD_REGEX = Pattern.compile("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,}");
    private static final Pattern EMAIL_REGEX = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

    @Autowired
    private UserRepository userRepository;

    /**
     * creates a new user in the database
     * @param user - the new user
     */
    @Transactional
    public void addUser(User user) {
        assertNotBlank(user.getUsername(), "Username cannot be empty.");
        assertMinimumLength(user.getUsername(), 6, "Username must have at least 6 characters.");
        assertNotBlank(user.getEmail(), "Email cannot be empty.");
        assertMatches(user.getEmail(), EMAIL_REGEX, "Invalid email.");
        assertNotBlank(user.getPasswordDigest(), "Password cannot be empty.");
        assertMatches(user.getPasswordDigest(), PASSWORD_REGEX, "Password must have at least 6 characters, with 1 numeric and 1 uppercase character.");
        assertNotBlank(user.getFirstname(), "firstname cannot be empty.");
        assertNotBlank(user.getLastname(), "lastname cannot be empty.");

        if (!userRepository.isUsernameAvailable(user.getUsername())) {
            throw new IllegalArgumentException("The username is not available.");
        }

        // default to Role to Viewer permission if not selected
        if(user.getRole().isEmpty()) {
            user.setRole(User.ROLE_UNASSIGNED);
        }

        //User user = new User(username, new BCryptPasswordEncoder().encode(password), email, firstname, lastname, role);
        // Without BCrypt password encrytion
        user.setPasswordDigest(new BCryptPasswordEncoder().encode(user.getPasswordDigest()));
        userRepository.addUser(user);
    }

    @Transactional(readOnly = true)
    public List<User> listUsers() {
        return userRepository.listUsers();
    }

    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return userRepository.getUserById(id);
    }

    @Transactional(readOnly = true)
    public User getUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }

    @Transactional(readOnly = true)
    public void updateUser(User user) {
        userRepository.updateUser(user);
    }

    @Transactional(readOnly = true)
    public void removeUser(Long id) {
        userRepository.removeUser(id);
    }






}
