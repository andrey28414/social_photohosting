package com.photos.serviceusers.api;

import com.photos.serviceusers.api.dto.UserDto;
import com.photos.serviceusers.repo.model.User;
import com.photos.serviceusers.repo.model.UserRole;
import com.photos.serviceusers.repo.model.UserStatus;
import com.photos.serviceusers.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public final class UserController {
    private final UserServiceImpl userServiceImpl;

    @GetMapping
    public ResponseEntity<List<User>> index() {
        final List<User> users = userServiceImpl.fetchAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> showById(@PathVariable long id) {
        try {
            final User user = userServiceImpl.fetchUserById(id);

            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody UserDto userDto){
        final String username = userDto.username();
        final String password = userDto.password();
        final String description = userDto.description();
        final UserStatus userStatus = UserStatus.valueOf(userDto.userStatus());
        final UserRole userRole = UserRole.valueOf(userDto.userRole());

        final long userId = userServiceImpl.createUser(username,password,description,userStatus,userRole);
        final String userUri = String.format("/users/%d", userId);

        return ResponseEntity.created(URI.create(userUri)).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> change(@PathVariable long id, @RequestBody UserDto userDto) {
        final String username = userDto.username();
        final String password = userDto.password();
        final String description = userDto.description();
        final UserStatus userStatus = UserStatus.valueOf(userDto.userStatus());
        final UserRole userRole = UserRole.valueOf(userDto.userRole());

        try {
            userServiceImpl.updateUser(id, username, password, description, userStatus, userRole);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable long id) {
        userServiceImpl.deleteUser(id);

        return ResponseEntity.noContent().build();
    }

}
