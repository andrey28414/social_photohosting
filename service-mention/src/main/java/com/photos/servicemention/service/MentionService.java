package com.photos.servicemention.service;

import com.photos.servicemention.repo.model.Mention;
import com.photos.servicemention.repo.model.MentionStatus;

import java.util.List;

public interface MentionService {
    List<Mention> fetchAllMentions();
    Mention fetchMentionById(long id) throws IllegalArgumentException;
    long createMention(long user_id, long publication_id, String content, MentionStatus status);

    void updateMention(long id, long user_id, long publication_id, String content, MentionStatus status) throws IllegalArgumentException;

    void deleteMention(long id);
}
