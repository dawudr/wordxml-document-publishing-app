package com.pearson.app.controllers;


import com.pearson.app.dto.NewUserDTO;
import com.pearson.app.dto.UserInfoDTO;
import com.pearson.app.model.User;
import com.pearson.app.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 *
 *  REST service for users.
 *
 */

@Controller
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, value = "/user")
    public UserInfoDTO getUserInfo(Principal principal) {

        User user = userService.getUserByUsername(principal.getName());
        LOGGER.debug("Current user[{}]", user.toString());
        return user != null ? new UserInfoDTO(user) : null;
    }


    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST, value = "/users")
    public void addUser(@RequestBody NewUserDTO newUserDTO) {
        User user = new User(
                newUserDTO.getUsername(),
                newUserDTO.getPassword(),
                newUserDTO.getFirstname(),
                newUserDTO.getLastname(),
                newUserDTO.getEmail(),
                newUserDTO.getRole());
        userService.addUser(user);
        LOGGER.debug("Add new user[{}]", user.toString());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, value= "/users")
    //public UserInfosDTO listUsers() {
//    public List<UserInfoDTO> listUsers() {
    public List<User> listUsers() {
        List<User> users = userService.listUsers();
        LOGGER.debug("Found {} Users", users.size());
        //return new UserInfosDTO(UserInfoDTO.mapFromUsersEntities(users));
//        return UserInfoDTO.mapFromUsersEntities(users);
        return users;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, value = "/user/{id}")
    //public UserInfoDTO getUserById(@PathVariable Long id) {
    public User getUserById(@PathVariable Integer id) {
        User user = userService.getUserById(id);
        LOGGER.debug("Found User id[{}] -> User[{}]", id, user.toString());
/*
        return user != null ? new UserInfoDTO(user.getId(), user.getUsername(),
                user.getEmail(), user.getFirstname(), user.getLastname(), user.getRole()) : null;
*/
        return user;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, value = "/user/username/{username}")
    //public UserInfoDTO getUserByUsername(Principal principal) {
    public User getUserByUsername(Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        LOGGER.debug("Found User username[{}] -> User[{}]", principal.getName(), user.toString());
        //return user != null ? new UserInfoDTO(user) : null;
        return user;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.PUT, value = "/user/{id}")
    public void updateUser(@RequestBody UserInfoDTO userInfoDTO) {
        LOGGER.debug("PUT UserInfoDTO[{}]", userInfoDTO.toString());
        User user = userService.getUserById(userInfoDTO.getId());
        user.setUsername(userInfoDTO.getUsername());
        user.setFirstname(userInfoDTO.getFirstname());
        user.setLastname(userInfoDTO.getLastname());
        user.setEmail(userInfoDTO.getEmail());
        user.setRole(userInfoDTO.getRole());
        user.setPasswordDigest(userInfoDTO.getPasswordDigest());

        userService.updateUser(user);
        LOGGER.debug("Update User[{}]", user.toString());

    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.DELETE, value = "/user/{id}")
    public void removeUser(@PathVariable int id) {
        userService.removeUser(id);
        LOGGER.debug("Remove User Id[{}]", id);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> errorHandler(Exception exc) {
        LOGGER.error(exc.getMessage(), exc);
        return new ResponseEntity<>(exc.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
