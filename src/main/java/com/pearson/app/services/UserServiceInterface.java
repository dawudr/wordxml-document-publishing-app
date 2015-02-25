package com.pearson.app.services;

import com.pearson.app.model.User;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created by dawud on 22/02/2015.
 */
@Service
public interface UserServiceInterface {

        public void addUser(User user);
        public List<User> listUsers();
        public User getUserById(Long id);
        public User getUserByUsername(String username);
        public void updateUser(User user);
        public void removeUser(Long id);
}
