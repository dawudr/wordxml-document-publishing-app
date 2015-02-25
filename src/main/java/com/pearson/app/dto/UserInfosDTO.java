package com.pearson.app.dto;

import com.pearson.app.model.User;

import java.util.List;
/**
 *
 * JSON-serializable DTO containing user data
 *
 */
public class UserInfosDTO {

    List<UserInfoDTO> users;

    public UserInfosDTO(List<UserInfoDTO> users) {
        this.users = users;
    }

    public List<UserInfoDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserInfoDTO> users) {
        this.users = users;
    }
}
