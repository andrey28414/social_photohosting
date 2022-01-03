package com.photos.servicepublication.repo;

import com.photos.servicepublication.repo.model.Publication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublicationRepo extends JpaRepository<Publication, Long> {
}
