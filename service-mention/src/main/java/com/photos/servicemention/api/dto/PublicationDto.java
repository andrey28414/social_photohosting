package com.photos.servicemention.api.dto;

public record PublicationDto(long id, String name, String imagepath, String description, long author_id) {
}

