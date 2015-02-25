package com.pearson.app.model;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * The User JPA entity.
 * Entity bean with JPA annotations
 * Hibernate provides JPA implementation
 *
 */
@Entity
@Table(name = "user")
@NamedQueries({
        @NamedQuery(
                name = User.FIND_BY_USERNAME,
                query = "select u from User u where username = :username"
        )
})
public class User extends AbstractEntity {

    public static final String FIND_BY_USERNAME = "user.findByUserName";
    public static final String ROLE_UNASSIGNED = "ROLE_UNASSIGNED";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_EDITOR = "ROLE_EDITOR";
    public static final String ROLE_AUTHOR = "ROLE_AUTHOR";
    public static final String ROLE_VIEWER = "ROLE_VIEWER";

    private String username;
    private String passwordDigest;
    private String email;
    private String firstname;
    private String lastname;
    private String role;

    public User() {

    }

    public User(String username, String passwordDigest, String email, String firstname, String lastname, String role) {
        this.username = username;
        this.passwordDigest = passwordDigest;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordDigest() {
        return passwordDigest;
    }

    public void setPasswordDigest(String passwordDigest) {
        this.passwordDigest = passwordDigest;
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
        return "User{username='" + username + '\'' +
                ", passwordDigest='" + passwordDigest + '\'' +
                ", email='" + email + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
