package com.photos.serviceusers.service;

import com.photos.serviceusers.repo.model.User;
import com.photos.serviceusers.repo.model.UserRole;
import com.photos.serviceusers.repo.model.UserStatus;

import java.util.Date;
import java.util.List;

public interface UserService {
    List<User> fetchAllUsers();
    User fetchUserById(long id) throws IllegalArgumentException;
    long createUser(String username, String password, String description, UserStatus status, UserRole userRole);

    void updateUser(long id, String username, String password, String description, UserStatus status, UserRole userRole) throws IllegalArgumentException;

    void deleteUser(long id);
}
