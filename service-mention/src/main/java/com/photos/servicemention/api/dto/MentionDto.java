package com.photos.servicemention.api.dto;

import com.photos.servicemention.repo.model.MentionStatus;

public record MentionDto(long user_id, long publication_id, String content, String status) {
}
