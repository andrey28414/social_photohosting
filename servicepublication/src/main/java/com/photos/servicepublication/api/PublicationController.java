package com.photos.servicepublication.api;

import com.photos.servicepublication.api.dto.PublicationDto;
import com.photos.servicepublication.api.dto.UserDto;
import com.photos.servicepublication.repo.model.Publication;
import com.photos.servicepublication.service.impl.PublicationServiceImpl;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/publications")
@RestController
public class PublicationController {
    private final PublicationServiceImpl publicationServiceImpl;

    @GetMapping
    public ResponseEntity<List<Publication>> index() {
        final List<Publication> publications = publicationServiceImpl.fetchAllPublications();
        return ResponseEntity.ok(publications);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Publication> showById(@PathVariable long id){
        try{
            final Publication publication = publicationServiceImpl.fetchPublicationById(id);
            return ResponseEntity.ok(publication);
        } catch (IllegalArgumentException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<JSONObject> create(@RequestBody PublicationDto publicationDto){
        final String name = publicationDto.name();
        final String imagepath = publicationDto.imagepath();
        final String description = publicationDto.description();
        final long author_id = publicationDto.author_id();

        try {
            final long id = publicationServiceImpl.createPublication(name, imagepath, description, author_id);
            final String publicationUri = String.format("/publications/%d", id);
            return ResponseEntity.created(URI.create(publicationUri)).build();
        } catch (IllegalArgumentException e) {
            JSONObject response = new JSONObject();
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<JSONObject> change(@PathVariable long id, @RequestBody PublicationDto publicationDto) {
        final String name = publicationDto.name();
        final String imagepath = publicationDto.imagepath();
        final String description = publicationDto.description();
        final long author_id = publicationDto.author_id();
        try {
            publicationServiceImpl.updatePublication(id, name, imagepath, description, author_id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            JSONObject response = new JSONObject();
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/{id}/author")
    public ResponseEntity<UserDto> showUserById(@PathVariable long id) {
        try {
            final UserDto userDto = publicationServiceImpl.getAuthorById(id);

            return ResponseEntity.ok(userDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable long id) {
        publicationServiceImpl.deletePublication(id);

        return ResponseEntity.noContent().build();
    }
}
