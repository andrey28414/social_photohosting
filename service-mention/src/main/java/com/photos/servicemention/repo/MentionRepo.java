package com.photos.servicemention.repo;

import com.photos.servicemention.repo.model.Mention;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MentionRepo extends JpaRepository<Mention, Long> {
}
