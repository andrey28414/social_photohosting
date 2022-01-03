package com.photos.serviceusers.service.impl;

import com.photos.serviceusers.repo.UserRepo;
import com.photos.serviceusers.repo.model.User;
import com.photos.serviceusers.repo.model.UserRole;
import com.photos.serviceusers.repo.model.UserStatus;
import com.photos.serviceusers.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public final class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    @Override
    public List<User> fetchAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public User fetchUserById(long id) throws IllegalArgumentException {
        final Optional<User> maybeUser = userRepo.findById(id);

        if (maybeUser.isPresent())
            return maybeUser.get();
        else
            throw new IllegalArgumentException("Invalid user ID");
    }

    @Override
    public long createUser(String username, String password, String description, UserStatus userStatus, UserRole userRole) {
        final User user = new User(username, password, description, userStatus, userRole, new Date());
        final User savedUser = userRepo.save(user);

        return savedUser.getId();
    }

    @Override
    public void updateUser(long id, String username, String password, String description, UserStatus userStatus, UserRole userRole) throws IllegalArgumentException {
        final Optional<User> maybeUser = userRepo.findById(id);
        if (maybeUser.isEmpty())
            throw new IllegalArgumentException("Invalid user ID");

        final User user = maybeUser.get();
        if (username != null && !username.isBlank()) user.setUsername(username);
        if (password != null && !password.isBlank()) user.setPassword(password);
        if (description != null && !description.isBlank()) user.setDescription(description);
        if (userStatus != null) user.setStatus(userStatus);
        else user.setStatus(UserStatus.ACTIVE);
        if (userRole != null) user.setUserRole(userRole);
        else user.setUserRole(UserRole.PAINTER);
        userRepo.save(user);
    }

    @Override
    public void deleteUser(long id) {
        userRepo.deleteById(id);
    }
}
