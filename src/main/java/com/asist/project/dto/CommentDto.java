package com.asist.project.dto;

import com.asist.project.models.User;

import java.time.Instant;

/**
 * createdAt: 11/15/2020
 * project: ProjectsPanel
 *
 * @author Alexander Petrushkin
 */
public class CommentDto {
    private Long id;
    private Instant date;
    private String text;
    private UserDto user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }
}
