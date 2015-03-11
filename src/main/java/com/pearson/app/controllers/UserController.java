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
    @RequestMapping(method = RequestMethod.POST, value = "/user")
    public void addUser(@RequestBody NewUserDTO newUserDTO) {
        User user = new User(
                newUserDTO.getUsername(),
                newUserDTO.getPlainTextPassword(),
                newUserDTO.getEmail(),
                newUserDTO.getFirstname(),
                newUserDTO.getLastname(),
                newUserDTO.getRole());
        userService.addUser(user);
        LOGGER.debug("Add new user[{}]", user.toString());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, value= "/user/list")
    //public UserInfosDTO listUsers() {
    public List<UserInfoDTO> listUsers() {
        List<User> users = userService.listUsers();
        LOGGER.debug("Found {} Users", users.size());
        //return new UserInfosDTO(UserInfoDTO.mapFromUsersEntities(users));
        return UserInfoDTO.mapFromUsersEntities(users);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, value = "/user/{id}")
    //public UserInfoDTO getUserById(@PathVariable Long id) {
    public User getUserById(@PathVariable Long id) {
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
    public UserInfoDTO getUserByUsername(Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        LOGGER.debug("Found User username[{}] -> User[{}]", principal.getName(), user.toString());
        return user != null ? new UserInfoDTO(user) : null;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST, value = "/user/update")
    public void updateUser(@RequestBody UserInfoDTO userInfoDTO) {

        User user = new User(
                userInfoDTO.getUsername(),
                userInfoDTO.getPassword(),
                userInfoDTO.getFirstname(),
                userInfoDTO.getLastname(),
                userInfoDTO.getEmail(),
                userInfoDTO.getRole());
        userService.updateUser(user);
        LOGGER.debug("Update User[{}]", user.toString());

    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.DELETE, value = "/user/remove/{id}")
    public void removeUser(@PathVariable Long id) {
        userService.removeUser(id);
        LOGGER.debug("Remove User Id[{}]", id);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> errorHandler(Exception exc) {
        LOGGER.error(exc.getMessage(), exc);
        return new ResponseEntity<>(exc.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
