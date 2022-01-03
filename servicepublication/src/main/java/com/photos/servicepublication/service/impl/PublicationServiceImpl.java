package com.photos.servicepublication.service.impl;

import com.photos.servicepublication.api.dto.UserDto;
import com.photos.servicepublication.repo.PublicationRepo;
import com.photos.servicepublication.repo.model.Publication;
import com.photos.servicepublication.service.PublicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public final class PublicationServiceImpl implements PublicationService {
    private final PublicationRepo publicationRepo;
    private final String userUrl = "http://service-users:8080/users/";

    @Override
    public List<Publication> fetchAllPublications(){return publicationRepo.findAll();}

    @Override
    public Publication fetchPublicationById(long id) throws IllegalArgumentException {
        final Optional<Publication> maybePublication = publicationRepo.findById(id);
        if (maybePublication.isPresent())
            return maybePublication.get();
        else
            throw new IllegalArgumentException("Invalid publication ID");
    }

    @Override
    public long createPublication(String name, String imagepath, String description, long author_id) {
        final RestTemplate restTemplate = new RestTemplate();
        final HttpEntity<Long> request = new HttpEntity<>(author_id);
        try {
            final ResponseEntity<UserDto> response = restTemplate.exchange(userUrl + author_id, HttpMethod.GET, request, UserDto.class);
        } catch(HttpClientErrorException e) { throw new IllegalArgumentException("Author does not exist");}
        final Publication publication = new Publication(name, imagepath, description,author_id);
        final Publication savedPublication = publicationRepo.save(publication);
        return savedPublication.getId();
    }

    @Override
    public void updatePublication(long id, String name, String imagepath, String description, long author_id) {
        final RestTemplate restTemplate = new RestTemplate();
        final HttpEntity<Long> request = new HttpEntity<>(author_id);
        try {
            final ResponseEntity<UserDto> response = restTemplate.exchange(userUrl + author_id, HttpMethod.GET, request, UserDto.class);
        } catch(HttpClientErrorException e) { throw new IllegalArgumentException("Author does not exist");}

        final Optional<Publication> maybepublication = publicationRepo.findById(id);
        if (maybepublication.isEmpty())
            throw new IllegalArgumentException("Invalid publication ID");

        final Publication publication = maybepublication.get();
        if (name != null && !name.isBlank()) publication.setName(name);
        if (imagepath != null && !imagepath.isBlank()) publication.setImagepath(imagepath);
        if (description != null && !description.isBlank()) publication.setDescription(description);
        publication.setAuthor_id(author_id);
        publicationRepo.save(publication);
    }

    public UserDto getAuthorById(long id){
        final Optional<Publication> maybepublication = publicationRepo.findById(id);
        if (maybepublication.isEmpty())
            throw new IllegalArgumentException("Invalid publication ID");
        long author_id = maybepublication.get().getAuthor_id();
        final RestTemplate restTemplate = new RestTemplate();
        final HttpEntity<Long> request = new HttpEntity<>(author_id);
        final ResponseEntity<UserDto> response = restTemplate.exchange(userUrl + author_id, HttpMethod.GET, request, UserDto.class);
        if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
            throw new IllegalArgumentException("User was not found");
        }
        return response.getBody();
    }

    @Override
    public void deletePublication(long id) {
        publicationRepo.deleteById(id);
    }

}
