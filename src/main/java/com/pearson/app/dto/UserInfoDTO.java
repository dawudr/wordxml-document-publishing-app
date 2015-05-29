package com.pearson.app.dto;


import com.pearson.app.model.User;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * JSON-serializable DTO containing user data
 *
 */
public class UserInfoDTO {

    private int id;
    private String username;
    private String password;
    private String email;
    private String firstname;
    private String lastname;
    private String role;

    public UserInfoDTO() {
        this.id = 0;
        this.username = "unknown";
        this.firstname = "unknown";
        this.lastname = "unknown";
        this.email = "unknown";
        this.role = User.ROLE_VIEWER;
    }

    public UserInfoDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPasswordDigest();
        this.email = user.getEmail();
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.role = user.getRole();
    }

    public UserInfoDTO(Integer id, String username, String password, String email,
                       String firstname, String lastname, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.role = role;
    }

    public static UserInfoDTO mapFromUserEntity(User user) {
        return new UserInfoDTO(user.getId(),
                user.getUsername(),
                user.getPasswordDigest(),
                user.getEmail(),
                user.getFirstname(),
                user.getLastname(),
                user.getRole());
    }


    public static List<UserInfoDTO> mapFromUsersEntities(List<User> users) {
        return users.stream().map((user) -> mapFromUserEntity(user)).collect(Collectors.toList());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserInfoDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
