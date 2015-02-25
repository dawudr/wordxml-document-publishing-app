package com.pearson.app.dao;

import java.util.List;
import com.pearson.app.model.User;


/**
 * Created by dawud on 22/02/2015.
 */
public interface UserDAOInterface {

        public void addUser(User user);
        public List<User> listUsers();
        public User getUserById(Long id);
        public User getUserByUsername(String username);
        public void updateUser(User user);
        public void removeUser(Long id);
}
