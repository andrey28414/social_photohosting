package com.photos.serviceusers.repo;

import com.photos.serviceusers.repo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
}
