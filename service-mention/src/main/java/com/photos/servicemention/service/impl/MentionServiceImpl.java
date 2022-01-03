package com.photos.servicemention.service.impl;

import com.photos.servicemention.api.dto.PublicationDto;
import com.photos.servicemention.api.dto.UserDto;
import com.photos.servicemention.repo.MentionRepo;
import com.photos.servicemention.repo.model.Mention;
import com.photos.servicemention.repo.model.MentionStatus;
import com.photos.servicemention.service.MentionService;
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
public final class MentionServiceImpl implements MentionService {
    private final MentionRepo mentionRepo;
    private final String userUrl = "http://service-users:8080/users/";
    private final String publicationUrl = "http://service-publications:8081/publications/";

    @Override
    public List<Mention> fetchAllMentions() {
        return mentionRepo.findAll();
    }

    @Override
    public Mention fetchMentionById(long id) throws IllegalArgumentException {
        final Optional<Mention> maybeMention = mentionRepo.findById(id);
        if (maybeMention.isPresent())
            return maybeMention.get();
        else
            throw new IllegalArgumentException("Invalid mention ID");
    }

    @Override
    public long createMention(long user_id, long publication_id, String content, MentionStatus status) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Long> request = new HttpEntity<>(user_id);
        try {
            final ResponseEntity<UserDto> response = restTemplate.exchange(userUrl + user_id, HttpMethod.GET, request, UserDto.class);
        } catch(HttpClientErrorException e) { throw new IllegalArgumentException("User does not exist");}

        restTemplate = new RestTemplate();
        request = new HttpEntity<>(publication_id);
        try {
            final ResponseEntity<PublicationDto> response = restTemplate.exchange(publicationUrl + publication_id, HttpMethod.GET, request, PublicationDto.class);
        } catch(HttpClientErrorException e) { throw new IllegalArgumentException("Publication does not exist");}

        final Mention mention = new Mention(user_id, publication_id, content, status);
        final Mention savedMention = mentionRepo.save(mention);

        return savedMention.getId();
    }

    @Override
    public void updateMention(long id, long user_id, long publication_id, String content, MentionStatus status) throws IllegalArgumentException {
        final Optional<Mention> maybeMention = mentionRepo.findById(id);
        if (maybeMention.isEmpty())
            throw new IllegalArgumentException("Invalid mention ID");

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Long> request = new HttpEntity<>(user_id);
        try {
            final ResponseEntity<UserDto> response = restTemplate.exchange(userUrl + user_id, HttpMethod.GET, request, UserDto.class);
        } catch(HttpClientErrorException e) { throw new IllegalArgumentException("User does not exist");}

        restTemplate = new RestTemplate();
        request = new HttpEntity<>(publication_id);
        try {
            final ResponseEntity<PublicationDto> response = restTemplate.exchange(publicationUrl + publication_id, HttpMethod.GET, request, PublicationDto.class);
        } catch(HttpClientErrorException e) { throw new IllegalArgumentException("Publication does not exist");}

        final Mention mention = maybeMention.get();
        if (content != null && !content.isBlank()) mention.setContent(content);
        if (status != null) mention.setStatus(status);
        else mention.setStatus(MentionStatus.ACTIVE);
        mentionRepo.save(mention);
    }

    public UserDto getUserByMentionId(long id){
        final Optional<Mention> maybeMention = mentionRepo.findById(id);
        if (maybeMention.isEmpty())
            throw new IllegalArgumentException("Invalid mention ID");
        long user_id = maybeMention.get().getUser_id();
        final RestTemplate restTemplate = new RestTemplate();
        final HttpEntity<Long> request = new HttpEntity<>(user_id);
        final ResponseEntity<UserDto> response = restTemplate.exchange(userUrl + user_id, HttpMethod.GET, request, UserDto.class);
        if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
            throw new IllegalArgumentException("User was not found");
        }
        return response.getBody();
    }

    public PublicationDto getPublicationByMentionId(long id){
        final Optional<Mention> maybeMention = mentionRepo.findById(id);
        if (maybeMention.isEmpty())
            throw new IllegalArgumentException("Invalid mention ID");
        long publication_id = maybeMention.get().getPublication_id();
        final RestTemplate restTemplate = new RestTemplate();
        final HttpEntity<Long> request = new HttpEntity<>(publication_id);
        final ResponseEntity<PublicationDto> response = restTemplate.exchange(publicationUrl + publication_id, HttpMethod.GET, request, PublicationDto.class);
        if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
            throw new IllegalArgumentException("Publication was not found");
        }
        return response.getBody();
    }

    @Override
    public void deleteMention(long id) {
        mentionRepo.deleteById(id);
    }
}
