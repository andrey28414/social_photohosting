package com.photos.servicemention.api;

import com.photos.servicemention.api.dto.MentionDto;
import com.photos.servicemention.api.dto.PublicationDto;
import com.photos.servicemention.api.dto.UserDto;
import com.photos.servicemention.repo.model.Mention;
import com.photos.servicemention.repo.model.MentionStatus;
import com.photos.servicemention.service.impl.MentionServiceImpl;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/mentions")
@RestController
public class MentionController {

    private final MentionServiceImpl mentionServiceImpl;

    @GetMapping
    public ResponseEntity<List<Mention>> index() {
        final List<Mention> publications = mentionServiceImpl.fetchAllMentions();
        return ResponseEntity.ok(publications);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mention> showById(@PathVariable long id){
        try{
            final Mention mention = mentionServiceImpl.fetchMentionById(id);
            return ResponseEntity.ok(mention);
        } catch (IllegalArgumentException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<JSONObject> create(@RequestBody MentionDto mentionDto){
        final long user_id = mentionDto.user_id();
        final long publication_id = mentionDto.publication_id();
        final String content = mentionDto.content();
        final MentionStatus status = MentionStatus.valueOf(mentionDto.status());

        try {
            final long id = mentionServiceImpl.createMention(user_id, publication_id, content, status);
            final String mentionUri = String.format("/mentions/%d", id);
            return ResponseEntity.created(URI.create(mentionUri)).build();
        } catch (IllegalArgumentException e) {
            JSONObject response = new JSONObject();
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<JSONObject> change(@PathVariable long id, @RequestBody MentionDto mentionDto) {
        final long user_id = mentionDto.user_id();
        final long publication_id = mentionDto.publication_id();
        final String content = mentionDto.content();
        final MentionStatus status = MentionStatus.valueOf(mentionDto.status());

        try {
            mentionServiceImpl.updateMention(id, user_id, publication_id, content, status);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            JSONObject response = new JSONObject();
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/{id}/user")
    public ResponseEntity<UserDto> showUserById(@PathVariable long id) {
        try {
            final UserDto userDto = mentionServiceImpl.getUserByMentionId(id);

            return ResponseEntity.ok(userDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/publication")
    public ResponseEntity<PublicationDto> showPublicationById(@PathVariable long id) {
        try {
            final PublicationDto publicationDto = mentionServiceImpl.getPublicationByMentionId(id);

            return ResponseEntity.ok(publicationDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable long id) {
        mentionServiceImpl.deleteMention(id);

        return ResponseEntity.noContent().build();
    }


}
