package com.pearson.app.dao;

import com.pearson.app.model.User;

import java.util.List;


/**
 * Created by dawud on 22/02/2015.
 */
public interface UserDAOInterface {

        public void addUser(User user);
        public List<User> listUsers();
        public User getUserById(int id);
        public User getUserByUsername(String username);
        public void updateUser(User user);
        public void removeUser(int id);
}
