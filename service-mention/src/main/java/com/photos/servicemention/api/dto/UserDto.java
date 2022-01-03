package com.photos.servicemention.api.dto;

public record UserDto(long id, String username, String password, String description, String userStatus, String userRole) {
}