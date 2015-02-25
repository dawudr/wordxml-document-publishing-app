package com.pearson.app;


import com.pearson.app.model.User;
import com.pearson.app.services.UserService;
import com.pearson.config.root.RootContextConfig;
import com.pearson.config.root.TestConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(classes={TestConfiguration.class, RootContextConfig.class})
public class UserServiceTest {

    public static final String USERNAME = "test123";

    @Autowired
    private UserService userService;

    @PersistenceContext
    private EntityManager em;


    @Test
    public void testFindUserByUsername() {
        User user = findUserByUsername(USERNAME);
        assertNotNull("User is mandatory",user);
        assertTrue("Unexpected user " + user.getUsername(), user.getUsername().equals(USERNAME));
    }

    @Test
    public void testUserNotFound() {
        User user = findUserByUsername("doesnotexist");
        assertNull("User must be null", user);
    }

    @Test
    public void testCreateValidUser() {
        userService.addUser(new User("test456", "Password3", "test@gmail.com", "Paul", "Winser", User.ROLE_ADMIN));
        User user = findUserByUsername("test456");

        assertTrue("username not expected " + user.getUsername(), "test456".equals(user.getUsername()) );
        assertTrue("email not expected " + user.getEmail(), "test@gmail.com".equals(user.getEmail()) );

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        assertTrue("password not expected " + user.getPasswordDigest(), passwordEncoder.matches("Password3",user.getPasswordDigest()) );
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBlankUser() {
        userService.addUser(new User("", "Password3", "test@gmail.com", "Paul", "Winser", User.ROLE_ADMIN));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUsernameLength() {
        userService.addUser(new User("test", "Password3", "test@gmail.com", "Paul", "Winser", User.ROLE_ADMIN));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUsernameAvailable() {
        userService.addUser(new User("test123", "Password3", "test@gmail.com", "Paul", "Winser", User.ROLE_ADMIN));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBlankEmail() {
        userService.addUser(new User("test001", "Password3", "", "Paul", "Winser", User.ROLE_ADMIN));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidEmail() {
        userService.addUser(new User("test001", "Password3", "test", "Paul", "Winser", User.ROLE_ADMIN));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBlankPassword() {
        userService.addUser(new User("test002", "test@gmail.com", "", "Paul", "Winser", User.ROLE_ADMIN));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPasswordPolicy() { userService.addUser(new User("test003", "Password", "test@gmail.com", "Paul", "Winser", User.ROLE_ADMIN));}


    private User findUserByUsername(String username) {
        List<User> users = em.createQuery("select u from User u where username = :username")
                .setParameter("username", username).getResultList();

        return users.size() == 1 ? users.get(0) : null;
    }


}
