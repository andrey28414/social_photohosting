package com.photos.servicepublication.service;

import com.photos.servicepublication.repo.model.Publication;

import java.util.List;

public interface PublicationService {
    List<Publication> fetchAllPublications();
    Publication fetchPublicationById(long id) throws IllegalArgumentException;
    long createPublication(String name, String imagepath, String description, long author_id);
    void updatePublication(long id, String name, String imagepath, String description, long author_id);
    void deletePublication(long id);
}
