package com.photos.serviceusers.repo.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(unique = true)
    private String username;
    private String password;
    private String description;
    private UserStatus status;
    private UserRole userRole;
    private Date registred;

    public User(String username, String password, String description, UserStatus status, UserRole userRole, Date registred) {
        this.username = username;
        this.password = password;
        this.description = description;
        this.status = status;
        this.userRole = userRole;
        this.registred = registred;
    }

    public User() {
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDescription() {
        return description;
    }

    public UserStatus getStatus() {
        return status;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public Date getRegistred() {
        return registred;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }
}
